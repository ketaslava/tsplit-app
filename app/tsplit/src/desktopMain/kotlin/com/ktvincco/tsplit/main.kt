package com.ktvincco.tsplit

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.awt.Toolkit

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Tsplit",
    ) {
        // keyboardService()
    }
}

actual fun epochMillis(): Long = System.currentTimeMillis()

@Composable
actual fun getScreenSizeInDp(): Pair<Dp, Dp> {
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    // Assuming a standard density of 1.0f for Desktop (logical density may vary by use case)
    val density = 1.0f // Update this if you have specific density logic for your desktop app
    val widthDp = (screenSize.width / density).toInt()
    val heightDp = (screenSize.height / density).toInt()
    return Pair(widthDp.dp, heightDp.dp)
}


@Composable
actual fun getScreenSizeInPx(): Pair<Int, Int> {
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    return Pair(screenSize.width, screenSize.height)
}