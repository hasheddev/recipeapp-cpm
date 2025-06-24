package org.hasheddev.recipeappcmp.features.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.hasheddev.recipeappcmp.features.app.data.Screen
import org.hasheddev.recipeappcmp.features.profile.ui.ProfileRoute

fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
    navigate(Screen.Profile.route)
}

fun NavGraphBuilder.profileNavGraph(
    isUserLoggedIn: () -> Boolean,
    openLogInSheet: (() -> Unit) -> Unit,
    onLogOut: () -> Unit,
) {
    composable(Screen.Profile.route) {
        ProfileRoute(
            isUserLoggedIn = isUserLoggedIn,
            openLogInSheet = openLogInSheet,
            onLogOut = onLogOut
        )
    }
}