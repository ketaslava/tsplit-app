package com.ktvincco.tsplit.domain

import androidx.compose.ui.graphics.Color
import com.ktvincco.tsplit.Circle
import com.ktvincco.tsplit.Rectangle
import com.ktvincco.tsplit.Surface2D
import com.ktvincco.tsplit.data.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.round
import kotlin.math.sqrt


class Keyboard (private val stack: Stack,
                private var emitInputCall: (input: KeyboardInput) -> Unit = {},
                private var playSoundCall: (soundResource: String) -> Unit = {},
                private var logger: Logger) {

    var baseLayer = "latin1"
    var firstLayer = baseLayer
    var isFirstLayerLocked = false
    var currentLayer = baseLayer
    var previewLayer = baseLayer
    var overlayLayer: String? = null

    var inputLevel = 0
    var isInputTriggered = false

    var isInGesture = false
    var gestureStartButton = Button()
    var clickStartButton = Button()
    var currentGestureName = ""
    var currentGestureStartPosition = Pair(0F, 0F)

    var isSoundEnabled = false

    var touchProcessor = TouchProcessor(logger)
    var pointers = Pointers(Pointer(Button(), false, "0",
        0F, 0F, 0, 0), null)
    var graphics = Graphics()


    fun update(targetSurface: Surface2D, touches: List<Map<String, String>>): Surface2D {
        var surface = targetSurface

        // Update input level
        val newInputLevel = touchProcessor.updateAndCalculateInputLevel(touches)

        // Update layer
        // Layer lock
        if (!isFirstLayerLocked) {
            firstLayer = baseLayer
        }
        // Always update
        var newIsInputTriggered = isInputTriggered
        if (newInputLevel == 0) {
            currentLayer = firstLayer
            previewLayer = firstLayer
            newIsInputTriggered = false
            overlayLayer = null
        }
        // Update during input
        if (newInputLevel != inputLevel) {

            if (newInputLevel > inputLevel) {
                currentLayer = previewLayer
                newIsInputTriggered = false
            } else {
                previewLayer = currentLayer
            }
        }

        // Get current touch
        val newPointers = touchProcessor.getPointers(touches, surface,
            Pair(0F, 0F), 4, 6,
            stack, currentLayer, overlayLayer)

        // Process layers
        // Preview layer
        val referenceLayerName = newPointers.current.button.referenceLayerName
        if (newInputLevel > 0 && !isInGesture) {
            previewLayer = referenceLayerName ?: currentLayer
        }
        // Overlay layer
        overlayLayer = null
        if(newPointers.current.button.overlayLayer != null && newPointers.current.isPressed) {
            overlayLayer = newPointers.current.button.overlayLayer
        }
        if (newPointers.second != null && newPointers.second?.isPressed == true) {
            overlayLayer = newPointers.second?.button?.overlayLayer?: overlayLayer
        }

        // Input
        if (newInputLevel < inputLevel && !isInputTriggered &&
                (!isInGesture || pointers.current.button == gestureStartButton)) {

            logger.log("AAAA", "INPUT!: ${pointers.current.button.inputText}, ${
                pointers.current.button.action}, ${pointers.current.button.amount}")
            // Process actions
            processActions(pointers.current.button)
            // Process layer lock
            processLayerLock(pointers.current.button, clickStartButton)
            // Initiate Input
            emitInputCall(KeyboardInput(inputText = pointers.current.button.inputText,
                action = pointers.current.button.action, amount = pointers.current.button.amount))
            // Play sound
            if (pointers.current.button.inputText != null && isSoundEnabled) {
                playSoundCall("vine-boom.wav")
            }
            // Set state
            newIsInputTriggered = true
        }

        // Process gestures
        // Start
        val gestureName = newPointers.current.button.gestureName
        if (gestureName != null && newInputLevel > inputLevel && !isInGesture) {
            isInGesture = true
            currentGestureName = gestureName
            currentGestureStartPosition = Pair(newPointers.current.x, newPointers.current.y)
            gestureStartButton = newPointers.current.button
        }
        if (newInputLevel > inputLevel) {
            clickStartButton = newPointers.current.button
        }
        // Stop
        var isGestureWasJustFinished = false
        if (newInputLevel < inputLevel && isInGesture) {
            // Set state and reset data
            isGestureWasJustFinished = true
            newIsInputTriggered = true
            isInGesture = false
            resetGestureDataFloat()
            resetGestureDataInt()
        }

        // Apply new states
        pointers = newPointers
        inputLevel = newInputLevel
        isInputTriggered = newIsInputTriggered

        // Draw

        // Draw background
        surface.drawObject(Rectangle(0F, 0F,
            surface.width.toFloat(), surface.height.toFloat(), Color.Black))

        // Draw grid
        surface = graphics.drawGrid(surface, 0F, 0F, surface.width.toFloat(),
            surface.height.toFloat(), 4, 6, 4F)

        // Draw buttons
        surface = graphics.drawButtons(surface, keyboardStack, previewLayer, 0F, 0F,
            surface.width.toFloat(), surface.height.toFloat(),
            4, 6, overlayLayer)

        // Draw touch
        if (!isInGesture && newPointers.current.isPressed)
            graphics.drawTouch(surface, 0F, 0F, surface.width.toFloat(),
                surface.height.toFloat(), newPointers.current.gridX,
                newPointers.current.gridY, 4, 6)

        // Process with all new states

        // Process
        surface = updateGestures(surface, isGestureWasJustFinished)
        updateLongPressActions()

        // Return
        return surface
    }


    fun processActions(button: Button) {

        // Scripts
        if (button.action == "changeScriptToLatin") {
            baseLayer = "latin1"
            firstLayer = baseLayer
            isFirstLayerLocked = false
            currentLayer = firstLayer
            previewLayer = firstLayer
        }
        if (button.action == "changeScriptToCyrillic") {
            baseLayer = "cyrillic1"
            firstLayer = baseLayer
            isFirstLayerLocked = false
            currentLayer = firstLayer
            previewLayer = firstLayer
        }
        /*if (button.action == "changeScriptToArabic") {
            baseLayer = "arabic1"
            firstLayer = baseLayer
            isFirstLayerLocked = false
        }*/

        // Sound
        if (button.action == "switchTheSound") {
            isSoundEnabled = !isSoundEnabled
        }
    }


    fun processLayerLock(button: Button, clickStartButton: Button) {
        if (button.isLockReferenceLayerOnCompleteClick && button == clickStartButton) {
            firstLayer = button.referenceLayerName?: baseLayer
            isFirstLayerLocked = true
            currentLayer = firstLayer
            previewLayer = firstLayer
        }
        if (button.isUnlockLayerOnCompleteClick && button == clickStartButton) {
            firstLayer = baseLayer
            isFirstLayerLocked = false
            currentLayer = firstLayer
            previewLayer = firstLayer
        }
    }


    var longPressAction = CoroutineScope(Dispatchers.Main)
    suspend fun processLongPressAction() {

        if (pointers.current.button.longPressAction == "deleteMultipleCharactersFromTheLeft" &&
            getGestureDataInt("stepsDone") == 0) {
            emitInputCall(KeyboardInput(action = "deleteCharacterFromTheLeft"))
        }
    }
    fun updateLongPressActions() {
        if (pointers.current.button.longPressAction != null && pointers.current.isPressed) {
            if (!longPressAction.isActive) {
                longPressAction = CoroutineScope(Dispatchers.Main)
                longPressAction.launch {
                    delay(333)
                    while (isActive) {
                        processLongPressAction()
                        delay(50)
                    }
                }
            }
        } else {
            longPressAction.cancel()
        }
    }


    var gestureDataFloat = mutableMapOf<String, Float>()
    fun setGestureDataFloat(name: String, value: Float) {
        gestureDataFloat[name] = value
    }
    fun getGestureDataFloat(name: String): Float {
        return gestureDataFloat[name]?: 0F
    }
    fun resetGestureDataFloat() {
        gestureDataFloat.clear()
    }
    var gestureDataInt = mutableMapOf<String, Int>()
    fun setGestureDataInt(name: String, value: Int) {
        gestureDataInt[name] = value
    }
    fun addToGestureDataInt(name: String, value: Int) {
        val data = gestureDataInt[name]?: 0
        gestureDataInt[name] = data + value
    }
    fun getGestureDataInt(name: String): Int {
        return gestureDataInt[name]?: 0
    }
    fun resetGestureDataInt() {
        gestureDataInt.clear()
    }


    fun calculateSteps(surface: Surface2D, gestureDistance: Float, speed: Float): Int {
        val step = surface.width / speed
        val stepsDone = getGestureDataInt("stepsDone")
        val stepsDoneDistance = stepsDone * step
        val stepsNotDoneDistance = gestureDistance - stepsDoneDistance
        return round(stepsNotDoneDistance / step).toInt()
    }


    fun updateGestures(targetSurface: Surface2D, isGestureWasJustFinished: Boolean): Surface2D {
        val surface = targetSurface

        // Calculate essentials
        val offset = Pair(
            pointers.current.x - currentGestureStartPosition.first,
            pointers.current.y - currentGestureStartPosition.second
        )
        val gestureDistance = sqrt(offset.first * offset.first + offset.second * offset.second)
        val minDistance = surface.width / 6F

        // Gestures

        // Delete
        if (isInGesture && currentGestureName == "deleteMultipleCharactersFromTheLeft") {
            val unprocessedSteps = calculateSteps(surface, gestureDistance, 128F)
            addToGestureDataInt("stepsDone", unprocessedSteps)

            if ((gestureDistance > minDistance && unprocessedSteps > 0) || unprocessedSteps < 0) {
                emitInputCall(KeyboardInput(action = "moveSelectionLeft", amount = unprocessedSteps))
            }
        }
        if (isGestureWasJustFinished && currentGestureName == "deleteMultipleCharactersFromTheLeft") {
            emitInputCall(KeyboardInput(action = "deleteSelection"))
        }

        // Move cursor
        if (isInGesture && currentGestureName == "moveCursor") {
            val unprocessedSteps = calculateSteps(surface, gestureDistance, 24F)
            addToGestureDataInt("stepsDone", unprocessedSteps)

            if ((gestureDistance > minDistance && unprocessedSteps > 0) || unprocessedSteps < 0) {

                /*val maxAntiDirection = getGestureDataInt("stepsDone")
                if (-maxAntiDirection > unprocessedSteps) unprocessedSteps = -maxAntiDirection*/

                emitInputCall(KeyboardInput(action = "moveCursor",
                    amount = gestureStartButton.amount!! * unprocessedSteps))
            }
        }

        // Update state
        setGestureDataFloat("lastGestureDistance", gestureDistance)
        if (gestureDistance > getGestureDataFloat("maxGestureDistance")) {
            setGestureDataFloat("maxGestureDistance", gestureDistance)
        }

        // Draw pointer
        if(isInGesture) {
            surface.drawObject(
                Circle(
                    pointers.current.x,
                    pointers.current.y, surface.width / 18F, Color.Yellow
                )
            )
        }

        return surface
    }
}