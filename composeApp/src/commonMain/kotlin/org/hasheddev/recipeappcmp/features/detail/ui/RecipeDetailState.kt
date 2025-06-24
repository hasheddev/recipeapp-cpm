package org.hasheddev.recipeappcmp.features.detail.ui

import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem

data class RecipeDetailUiState(
    val isLoading: Boolean = true,
    val recipeDetail: RecipeItem? = null,
    val errorDetail: String? = null
)
