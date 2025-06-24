package org.hasheddev.recipeappcmp.features.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.savedstate.read
import org.hasheddev.recipeappcmp.features.app.data.Screen
import org.hasheddev.recipeappcmp.features.detail.ui.DetailRoute

const val RECIPE_ID_ARG = "recipeId"

fun NavController.navigateToDetail(id: Long, navOptions: NavOptions? = null) {
    navigate(Screen.Detail.route.replace("$RECIPE_ID_ARG={$RECIPE_ID_ARG}", "$RECIPE_ID_ARG=${id}"))
}

fun NavGraphBuilder.detailNavGraph(
    onBackClick: () -> Unit,
    isUserLoggedIn: () -> Boolean,
    openLogInSheet: (() -> Unit) -> Unit
) {
    composable(
        Screen.Detail.route,
        arguments = listOf(
            navArgument(RECIPE_ID_ARG) {
                type = NavType.LongType
            }
        )
    ) {
        val recipeId = it.arguments?.read { getLong(RECIPE_ID_ARG) } ?: 0
        DetailRoute(
            recipeId,
            onBackClick,
            isUserLoggedIn = isUserLoggedIn,
            openLogInSheet = openLogInSheet,
        )
    }
}