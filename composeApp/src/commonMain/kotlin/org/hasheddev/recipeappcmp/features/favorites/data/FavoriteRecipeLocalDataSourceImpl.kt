package org.hasheddev.recipeappcmp.features.favorites.data

import org.hasheddev.recipeappcmp.features.common.data.db.daos.FavoriteRecipeDao
import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem
import org.hasheddev.recipeappcmp.features.favorites.domain.FavoriteRecipeLocalDataSource

class FavoriteRecipeLocalDataSourceImpl(
    private val favoriteRecipeDao: FavoriteRecipeDao
):FavoriteRecipeLocalDataSource {
    override suspend fun getAllFavoriteRecipes(): List<RecipeItem> {
        return favoriteRecipeDao.getAllFavoriteRecipes()
    }

    override suspend fun addFavorite(recipeId: Long) {
        return favoriteRecipeDao.addFavorite(recipeId)
    }

    override suspend fun removeFavorite(recipeId: Long) {
        favoriteRecipeDao.deleteFavorite(recipeId)
    }
}