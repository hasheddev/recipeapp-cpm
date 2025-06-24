package org.hasheddev.recipeappcmp.features.search.data.datasource

import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem

interface SearchRecipeDataSource {
    suspend fun searchRecipeByText(query: String): List<RecipeItem>
}