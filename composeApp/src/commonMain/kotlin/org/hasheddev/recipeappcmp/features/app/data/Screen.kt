package org.hasheddev.recipeappcmp.features.app.data

import kotlinx.serialization.Serializable
import org.hasheddev.recipeappcmp.features.detail.navigation.RECIPE_ID_ARG
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import recipeapp_cpm.composeapp.generated.resources.Res
import recipeapp_cpm.composeapp.generated.resources.bookmark_selected
import recipeapp_cpm.composeapp.generated.resources.bookmark_unselected
import recipeapp_cpm.composeapp.generated.resources.detail
import recipeapp_cpm.composeapp.generated.resources.favorites
import recipeapp_cpm.composeapp.generated.resources.home
import recipeapp_cpm.composeapp.generated.resources.home_selected
import recipeapp_cpm.composeapp.generated.resources.home_unselected
import recipeapp_cpm.composeapp.generated.resources.profile
import recipeapp_cpm.composeapp.generated.resources.profile_selected
import recipeapp_cpm.composeapp.generated.resources.profile_unselected
import recipeapp_cpm.composeapp.generated.resources.search
import recipeapp_cpm.composeapp.generated.resources.tabs

sealed class Screen(
    val route: String,
    val resourceId: StringResource,
    val selectedIcon: DrawableResource? = null,
    val unSelectedIcon: DrawableResource? = null,
) {
    data object Search: Screen("search", Res.string.search)
    data object Tabs: Screen("tabs", Res.string.tabs)
    data object Detail: Screen("detail?$RECIPE_ID_ARG={$RECIPE_ID_ARG}", Res.string.detail)


    data object Home: Screen(
        "home",
        Res.string.home,
        Res.drawable.home_selected,
        Res.drawable.home_unselected
    )

    data object Favorites: Screen(
        "favorites",
        Res.string.favorites,
        Res.drawable.bookmark_selected,
        Res.drawable.bookmark_unselected
    )

    data object Profile: Screen(
        "profile",
        Res.string.profile,
        Res.drawable.profile_selected,
        Res.drawable.profile_unselected
    )
}