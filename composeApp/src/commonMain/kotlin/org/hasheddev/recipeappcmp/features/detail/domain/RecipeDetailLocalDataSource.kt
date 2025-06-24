package org.hasheddev.recipeappcmp.features.detail.domain

import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem

interface RecipeDetailLocalDataSource {
    suspend fun getRecipeDetail(id: Long): RecipeItem?

    suspend fun saveRecipe(recipe: RecipeItem)

    suspend fun addFavorite(id: Long)
    suspend fun removeFavorite(id: Long)

    suspend fun isFavorite(recipeId: Long): Boolean
}