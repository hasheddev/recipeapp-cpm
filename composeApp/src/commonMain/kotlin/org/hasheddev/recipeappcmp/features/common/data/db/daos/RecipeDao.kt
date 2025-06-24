package org.hasheddev.recipeappcmp.features.common.data.db.daos

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import org.hasheddev.recipeappcmp.features.common.data.db.DbHelper
import org.hasheddev.recipeappcmp.features.common.data.db.util.recipeEntityMapper
import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem

class RecipeDao(
    private val dbHelper: DbHelper
) {
    suspend fun insertRecipe(recipeItem: RecipeItem) {
        dbHelper.withDatabase { database ->
            database.recipeEntityQueries.insertRecipe(
                recipeItem.id,
                recipeItem.title,
                recipeItem.description,
                recipeItem.category,
                recipeItem.area,
                recipeItem.imageUrl,
                recipeItem.youtubeLink,
                recipeItem.ingredients,
                recipeItem.instructions,
                isFavorite = if (recipeItem.isFavorite) 1 else 0,
                recipeItem.rating,
                recipeItem.duration,
                recipeItem.difficulty
            )
        }
    }

    suspend fun updateRecipe(recipeItem: RecipeItem) {
        dbHelper.withDatabase { database ->
            database.recipeEntityQueries.updateRecipe(
                recipeItem.title,
                recipeItem.description,
                recipeItem.category,
                recipeItem.area,
                recipeItem.imageUrl,
                recipeItem.youtubeLink,
                recipeItem.ingredients,
                recipeItem.instructions,
                isFavorite = if (recipeItem.isFavorite) 1 else 0,
                recipeItem.rating,
                recipeItem.duration,
                recipeItem.difficulty,
                recipeItem.id
            )
        }
    }

    suspend fun insertRecipes(recipes: List<RecipeItem>) {
        recipes.forEach { recipeItem ->
            dbHelper.withDatabase { database ->
                database.recipeEntityQueries.insertRecipe(
                    recipeItem.id,
                    recipeItem.title,
                    recipeItem.description,
                    recipeItem.category,
                    recipeItem.area,
                    recipeItem.imageUrl,
                    recipeItem.youtubeLink,
                    recipeItem.ingredients,
                    recipeItem.instructions,
                    isFavorite = if (recipeItem.isFavorite) 1 else 0,
                    recipeItem.rating,
                    recipeItem.duration,
                    recipeItem.difficulty
                )
            }
        }
    }

    suspend fun upsertRecipes(recipes: List<RecipeItem>) {
        recipes.forEach { recipeItem ->
            dbHelper.withDatabase { database ->
                database.recipeEntityQueries.upsertRecipe(
                    recipeItem.title,
                    recipeItem.description,
                    recipeItem.category,
                    recipeItem.area,
                    recipeItem.imageUrl,
                    recipeItem.youtubeLink,
                    recipeItem.ingredients,
                    recipeItem.instructions,
                    isFavorite = if (recipeItem.isFavorite) 1 else 0,
                    recipeItem.rating,
                    recipeItem.duration,
                    recipeItem.difficulty,
                    recipeItem.id
                )
            }
        }
    }

    suspend fun getAllRecipes(): List<RecipeItem> {
        return dbHelper.withDatabase { db ->
            db.recipeEntityQueries.selectAllRecipes().awaitAsList().map {
                recipeEntityMapper(it)
            }
        }
    }

    suspend fun getRecipeById(id: Long): RecipeItem? {
        return dbHelper.withDatabase { db ->
            db.recipeEntityQueries.selectRecipeById(id).awaitAsOneOrNull()?.let {
                recipeEntityMapper(it)
            }
        }
    }

    suspend fun searchResultByText(text: String): List<RecipeItem> {
        return dbHelper.withDatabase { db ->
            db.recipeEntityQueries.searchecipeByText(text).awaitAsList().map {
                recipeEntityMapper(it)
            }
        }
    }

    suspend fun deleteRecipeById(id: Long) {
        return dbHelper.withDatabase { db ->
            db.recipeEntityQueries.deleteRecipeById(id)
        }
    }
}