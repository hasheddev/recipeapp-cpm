package org.hasheddev.recipeappcmp.features.app.data.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.hasheddev.recipeappcmp.features.app.data.AppState
import org.hasheddev.recipeappcmp.features.app.data.Screen
import org.hasheddev.recipeappcmp.features.detail.navigation.detailNavGraph
import org.hasheddev.recipeappcmp.features.search.navigation.searchNavGraph
import org.hasheddev.recipeappcmp.features.tabs.navigation.tabsNavGraph

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    appState: AppState,
    startDestination: String = Screen.Tabs.route,
    isUserLoggedIn: () -> Boolean,
    openLogInSheet: (() -> Unit) -> Unit,
    onLogOut: () -> Unit,
) {
    val navController = appState.navController
    val tabNavController = rememberNavController()

    NavHost(
        startDestination = startDestination,
        navController = navController
    ) {
        tabsNavGraph(
            navController = tabNavController,
            navigateToDetail = {
                appState.navigateToDetail(it)
            },
            isUserLoggedIn = isUserLoggedIn,
            openLogInSheet = openLogInSheet,
            onLogOut = onLogOut,
            navigateToSearch = appState::navigateToSearch
        )

        searchNavGraph(
            onBackPress = appState::onBackClick,
            navigateToDetail = {
                appState.navigateToDetail(it)
            }
        )

        detailNavGraph(
            onBackClick = appState::onBackClick,
            isUserLoggedIn = isUserLoggedIn,
            openLogInSheet = openLogInSheet,
        )
    }
}