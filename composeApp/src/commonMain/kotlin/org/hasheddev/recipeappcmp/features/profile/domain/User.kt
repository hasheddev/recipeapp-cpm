package org.hasheddev.recipeappcmp.features.profile.domain

data class User(
    val id: String,
    val name: String,
    val email: String,
    val recipeCount: Int,
    val favoriteRecipeCount: Int,
    val followers: Int

)
