package org.hasheddev.recipeappcmp.features.feeds.data.datasource

import org.hasheddev.recipeappcmp.features.common.data.db.daos.RecipeDao
import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem
import org.hasheddev.recipeappcmp.features.feeds.domain.FeedLocalDataSource

class FeedLocalDataSourceImpl(
    private val recipeDao: RecipeDao
): FeedLocalDataSource {
    override suspend fun getRecipesList(): List<RecipeItem> {
        return recipeDao.getAllRecipes()
    }

    override suspend fun saveRecipes(recipeItems: List<RecipeItem>) {
        recipeDao.insertRecipes(recipeItems)
    }
}