package org.hasheddev.recipeappcmp.features.common.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeApiResponse(
    @SerialName("meals") val meals : List<RecipeApiItem>
)
