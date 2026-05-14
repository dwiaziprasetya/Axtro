package com.dwiaziprasetya.api_auth.domain.usecase

import com.dwiaziprasetya.api_auth.domain.model.User
import com.dwiaziprasetya.api_auth.domain.repository.AuthRepository
import com.dwiaziprasetya.core_common.util.AppResult

class RegisterWithEmail(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): AppResult<User> {

        if (email.isBlank() || password.isBlank()) {
            return AppResult.Error("Email and password cannot be empty")
        }

        if (password.length < 6) {
            return AppResult.Error("Password must be at least 6 characters")
        }

        return repository.registerWithEmail(email, password)
    }
}