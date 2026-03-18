package com.dwiaziprasetya.axtro.domain.usecase

import com.dwiaziprasetya.axtro.core.util.AppResult
import com.dwiaziprasetya.axtro.domain.model.User
import com.dwiaziprasetya.axtro.domain.repository.AuthRepository

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