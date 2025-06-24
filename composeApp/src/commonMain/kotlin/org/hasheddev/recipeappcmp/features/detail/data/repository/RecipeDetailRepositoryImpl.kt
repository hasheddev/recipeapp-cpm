package org.hasheddev.recipeappcmp.features.detail.data.repository

import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem
import org.hasheddev.recipeappcmp.features.detail.domain.RecipeDetailLocalDataSource
import org.hasheddev.recipeappcmp.features.detail.domain.RecipeDetailRemoteDataSource
import org.hasheddev.recipeappcmp.features.detail.domain.repository.RecipeDetailRepository

class RecipeDetailRepositoryImpl(
    private val localDataSource: RecipeDetailLocalDataSource,
    private val remoteDataSource: RecipeDetailRemoteDataSource
): RecipeDetailRepository {
    override suspend fun getRecipeDetail(id: Long): Result<RecipeItem> {
        try {
            val recipeListLocal = localDataSource.getRecipeDetail(id)
            if(recipeListLocal != null) {
                val isFav = localDataSource.isFavorite(recipeId = id)
                return Result.success(recipeListLocal.copy(isFavorite = isFav))
            } else {
                val remoteApiRecipe = remoteDataSource.getRecipeDetail(id)
                remoteApiRecipe?: return Result.failure(Exception("Recipe not found"))
                localDataSource.saveRecipe(remoteApiRecipe)
                return Result.success(remoteApiRecipe)
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun addFavorite(id: Long) {
        localDataSource.addFavorite(id)
    }

    override suspend fun removeFavorite(id: Long) {
        localDataSource.removeFavorite(id)
    }
}