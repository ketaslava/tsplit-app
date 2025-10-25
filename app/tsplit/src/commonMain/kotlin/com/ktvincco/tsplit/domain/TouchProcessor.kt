package com.ktvincco.tsplit.domain

import com.ktvincco.tsplit.Surface2D
import com.ktvincco.tsplit.data.Logger
import kotlin.Float
import kotlin.String
import kotlin.collections.Map
import kotlin.math.round


class Pointers (var current: Pointer, var second: Pointer?)

class Pointer (var button: Button, var isPressed: Boolean, var id: String, var x: Float,
               var y: Float, var gridX: Int, var gridY: Int, var gridLinearPosition: Int = 0)


class TouchProcessor (val logger: Logger) {

    var inputLevel = 0
    var isInputTriggered = false
    var lastConfiguration = listOf(false, false)
    var focusedOnId = 0
    var maxInputLevel = 0


    fun updateAndCalculateInputLevel(touches: List<Map<String, String>>): Int {
        // Calculate level

        // Get configuration
        val states = mutableListOf(false, false)
        for (touch in touches) {
            if (touch["id"] == "0") states[0] = true
            if (touch["id"] == "1") states[1] = true
        }
        val configuration = states.toList()

        // No touches
        if (configuration == listOf(false, false) && configuration != lastConfiguration) {
            focusedOnId = 0
            inputLevel = 0
            maxInputLevel = 0
        }

        // One touch after zero
        if (lastConfiguration == listOf(false, false) && configuration != lastConfiguration) {
            focusedOnId = 0
            inputLevel = 1
        }

        // Both touches -> Up level and switch focus
        if (configuration == listOf(true, true) && configuration != lastConfiguration) {
            focusedOnId = if (focusedOnId == 0) 1 else 0
            inputLevel += 1
        }

        // Finger was lifted -> ignore or down level
        if (lastConfiguration == listOf(true, true) && configuration != lastConfiguration) {
            if (!configuration[focusedOnId]) {
                focusedOnId = if (focusedOnId == 0) 1 else 0
                inputLevel -= 1
                if (inputLevel < 0) {
                    inputLevel = 0
                }
            }
        }

        // Max input level
        if (inputLevel >= maxInputLevel) {
            maxInputLevel = inputLevel
            isInputTriggered = false
        }

        // Set state
        lastConfiguration = configuration

        // Return
        return inputLevel
    }


    fun getPointer(touches: List<Map<String, String>>, touchId: Int, surface: Surface2D,
                    gridOffset: Pair<Float, Float>, rows: Int, columns: Int, stack: Stack,
                    currentLayer: String, overlayLayer: String? = null): Pointer {

        // Essentials
        val buttonSize = Pair(surface.width / columns.toFloat(),
            surface.height / rows.toFloat())

        var position = Pair(0F, 0F)
        var isPressed = false

        // Get position from touches
        for (touch in touches) {
            if (touchId.toString() == touch["id"]) {
                position = Pair(
                    touch["x"]?.toFloat()
                        ?: 0F, touch["y"]?.toFloat() ?: 0F
                )
                isPressed = true
            }
        }

        // Calculate grid position
        val gridPosition = Pair(
            round(((gridOffset.first + position.first) / buttonSize.first) - 0.5F).toInt(),
            round(((gridOffset.second + position.second) / buttonSize.second) - 0.5F).toInt())
        val gridLinearPosition = (columns * gridPosition.second) + gridPosition.first

        // Get button
        var button = stack.getLayerByName(currentLayer
        )?.getButtonByPosition(gridLinearPosition)?: Button()
        val overlayButton = stack.getLayerByName(
            overlayLayer?: "")?.getButtonByPosition(gridLinearPosition)
        if (overlayButton != null) button = overlayButton

        // Return
        return Pointer(button, isPressed, focusedOnId.toString(),
            position.first, position.second, gridPosition.first,
            gridPosition.second, gridLinearPosition)
    }


    fun getPointers(touches: List<Map<String, String>>, surface: Surface2D,
        gridOffset: Pair<Float, Float>, rows: Int, columns: Int, stack: Stack,
        currentLayer: String, overlayLayer: String? = null): Pointers {

        return Pointers(
            getPointer(touches, focusedOnId, surface, gridOffset, rows, columns, stack,
                currentLayer, overlayLayer),
            getPointer(touches, if (focusedOnId == 0) 1 else 0, surface,
                gridOffset, rows, columns, stack, currentLayer, overlayLayer)
        )
    }
}