package com.example.axtro.domain.usecase

import com.example.axtro.core.util.AppResult
import com.example.axtro.domain.model.User
import com.example.axtro.domain.repository.AuthRepository

class LoginWithEmail(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): AppResult<User> {

        if (email.isBlank() || password.isBlank()) {
            return AppResult.Error("Email & password tidak boleh kosong")
        }

        return repository.loginWithEmail(email, password)
    }
}