package org.hasheddev.recipeappcmp.features.search.domain.repository

import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem

interface SearchRecipeRepository {
    suspend fun searchRecipeByText(query: String): Result<List<RecipeItem>>
}