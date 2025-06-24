package org.hasheddev.recipeappcmp

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController()  {
    ComposeUIViewController(
        configure = {
            initKoinIOS()
        }
    ) { App() }
}