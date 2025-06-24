package org.hasheddev.recipeappcmp.features.favorites.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.hasheddev.recipeappcmp.features.favorites.domain.FavoriteRecipeRepository

class FavoriteScreenViewModel(
    private val favoriteRecipeRepository: FavoriteRecipeRepository
): ViewModel() {
    private val _state = MutableStateFlow(FavoriteScreenUiState())
    val favoriteState = _state.asStateFlow()


    init {
        viewModelScope.launch {
            getRecipesList()
        }
    }

    suspend fun getRecipesList() {
        _state.update {
            it.copy(isLoading = true)
        }
        val recipes = favoriteRecipeRepository.getAllFavoriteRecipes()
        if (recipes.isSuccess) {
            _state.update {
                it.copy(itemList = recipes.getOrDefault(emptyList()), isLoading = false)
            }
        } else {
            _state.update {
                it.copy(
                    isLoading = false,
                    listError = recipes.exceptionOrNull()?.message
                )
            }
        }
    }
}