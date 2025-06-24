package org.hasheddev.recipeappcmp.features.search.data.repository

import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem
import org.hasheddev.recipeappcmp.features.search.data.datasource.SearchRecipeDataSource
import org.hasheddev.recipeappcmp.features.search.domain.repository.SearchRecipeRepository

class SearchRecipeRepositoryImpl(
    private val searchRecipeLocalDataSource: SearchRecipeDataSource
): SearchRecipeRepository {
    override suspend fun searchRecipeByText(query: String): Result<List<RecipeItem>>{
       return try {
            val recipes = searchRecipeLocalDataSource.searchRecipeByText(query)
           Result.success(recipes)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
}