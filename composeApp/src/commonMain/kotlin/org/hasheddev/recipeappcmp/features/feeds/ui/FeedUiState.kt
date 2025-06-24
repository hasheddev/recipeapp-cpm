package org.hasheddev.recipeappcmp.features.feeds.ui

import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem

data class FeedUiState(
    val recipeList: List<RecipeItem>? = null,
    val isLoading: Boolean = true,
    val recipeError: String? = null
)
