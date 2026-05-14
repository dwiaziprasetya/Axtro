package com.dwiaziprasetya.feature_signup.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.dwiaziprasetya.feature_signup.state.SignUpUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerWithEmail: RegisterWithEmail
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpUiState())
    val state: StateFlow<SignUpUiState> = _state.asStateFlow()

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

    fun register() {
        viewModelScope.launch {

            val current = _state.value

            _state.update {
                it.copy(isLoading = true, error = null)
            }

            val result = registerWithEmail(
                current.email.trim(),
                current.password.trim()
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
                            message = "Account created successfully",
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