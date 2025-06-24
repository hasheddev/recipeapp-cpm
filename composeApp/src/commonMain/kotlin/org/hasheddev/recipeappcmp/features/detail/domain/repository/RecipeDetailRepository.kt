package org.hasheddev.recipeappcmp.features.detail.domain.repository

import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem

interface RecipeDetailRepository {
    suspend fun getRecipeDetail(id: Long): Result<RecipeItem>
    suspend fun addFavorite(id: Long)
    suspend fun removeFavorite(id: Long)
}