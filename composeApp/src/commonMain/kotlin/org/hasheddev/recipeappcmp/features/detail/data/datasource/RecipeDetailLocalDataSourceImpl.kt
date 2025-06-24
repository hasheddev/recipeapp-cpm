package org.hasheddev.recipeappcmp.features.detail.data.datasource

import org.hasheddev.recipeappcmp.features.common.data.db.daos.FavoriteRecipeDao
import org.hasheddev.recipeappcmp.features.common.data.db.daos.RecipeDao
import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem
import org.hasheddev.recipeappcmp.features.detail.domain.RecipeDetailLocalDataSource

class RecipeDetailLocalDataSourceImpl(
    private val recipeDao: RecipeDao,
    private val favoriteRecipeDao: FavoriteRecipeDao
): RecipeDetailLocalDataSource {
    override suspend fun getRecipeDetail(id: Long): RecipeItem? {
        return recipeDao.getRecipeById(id)
    }

    override suspend fun saveRecipe(recipe: RecipeItem) {
        recipeDao.insertRecipe(recipe)
    }

    override suspend fun addFavorite(id: Long) {
        favoriteRecipeDao.addFavorite(id)
    }

    override suspend fun removeFavorite(id: Long) {
        favoriteRecipeDao.deleteFavorite(id)
    }

    override suspend fun isFavorite(recipeId: Long): Boolean {
        return favoriteRecipeDao.isFavorite(recipeId)
    }
}