package org.hasheddev.recipeappcmp.features.favorites.domain

import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem

interface FavoriteRecipeLocalDataSource {
    suspend fun getAllFavoriteRecipes(): List<RecipeItem>

    suspend fun addFavorite(recipeId: Long)

    suspend fun removeFavorite(recipeId: Long)
}