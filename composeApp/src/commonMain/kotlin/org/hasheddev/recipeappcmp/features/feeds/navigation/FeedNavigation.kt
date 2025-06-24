package org.hasheddev.recipeappcmp.features.feeds.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.hasheddev.recipeappcmp.features.app.data.Screen
import org.hasheddev.recipeappcmp.features.feeds.ui.FeedRoute

fun NavController.navigateToFeed(navOptions: NavOptions? = null) {
    navigate(Screen.Home.route)
}

fun NavGraphBuilder.feedNavGraph(
    navigateToDetail: (Long) -> Unit,
    isUserLoggedIn: () -> Boolean,
    openLogInSheet: (() -> Unit) -> Unit,
    navigateToSearch: () -> Unit
) {
    composable(Screen.Home.route) {
        FeedRoute(
            navigateToDetail,
            isUserLoggedIn = isUserLoggedIn,
            openLogInSheet = openLogInSheet,
            navigateToSearch = navigateToSearch
        )
    }
}