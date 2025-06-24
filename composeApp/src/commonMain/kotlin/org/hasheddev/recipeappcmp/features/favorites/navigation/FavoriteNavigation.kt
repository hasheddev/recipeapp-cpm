package org.hasheddev.recipeappcmp.features.favorites.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.hasheddev.recipeappcmp.features.app.data.Screen
import org.hasheddev.recipeappcmp.features.favorites.ui.FavoriteRoute

fun NavController.navigateToFavorite(navOptions: NavOptions? = null) {
    navigate(Screen.Favorites.route)
}

fun NavGraphBuilder.favoriteNavGraph(
    navigateToDetail: (Long) -> Unit,
    isUserLoggedIn: () -> Boolean
) {
    composable(Screen.Favorites.route) {
        FavoriteRoute(
            navigateToDetail = navigateToDetail,
            isUserLoggedIn = isUserLoggedIn
        )
    }
}