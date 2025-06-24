package org.hasheddev.recipeappcmp.features.app.data

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import recipeapp_cpm.composeapp.generated.resources.Res
import recipeapp_cpm.composeapp.generated.resources.detail
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
    data object Detail: Screen("detail", Res.string.detail)


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