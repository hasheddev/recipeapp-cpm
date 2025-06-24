package org.hasheddev.recipeappcmp.features.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.hasheddev.recipeappcmp.features.profile.domain.User

class ProfileViewModel(): ViewModel() {

    private val _refresh = MutableStateFlow(false)
    private val refresh = _refresh.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val profileState: StateFlow<ProfileUiState> = combine(refresh) { _ ->
        getUserInfo()
    }.flatMapLatest { it }.stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(5000), ProfileUiState())



    fun refresh() {
        _refresh.update { true }
    }

    private fun getUserInfo() = flow {
        emit(ProfileUiState(isLoading = true))
        delay(1000)
        emit(ProfileUiState(
            isLoading = false,
            isLoggedIn = true,
            userInfo = User(
                "1",
                "Scales Lone",
                "Scales@bit.com",
                10,
                84,
                119
            )
        ))
    }

}