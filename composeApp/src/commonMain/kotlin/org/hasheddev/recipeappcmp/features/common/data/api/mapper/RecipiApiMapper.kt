package org.hasheddev.recipeappcmp.features.common.data.api.mapper

import org.hasheddev.recipeappcmp.features.common.data.models.RecipeApiItem
import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem

fun RecipeApiItem.toRecipeItem(): RecipeItem?{
    return if(idMeal != null)
        RecipeItem(
            id = idMeal.toLong(),
            title = strMeal ?: "",
            description = strMeal ?: "",
            category = strCategory ?: "",
            area= strArea ?: "",
            imageUrl= strImageSource ?: "",
            youtubeLink= strYoutube ?: "",
            isFavorite =  false,
            ingredients = generateListIngredient(),
            instructions = generateInstructionList(),
            rating = 3L

        )
            else null
}

fun RecipeApiItem.generateListIngredient(): List<String> {
    return listOfNotNull(
        "$strIngredient1: $strMeasure1",
        "$strIngredient2: $strMeasure2",
        "$strIngredient3: $strMeasure3",
        "$strIngredient4: $strMeasure4",
        "$strIngredient5: $strMeasure5",
        "$strIngredient6: $strMeasure6",
        "$strIngredient7: $strMeasure7",
        "$strIngredient8: $strMeasure8",
        "$strIngredient9: $strMeasure9",
        "$strIngredient10: $strMeasure10",
        "$strIngredient11: $strMeasure11",
        "$strIngredient12: $strMeasure12",
        "$strIngredient13: $strMeasure13",
        "$strIngredient14: $strMeasure14",
        )
}

fun RecipeApiItem.generateInstructionList(): List<String> {
    return strInstructions?.split(",")?.map {
        it.trim().replace("\r\n", "")
            .capitalizeFirstWord()
    }?.filter { it.isNotEmpty() }
        ?: emptyList<String>()
}

fun String.capitalizeFirstWord() = this.replaceFirstChar { it.uppercase() }