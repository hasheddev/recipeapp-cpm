package org.hasheddev.recipeappcmp.features.detail.domain

import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem

interface RecipeDetailRemoteDataSource {
    suspend fun getRecipeDetail(id: Long): RecipeItem?
}