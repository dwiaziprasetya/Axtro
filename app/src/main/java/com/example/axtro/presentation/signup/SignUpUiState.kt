package com.example.axtro.presentation.signup

data class SignUpUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)