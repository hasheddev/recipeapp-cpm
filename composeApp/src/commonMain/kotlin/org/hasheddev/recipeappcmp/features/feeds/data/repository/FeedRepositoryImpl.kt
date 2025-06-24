package org.hasheddev.recipeappcmp.features.feeds.data.repository

import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem
import org.hasheddev.recipeappcmp.features.feeds.domain.FeedLocalDataSource
import org.hasheddev.recipeappcmp.features.feeds.domain.FeedRemoteDataSource
import org.hasheddev.recipeappcmp.features.feeds.domain.repository.FeedRepository

class FeedRepositoryImpl(
    private val feedLocalDataSource: FeedLocalDataSource,
    private val feedRemoteDataSource: FeedRemoteDataSource
): FeedRepository {
    override suspend fun getRecipesList(): Result<List<RecipeItem>> {
        try {
            val recipeListLocal = feedLocalDataSource.getRecipesList()
            if(recipeListLocal.isNotEmpty()) {
                return Result.success(recipeListLocal)
            } else {
                val remoteApiRecipes = feedRemoteDataSource.getRecipesList()
                feedLocalDataSource.saveRecipes(remoteApiRecipes)
                return Result.success(remoteApiRecipes)
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }


    }

}