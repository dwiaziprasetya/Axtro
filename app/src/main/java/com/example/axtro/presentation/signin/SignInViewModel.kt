package com.example.axtro.presentation.signin

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.axtro.core.util.AppResult
import com.example.axtro.core.util.SnackbarController
import com.example.axtro.core.util.SnackbarEvent
import com.example.axtro.core.util.SnackbarType
import com.example.axtro.domain.usecase.LoginWithEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginWithEmail: LoginWithEmail
) : ViewModel() {

    private val _state = MutableStateFlow(SignInUiState())
    val state: StateFlow<SignInUiState> = _state

    fun onEmailChange(value: String) {
        val isValid = Patterns.EMAIL_ADDRESS.matcher(value).matches()

        _state.update {
            it.copy(
                email = value,
                emailError = value.isNotEmpty() && !isValid
            )
        }
    }

    fun onPasswordChange(value: String) {
        _state.update {
            it.copy(
                password = value,
                passwordError = value.contains(" ")
            )
        }
    }

    fun signInWithEmail() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val result = loginWithEmail(
                _state.value.email,
                _state.value.password
            )

            when (result) {
                is AppResult.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = true
                        )
                    }

                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = "Success login",
                            type = SnackbarType.SUCCESS
                        )
                    )
                }

                is AppResult.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }

                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.message,
                            type = SnackbarType.ERROR
                        )
                    )
                }
            }
        }
    }
}