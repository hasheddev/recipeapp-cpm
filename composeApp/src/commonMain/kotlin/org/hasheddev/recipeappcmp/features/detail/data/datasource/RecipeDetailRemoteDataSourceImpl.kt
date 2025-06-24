package org.hasheddev.recipeappcmp.features.detail.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.hasheddev.recipeappcmp.features.common.data.api.BASE_URL
import org.hasheddev.recipeappcmp.features.common.data.api.mapper.toRecipeItem
import org.hasheddev.recipeappcmp.features.common.data.models.RecipeApiResponse
import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem
import org.hasheddev.recipeappcmp.features.detail.domain.RecipeDetailRemoteDataSource

class RecipeDetailRemoteDataSourceImpl(
    private val httpClient: HttpClient
): RecipeDetailRemoteDataSource {
    override suspend fun getRecipeDetail(id: Long): RecipeItem? {
       val response = httpClient.get("${BASE_URL}lookup.php?i=$id")
        return response.body<RecipeApiResponse>().meals.firstOrNull()?.toRecipeItem()
    }
}