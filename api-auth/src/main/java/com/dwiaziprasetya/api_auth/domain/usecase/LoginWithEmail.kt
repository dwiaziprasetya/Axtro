package com.dwiaziprasetya.api_auth.domain.usecase

import com.dwiaziprasetya.api_auth.domain.model.User
import com.dwiaziprasetya.api_auth.domain.repository.AuthRepository
import com.dwiaziprasetya.core_common.util.AppResult

class LoginWithEmail(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): AppResult<User> {

        if (email.isBlank() || password.isBlank()) {
            return AppResult.Error("Email and password cannot be empty")
        }

        return repository.loginWithEmail(email, password)
    }
}