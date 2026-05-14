package com.dwiaziprasetya.feature_signin.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwiaziprasetya.api_auth.domain.usecase.LoginWithEmail
import com.dwiaziprasetya.api_auth.domain.usecase.LoginWithGoogle
import com.dwiaziprasetya.core_common.util.AppResult
import com.dwiaziprasetya.core_ui.util.SnackbarController
import com.dwiaziprasetya.core_ui.util.SnackbarEvent
import com.dwiaziprasetya.core_ui.util.SnackbarType
import com.dwiaziprasetya.feature_signin.state.SignInUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginWithEmail: LoginWithEmail,
    private val loginWithGoogle: LoginWithGoogle
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
            _state.update { it.copy(isLoadingEmailSignIn = true, error = null) }

            val result = loginWithEmail(
                _state.value.email,
                _state.value.password
            )

            when (result) {
                is AppResult.Success -> {
                    _state.update {
                        it.copy(
                            isLoadingEmailSignIn = false,
                            isSuccess = true
                        )
                    }

                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = "Logged in successfully",
                            type = SnackbarType.SUCCESS
                        )
                    )
                }

                is AppResult.Error -> {
                    _state.update {
                        it.copy(
                            isLoadingEmailSignIn = false,
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

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingGoogleSignIn = true, error = null) }

            val result = loginWithGoogle(idToken)

            when (result) {
                is AppResult.Success -> {
                    _state.update {
                        it.copy(
                            isLoadingGoogleSignIn = false,
                            isSuccess = true
                        )
                    }

                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = "Logged in successfully",
                            type = SnackbarType.SUCCESS
                        )
                    )
                }

                is AppResult.Error -> {
                    _state.update {
                        it.copy(
                            isLoadingGoogleSignIn = false,
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

    fun resetSuccess() {
        _state.update { it.copy(isSuccess = false) }
    }
}