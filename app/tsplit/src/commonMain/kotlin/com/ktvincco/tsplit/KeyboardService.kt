package com.ktvincco.tsplit


import com.ktvincco.tsplit.data.Database
import com.ktvincco.tsplit.data.EnvironmentConnector
import com.ktvincco.tsplit.data.Logger
import com.ktvincco.tsplit.data.PermissionController
import com.ktvincco.tsplit.domain.Keyboard
import com.ktvincco.tsplit.domain.KeyboardInput
import com.ktvincco.tsplit.domain.keyboardStack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


class KeyboardService(private var emitInputCall: (input: KeyboardInput) -> Unit,
                      private var playSoundCall: (soundResource: String) -> Unit,
                      private var getKeyboardImageViewSize: () -> Pair<Int, Int>,
                      private var getTouches: () -> List<Map<String, String>>,
                      private var onNewFrame: (surface2D: Surface2D) -> Unit,
                      private var logger: Logger,
                      private var permissionController: PermissionController,
                      private var database: Database,
                      private var environmentConnector: EnvironmentConnector) {


    private var scope: CoroutineScope? = null
    val keyboard = Keyboard(keyboardStack, emitInputCall, playSoundCall, logger)


    fun start() {
        stop()

        if (scope == null) {
            scope = CoroutineScope(Dispatchers.Main)
            scope?.launch {
                while (isActive) {
                    update() // suspend or normal function
                    delay(16)     // 16 = ~60 FPS
                }
            }
        }

        logger.log("AAAA", "BBB!!!!BBB")
    }


    private fun update() {
        // logger?.log("AAAA", "BBBRRRRRRRRRRRRRR")

        // Get size
        val size = getKeyboardImageViewSize()
        if (size.first == 0 || size.second == 0) return
        var surface = Surface2D(size.first, size.second)

        // Get touches
        val touches = getTouches()

        // Update keyboard
        surface = keyboard.update(surface, touches)

        // Return
        onNewFrame(surface)
    }


    fun stop() {
        scope?.cancel()
        scope = null
    }

}
