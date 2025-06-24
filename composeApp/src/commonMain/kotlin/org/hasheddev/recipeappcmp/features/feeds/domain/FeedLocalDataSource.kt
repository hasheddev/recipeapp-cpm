package org.hasheddev.recipeappcmp.features.feeds.domain

import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem

interface FeedLocalDataSource {
    suspend fun getRecipesList(): List<RecipeItem>
    suspend fun saveRecipes(recipeItems: List<RecipeItem>)
}