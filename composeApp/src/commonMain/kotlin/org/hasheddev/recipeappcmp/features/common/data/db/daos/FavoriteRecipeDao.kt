package org.hasheddev.recipeappcmp.features.common.data.db.daos

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.hasheddev.recipeappcmp.features.common.data.db.DbHelper
import org.hasheddev.recipeappcmp.features.common.data.db.util.recipeEntityMapper
import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem

class FavoriteRecipeDao(
    private val dbHelper: DbHelper
) {
    suspend fun addFavorite(recipeId: Long) {
        val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        dbHelper.withDatabase { db ->
            db.favoriteRecipeQueries.upsertRecipe(
                recipe_id = recipeId,
                added_at = currentDateTime.toString()
            )
        }
    }

    suspend fun deleteFavorite(id: Long) {
        return dbHelper.withDatabase { db ->
            db.favoriteRecipeQueries.deleteFavoriteByRecipeId(recipe_id = id)
        }
    }

    suspend fun getAllFavoriteRecipes(): List<RecipeItem> {
        return dbHelper.withDatabase { db ->
            db.favoriteRecipeQueries.selectAllFavoriteRecipes().awaitAsList().map {
                recipeEntityMapper(it)
            }
        }
    }

    suspend fun isFavorite(id: Long): Boolean {
        return dbHelper.withDatabase { db ->
            db.favoriteRecipeQueries.selectFavoriteRecipeByRecipeId(id)
                .awaitAsOneOrNull() != null
        }
    }

}
