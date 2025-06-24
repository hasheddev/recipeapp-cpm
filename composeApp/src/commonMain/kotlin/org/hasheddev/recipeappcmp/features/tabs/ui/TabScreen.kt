package org.hasheddev.recipeappcmp.features.tabs.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.hasheddev.recipeappcmp.features.app.data.Screen
import org.hasheddev.recipeappcmp.features.favorites.navigation.favoriteNavGraph
import org.hasheddev.recipeappcmp.features.feeds.navigation.feedNavGraph
import org.hasheddev.recipeappcmp.features.profile.navigation.profileNavGraph
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

val tabItems = listOf(
    Screen.Home,
    Screen.Favorites,
    Screen.Profile
)

@Composable
fun TabRoute(
    navController: NavHostController,
    navigateToDetail: (Long) -> Unit,
    isUserLoggedIn: () -> Boolean,
    openLogInSheet: (() -> Unit) -> Unit,
    onLogOut: () -> Unit,
    navigateToSearch: () -> Unit
) {
    TabScreen(
        navController = navController,
        navigateToDetail = navigateToDetail,
        isUserLoggedIn = isUserLoggedIn,
        openLogInSheet = openLogInSheet,
        onLogOut = onLogOut,
        navigateToSearch = navigateToSearch
    )
}


@Composable
private fun TabScreen(
    navController: NavHostController,
    navigateToDetail: (Long) -> Unit,
    isUserLoggedIn: () -> Boolean,
    openLogInSheet: (() -> Unit) -> Unit,
    onLogOut: () -> Unit,
    navigateToSearch: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.onPrimary
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                tabItems.forEach { topLevelRoute ->
                    val isSelected = currentDestination?.hierarchy?.any { it.route == topLevelRoute.route } == true
                    val icon = if(isSelected) topLevelRoute.selectedIcon else topLevelRoute.unSelectedIcon
                    val color = if(isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.onBackground
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent
                        ),
                        icon = {
                            icon?.let {
                                Icon(
                                    tint = color,
                                    painter = painterResource(icon), contentDescription = topLevelRoute.route,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        },
                        label = { Text(stringResource(topLevelRoute.resourceId)) },
                        selected = isSelected,
                        onClick = {
                            navController.navigate(topLevelRoute.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Screen.Home.route, Modifier.padding(innerPadding)) {
            feedNavGraph(
                navigateToDetail = navigateToDetail,
                isUserLoggedIn = isUserLoggedIn,
                openLogInSheet = openLogInSheet,
            ) {  navigateToSearch() }

            favoriteNavGraph(
                navigateToDetail = navigateToDetail,
                isUserLoggedIn = isUserLoggedIn,
            )

            profileNavGraph(
                isUserLoggedIn = isUserLoggedIn,
                openLogInSheet = openLogInSheet,
                onLogOut = onLogOut
            )
        }
    }
}