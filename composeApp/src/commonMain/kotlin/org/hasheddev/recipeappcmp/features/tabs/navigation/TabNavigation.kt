package org.hasheddev.recipeappcmp.features.tabs.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.hasheddev.recipeappcmp.features.app.data.Screen
import org.hasheddev.recipeappcmp.features.tabs.ui.TabRoute

fun NavController.navigateToTabs(navOptions: NavOptions? = null) {
    navigate(Screen.Tabs.route)
}

fun NavGraphBuilder.tabsNavGraph(
    navigateToDetail: (Long) -> Unit,
    navController: NavHostController,
    isUserLoggedIn: () -> Boolean,
    openLogInSheet: (() -> Unit) -> Unit,
    onLogOut: () -> Unit,
    navigateToSearch: () -> Unit
) {
    composable(Screen.Tabs.route) {
        TabRoute(
            navController =  navController,
            navigateToDetail = navigateToDetail,
            isUserLoggedIn = isUserLoggedIn,
            openLogInSheet = openLogInSheet,
            onLogOut = onLogOut,
            navigateToSearch = navigateToSearch
        )
    }
}