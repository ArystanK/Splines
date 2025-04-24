package kz.arctan.splines

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Splines",
    ) {
        SplineView()
    }
}