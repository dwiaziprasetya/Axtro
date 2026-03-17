package com.example.axtro.domain.usecase

import com.example.axtro.core.util.AppResult
import com.example.axtro.domain.model.User
import com.example.axtro.domain.repository.AuthRepository

class GetCurrentUser(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): AppResult<User> {
        return repository.getCurrentUser()
    }
}