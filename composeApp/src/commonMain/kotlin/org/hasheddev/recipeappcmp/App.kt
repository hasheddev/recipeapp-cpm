package org.hasheddev.recipeappcmp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import org.hasheddev.recipeappcmp.features.app.data.navigation.AppNavHost
import org.hasheddev.recipeappcmp.features.app.data.rememberAppState
import org.hasheddev.recipeappcmp.features.designsystem.theme.RecipeAppCMPTheme
import org.hasheddev.recipeappcmp.features.login.ui.LoginScreenModalBottomSheet
import org.hasheddev.recipeappcmp.features.login.ui.LoginViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    RecipeAppCMPTheme {
        val navController = rememberNavController()
        val appState = rememberAppState(navController)

        var showLoginSheet by remember {mutableStateOf(false)}

        val loginViewModel: LoginViewModel = koinViewModel()

        val isLoggedIn by appState.isLoggedIn.collectAsStateWithLifecycle()
        val isUserLoggedIn: () -> Boolean = { isLoggedIn }
        var loginCallback: () -> Unit by remember {
            mutableStateOf({})
        }
        val openLoginBottomSheet: (() -> Unit) -> Unit = {call ->
            showLoginSheet = true
            loginCallback = call
        }
        val onLoginSuccess: () -> Unit = {
            showLoginSheet = false
            appState.updateIsLoggedIn(true)
            loginViewModel.resetState()
            loginCallback()
        }

        val onLogOut: () -> Unit = {
            appState.onLogeOut()
            loginViewModel.resetState()
        }

        val onCloseBottomSheet: () -> Unit = {
            showLoginSheet = false
            loginViewModel.resetState()
        }

        LoginScreenModalBottomSheet(
            loginViewModel = loginViewModel,
            showBottomSheet = showLoginSheet,
            onClose = onCloseBottomSheet,
            onLoginSuccess = onLoginSuccess
        )
        AppNavHost(
            appState = appState,
            openLogInSheet = openLoginBottomSheet,
            isUserLoggedIn = isUserLoggedIn,
            onLogOut = onLogOut,
        )
    }
}