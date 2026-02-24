package com.example.axtro.domain.usecase

import com.example.axtro.core.util.AppResult
import com.example.axtro.domain.model.User
import com.example.axtro.domain.repository.AuthRepository

class LoginWithGoogle(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        idToken: String
    ): AppResult<User> {
        return repository.loginWithGoogle(idToken)
    }
}