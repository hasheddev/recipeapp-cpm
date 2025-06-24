package org.hasheddev.recipeappcmp.features.feeds.domain.repository

import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem

interface FeedRepository {
    suspend fun getRecipesList(): Result<List<RecipeItem>>
}