package com.ktvincco.tsplit

import android.annotation.SuppressLint
import android.inputmethodservice.InputMethodService
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.ExtractedTextRequest
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ktvincco.tsplit.data.AndroidDatabase
import com.ktvincco.tsplit.data.AndroidEnvironmentConnector
import com.ktvincco.tsplit.data.AndroidLogger
import com.ktvincco.tsplit.data.AndroidPermissionController
import android.widget.ImageView
import com.ktvincco.tsplit.domain.KeyboardInput
import kotlin.collections.mutableListOf
import android.content.res.Configuration
import android.media.AudioAttributes
import android.media.SoundPool
import java.io.IOException
import kotlin.collections.set


/*class MainActivity : ComponentActivity() {

    // Create platform components
    private val androidLogger = AndroidLogger()
    private val permissionController = AndroidPermissionController(this)
    private val androidDatabase = AndroidDatabase(this, AppInfo.NAME)
    private val environmentConnector = AndroidEnvironmentConnector(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(androidLogger, permissionController, androidDatabase, environmentConnector)
        }
    }

    // Permissions request callback
    @Deprecated("Deprecated in Java (Today don't have a Kotlin solution)")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        @Suppress("DEPRECATION")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Process in domainController
        permissionController.requestPermissionsResultCallback(
            requestCode, permissions, grantResults)
    }
}*/


class MainActivity : ComponentActivity() {

    // Create platform components
    private val androidLogger = AndroidLogger()
    private val permissionController = AndroidPermissionController(this)
    private val androidDatabase = AndroidDatabase(this, AppInfo.NAME)
    private val environmentConnector = AndroidEnvironmentConnector(this)
    private var keyboardService: KeyboardService? = null

    var keyboardView: View? = null
    var keyboardImageView: ImageView? = null

    // Sound
    private lateinit var soundPool: SoundPool
    private val soundMap = mutableMapOf<String, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Create view according to orientation
        val orientation = resources.configuration.orientation
        keyboardView = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutInflater.inflate(R.layout.keyboard_layout_landscape, null)
        } else {
            layoutInflater.inflate(R.layout.keyboard_layout, null)
        }

        // Sound
        // Initialize SoundPool for short overlapping sounds
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(10) // allow overlapping sounds
            .setAudioAttributes(audioAttributes)
            .build()

        // Set view
        setContentView(keyboardView)
    }

    fun getKeyboardImageViewSize(): Pair<Int, Int> {
        return Pair(keyboardImageView!!.width, keyboardImageView!!.height)
    }

    override fun onResume() {
        super.onResume()
        keyboardImageView = keyboardView?.findViewById<ImageView>(R.id.imageView1)
        assignListeners()
        Log.i("MyKeyboardService", "ON UI " + "${keyboardImageView!!.width}")
        keyboardService = KeyboardService({emitInput(it)},
            { playSound(it) },
            {getKeyboardImageViewSize()},
            {getTouchesList()}, {onNewFrame(it)},
            androidLogger, permissionController, androidDatabase, environmentConnector)
        keyboardService?.start()
    }

    fun emitInput(input: KeyboardInput) {
        // Empty
    }

    fun playSound(soundResource: String) {
        // If sound already loaded, play it immediately
        val soundId = soundMap[soundResource]
        if (soundId != null) {
            soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
            return
        }

        // Otherwise load it from assets
        try {
            assets.openFd(soundResource).use { afd ->
                val newSoundId = soundPool.load(afd, 1)
                soundMap[soundResource] = newSoundId

                // Play as soon as it finishes loading
                soundPool.setOnLoadCompleteListener { sp, sampleId, status ->
                    if (status == 0 && sampleId == newSoundId) {
                        sp.play(sampleId, 1f, 1f, 0, 0, 1f)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    val touches: MutableList<MutableMap<String, String>> = mutableListOf() // (x, y)

    @SuppressLint("ClickableViewAccessibility")
    fun assignListeners() {
        keyboardImageView?.setOnTouchListener { v, event ->

            touches.clear()
            val pointerCount = event.pointerCount

            for (i in 0 until pointerCount) {
                val pointerId = event.getPointerId(i)
                val x = event.getX(i)
                val y = event.getY(i)

                val action = event.actionMasked
                val actionIndex = event.actionIndex

                // Skip if this specific pointer has lifted up
                if ((action == MotionEvent.ACTION_UP) ||
                    (action == MotionEvent.ACTION_POINTER_UP && i == actionIndex)) {
                    continue
                }

                touches.add(
                    mutableMapOf(
                        "id" to pointerId.toString(),
                        "x" to x.toString(),
                        "y" to y.toString()
                    )
                )
            }

            true
        }

        keyboardImageView?.setOnClickListener { /* no-op */ }
    }

    fun getTouchesList(): List<Map<String, String>> {
        return touches
    }

    fun onNewFrame(surface2D: Surface2D) {
        keyboardImageView?.setImageBitmap(surface2DToAndroidBitmap(surface2D, this))
    }

    fun stop() {
        keyboardService?.stop()
        touches.clear()
    }

    override fun onPause() {
        super.onPause()
        stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        stop()
        soundPool.release()
    }

    // Permissions request callback
    @Deprecated("Deprecated in Java (Today don't have a Kotlin solution)")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        @Suppress("DEPRECATION")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Process in domainController
        permissionController.requestPermissionsResultCallback(
            requestCode, permissions, grantResults)
    }
}


class MyKeyboardService : InputMethodService() {

    private val androidLogger = AndroidLogger()
    private val permissionController = AndroidPermissionController(null)
    private val androidDatabase = AndroidDatabase(null, AppInfo.NAME)
    private val environmentConnector = AndroidEnvironmentConnector(null)
    private var keyboardService: KeyboardService? = null

    var keyboardView: View? = null
    var keyboardImageView: ImageView? = null

    // Sound
    private lateinit var soundPool: SoundPool
    private val soundMap = mutableMapOf<String, Int>()

    override fun onCreateInputView(): View {

        // Create view according to orientation
        val orientation = resources.configuration.orientation
        keyboardView = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutInflater.inflate(R.layout.keyboard_layout_landscape, null)
        } else {
            layoutInflater.inflate(R.layout.keyboard_layout, null)
        }

        // Sound
        // Initialize SoundPool for short overlapping sounds
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(10) // allow overlapping sounds
            .setAudioAttributes(audioAttributes)
            .build()

        // Log
        Log.i("MyKeyboardService", "ON CREATE !!!!!!!!!!!!")

        // Return view
        return keyboardView!!
    }

    fun onNewFrame(surface2D: Surface2D) {
        keyboardImageView?.setImageBitmap(surface2DToAndroidBitmap(surface2D, this))
    }

    fun emitInput(input: KeyboardInput) {
        // Get current input connection (the active text field)
        val ic = currentInputConnection ?: return

        // Text

        if (input.inputText != null) {
            ic.commitText(input.inputText, 1)
            ic.finishComposingText()
        }

        // Actions

        if (input.action == "deleteCharacterFromTheLeft") {
            ic.deleteSurroundingText(1, 0)
        }

        if (input.action == "enter") {
            val actionId = currentInputEditorInfo?.imeOptions ?: EditorInfo.IME_NULL
            when (actionId and EditorInfo.IME_MASK_ACTION) {
                EditorInfo.IME_ACTION_SEARCH -> ic.performEditorAction(EditorInfo.IME_ACTION_SEARCH)
                EditorInfo.IME_ACTION_GO -> ic.performEditorAction(EditorInfo.IME_ACTION_GO)
                EditorInfo.IME_ACTION_SEND -> ic.performEditorAction(EditorInfo.IME_ACTION_SEND)
                EditorInfo.IME_ACTION_DONE -> ic.performEditorAction(EditorInfo.IME_ACTION_DONE)
                else -> ic.commitText("\n", 1)
            }
        }

        if (input.action == "moveCursor") {
            //androidLogger.log("AAA", "moveCursor on ${input.amount}")
            val text = ic.getExtractedText(ExtractedTextRequest(), 0)?.text ?: return
            val cursor = ic.getExtractedText(ExtractedTextRequest(), 0)?.selectionStart ?: return
            val newPos = (cursor + input.amount!!)//.coerceAtLeast(0).coerceAtMost(text.length)
            ic.setSelection(newPos, newPos)
        }

        if (input.action == "moveSelectionLeft") {
            val extracted = ic.getExtractedText(ExtractedTextRequest(), 0) ?: return
            val start = extracted.selectionStart
            val end = extracted.selectionEnd

            // Move selection start one char to the left
            val newStart = (start - input.amount!!).coerceAtLeast(0).coerceAtMost(end)
            ic.setSelection(newStart, end)
        }

        if (input.action == "deleteSelection") {
            val extracted = ic.getExtractedText(ExtractedTextRequest(), 0) ?: return
            val start = extracted.selectionStart
            val end = extracted.selectionEnd
            if (start != end) {
                ic.setSelection(start, end)
                ic.commitText("", 1)
            }
        }

        //Log.i("MyKeyboardService", "Input committed: $input")
    }

    fun playSound(soundResource: String) {
        // If sound already loaded, play it immediately
        val soundId = soundMap[soundResource]
        if (soundId != null) {
            soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
            return
        }

        // Otherwise load it from assets
        try {
            assets.openFd(soundResource).use { afd ->
                val newSoundId = soundPool.load(afd, 1)
                soundMap[soundResource] = newSoundId

                // Play as soon as it finishes loading
                soundPool.setOnLoadCompleteListener { sp, sampleId, status ->
                    if (status == 0 && sampleId == newSoundId) {
                        sp.play(sampleId, 1f, 1f, 0, 0, 1f)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getKeyboardImageViewSize(): Pair<Int, Int> {
        return Pair(keyboardImageView!!.width, keyboardImageView!!.height)
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        Log.i("MyKeyboardService", "ON START !!!!!!!!!!!!")
        keyboardImageView = keyboardView!!.findViewById<ImageView>(R.id.imageView1)
        assignListeners()
        if (keyboardService == null) {
            keyboardService = KeyboardService(
                { emitInput(it) },
                { playSound(it) },
                { getKeyboardImageViewSize() },
                { getTouchesList() }, { onNewFrame(it) },
                androidLogger, permissionController,
                androidDatabase, environmentConnector
            )
        }
        keyboardService?.start()
    }

    val touches: MutableList<MutableMap<String, String>> = mutableListOf() // (x, y)

    @SuppressLint("ClickableViewAccessibility")
    fun assignListeners() {
        keyboardImageView?.setOnTouchListener { v, event ->

            touches.clear()
            val pointerCount = event.pointerCount

            for (i in 0 until pointerCount) {
                val pointerId = event.getPointerId(i)
                val x = event.getX(i)
                val y = event.getY(i)

                val action = event.actionMasked
                val actionIndex = event.actionIndex

                // Skip if this specific pointer has lifted up
                if ((action == MotionEvent.ACTION_UP) ||
                    (action == MotionEvent.ACTION_POINTER_UP && i == actionIndex)) {
                    continue
                }

                touches.add(
                    mutableMapOf(
                        "id" to pointerId.toString(),
                        "x" to x.toString(),
                        "y" to y.toString()
                    )
                )
            }

            true
        }

        keyboardImageView?.setOnClickListener { /* no-op */ }
    }

    fun getTouchesList(): List<Map<String, String>> {
        return touches
    }

    fun stop() {
        keyboardService?.stop()
        touches.clear()
    }

    override fun onFinishInputView(finishingInput: Boolean) {
        super.onFinishInputView(finishingInput)
        stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        stop()
        soundPool.release()
    }
}


actual fun epochMillis(): Long = System.currentTimeMillis()


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
actual fun getScreenSizeInDp(): Pair<Dp, Dp> {
    val conf = LocalConfiguration.current
    return Pair(conf.screenWidthDp.dp, conf.screenHeightDp.dp)
}


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
actual fun getScreenSizeInPx(): Pair<Int, Int> {
    val conf = LocalConfiguration.current
    val density = LocalDensity.current.density
    return Pair((conf.screenWidthDp * density).toInt(), (conf.screenHeightDp * density).toInt())
}