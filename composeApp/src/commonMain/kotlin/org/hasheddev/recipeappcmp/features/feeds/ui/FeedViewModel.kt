package org.hasheddev.recipeappcmp.features.feeds.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.hasheddev.recipeappcmp.features.feeds.domain.repository.FeedRepository

class FeedViewModel(
    private val feedRepository: FeedRepository
) : ViewModel() {
    private val _feedUiState = MutableStateFlow(FeedUiState())
    val feedUiState = _feedUiState.asStateFlow()

    init {
        viewModelScope.launch {
            getRecipesList()
        }
    }

    private suspend fun getRecipesList() {
        _feedUiState.update {
            it.copy(isLoading = true)
        }
        val recipes = feedRepository.getRecipesList()
        if(recipes.isSuccess) {
            _feedUiState.update {
                it.copy(recipeList = recipes.getOrDefault(emptyList()), isLoading = false)
            }
        } else {
            _feedUiState.update {
                it.copy(
                    isLoading = false,
                    recipeError = recipes.exceptionOrNull()?.message
                )
            }
        }
    }
}