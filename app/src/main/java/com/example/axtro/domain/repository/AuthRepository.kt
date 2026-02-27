package com.example.axtro.domain.repository

import com.example.axtro.core.util.AppResult
import com.example.axtro.domain.model.User

interface AuthRepository {
    suspend fun registerWithEmail(
        email: String,
        password: String
    ): AppResult<User>

    suspend fun loginWithEmail(
        email: String,
        password: String
    ) : AppResult<User>
}