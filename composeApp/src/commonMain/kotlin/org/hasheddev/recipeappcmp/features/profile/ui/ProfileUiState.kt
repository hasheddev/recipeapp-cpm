package org.hasheddev.recipeappcmp.features.profile.ui

import org.hasheddev.recipeappcmp.features.profile.domain.User

data class ProfileUiState (
        val userInfo: User? = null,
        val isLoggedIn: Boolean = false,
        val isLoading: Boolean = false,
        val error: String? = null
)