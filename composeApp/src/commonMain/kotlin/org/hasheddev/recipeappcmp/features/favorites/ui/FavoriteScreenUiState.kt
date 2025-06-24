package org.hasheddev.recipeappcmp.features.favorites.ui

import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem

data class FavoriteScreenUiState(
    val itemList: List<RecipeItem>? = null,
    val isLoading: Boolean = true,
    val listError: String? = null
)