package org.hasheddev.recipeappcmp.features.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(): ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.update {
                LoginState.Idle
            }

            try {
                delay(1000)
                if (email == "test@gmail.com" && password == "test") {
                    _loginState.update {
                        LoginState.Success
                    }
                } else {
                    _loginState.update {
                        LoginState.Error("Invalid email or password")
                    }
                }
            } catch (e: Exception)
            {
                _loginState.update {
                    LoginState.Error(e.message?: "An unexpected error in login")
                }
            }
        }
    }

    fun resetState() {
        _loginState.update {
            LoginState.Idle
        }
    }
}