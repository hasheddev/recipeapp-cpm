package org.hasheddev.recipeappcmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.hasheddev.recipeappcmp.di.initKoinJVM

fun main() = application {
    initKoinJVM()
    Window(
        onCloseRequest = ::exitApplication,
        title = "RecipeApp-CPM",
    ) {
        App()
    }
}