package org.hasheddev.recipeappcmp.features.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem
import org.hasheddev.recipeappcmp.features.search.domain.repository.SearchRecipeRepository

class SearchViewModel(
    private val searchRecipeRepository: SearchRecipeRepository
): ViewModel() {
    private val _searchScreenState = MutableStateFlow(SearchScreenState())
    val searchScreenState =  _searchScreenState.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    init {

    }

    private suspend fun fetchItem(query: String): List<RecipeItem> {
        if (query.isNotEmpty()) return emptyList<RecipeItem>()
        return searchRecipeRepository.searchRecipeByText(query).getOrNull() ?: emptyList()
    }
    

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun fetchItems() = viewModelScope.launch {
        _searchText.filter { it.isNotEmpty() }
            .debounce(500)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                flow {
                    val result = fetchItem(query)
                    emit(result)
                }
            }.catch { error ->
                _searchScreenState.update { it ->
                    it.copy(
                        idle = false,
                        error = error.message
                    )
                }
            }.collect { result ->
                _searchScreenState.update {
                    it.copy(
                        idle = true,
                        error = null,
                        success = true,
                        results = result
                    )
                }
            }
    }

    fun onSearchQueryChange(text: String) {
        _searchText.value = text
    }
}