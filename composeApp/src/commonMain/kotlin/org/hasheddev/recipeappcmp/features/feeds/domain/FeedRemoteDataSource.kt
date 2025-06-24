package org.hasheddev.recipeappcmp.features.feeds.domain

import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem

interface FeedRemoteDataSource {
    suspend fun getRecipesList(): List<RecipeItem>
}