package org.hasheddev.recipeappcmp.features.feeds.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.hasheddev.recipeappcmp.features.common.data.api.BASE_URL
import org.hasheddev.recipeappcmp.features.common.data.api.mapper.toRecipeItem
import org.hasheddev.recipeappcmp.features.common.data.models.RecipeApiResponse
import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem
import org.hasheddev.recipeappcmp.features.feeds.domain.FeedRemoteDataSource

class FeedRemoDataSourceImpl(
    private val httpClient: HttpClient
): FeedRemoteDataSource {
    override suspend fun getRecipesList(): List<RecipeItem> {
        val response = httpClient.get("{$BASE_URL}search.php?f=b")
        val recipeListApiResponse = response.body<RecipeApiResponse>()
        return recipeListApiResponse.meals.mapNotNull {
            it.toRecipeItem()
        }
    }
}