package org.hasheddev.recipeappcmp.features.search.data.datasource

import org.hasheddev.recipeappcmp.features.common.data.db.daos.RecipeDao
import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem

class SearchRecipeLocalDataSourceImpl(
    private val recipeDao: RecipeDao
): SearchRecipeDataSource {
    override suspend fun searchRecipeByText(query: String): List<RecipeItem> {
        return recipeDao.searchResultByText(query)
    }
}