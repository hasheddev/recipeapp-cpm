package org.hasheddev.recipeappcmp.features.common.data.db.util

import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem
import orghasheddevrecipeappcmp.Recipe

fun recipeEntityMapper(recipe: Recipe) = RecipeItem(
    id = recipe.id,
    title = recipe.title,
    description = recipe.description,
    category = recipe.category,
    area= recipe.area,
    imageUrl= recipe.imageUrl,
    youtubeLink= recipe.youtubeLink,
    isFavorite =  recipe.isFavorite == 1L,
    ingredients = recipe.ingredients,
    instructions = recipe.instructions,
    rating = recipe.rating,
    duration = recipe.duration ?: "20 mins",
    difficulty = recipe.difficulty?: "Easy"
)