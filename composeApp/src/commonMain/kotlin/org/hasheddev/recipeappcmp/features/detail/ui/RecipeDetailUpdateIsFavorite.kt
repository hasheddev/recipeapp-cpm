package org.hasheddev.recipeappcmp.features.detail.ui

import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem

data class RecipeDetailUpdateIsFavorite(
    val isSuccess: Boolean? = null,
    val isUpdating: Boolean = true,
    val errorDetail: String? = null
)
