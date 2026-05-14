package com.dwiaziprasetya.feature_signin.state

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
    val isLoadingEmailSignIn: Boolean = false,
    val isLoadingGoogleSignIn: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)