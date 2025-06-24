package org.hasheddev.recipeappcmp.features.detail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.hasheddev.recipeappcmp.features.detail.domain.repository.RecipeDetailRepository
import org.hasheddev.recipeappcmp.features.favorites.domain.FavoriteRecipeRepository

class RecipeDetailViewModel(
    private val repository: RecipeDetailRepository,
): ViewModel() {
    private var _detailState = MutableStateFlow(RecipeDetailUiState())
    val detailState = _detailState.asStateFlow()
    private val _updateFavoriteState = MutableStateFlow(RecipeDetailUpdateIsFavorite())
    val updateIsFavoriteState = _updateFavoriteState.asStateFlow()


    fun getRecipeDetail(id: Long) {
        viewModelScope.launch {
            _detailState.update {
                it.copy(
                    isLoading = true
                )
            }
            val response = repository.getRecipeDetail(id)
            if (response.isSuccess) {
                _detailState.update {
                    it.copy(
                        isLoading = false,
                        errorDetail = null,
                        recipeDetail = response.getOrNull()
                    )
                }
            } else {
                _detailState.update {
                    it.copy(
                        isLoading = false,
                        errorDetail = response.exceptionOrNull()?.message
                    )
                }
            }
        }
    }

    fun updateIsFavorite(id: Long, isAdding: Boolean) {
        viewModelScope.launch {
            try {
                _updateFavoriteState.update {
                    it.copy(isUpdating = true)
                }
                if (isAdding) {
                    repository.addFavorite(id)
                } else {
                    repository.removeFavorite(id)
                }

                _detailState.update {
                    it.copy(
                       recipeDetail = it.recipeDetail?.copy(isFavorite = isAdding)
                    )
                }

                _updateFavoriteState.update {
                    it.copy(isUpdating = false, isSuccess = true)
                }
            } catch (e: Exception) {
                _updateFavoriteState.update {
                    it.copy(isUpdating = false, errorDetail = e.message)
                }
            }
        }
    }
}