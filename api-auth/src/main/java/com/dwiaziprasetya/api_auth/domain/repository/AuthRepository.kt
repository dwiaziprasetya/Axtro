package com.dwiaziprasetya.api_auth.domain.repository

import com.dwiaziprasetya.api_auth.domain.model.User
import com.dwiaziprasetya.core_common.util.AppResult

interface AuthRepository {
    suspend fun registerWithEmail(
        email: String,
        password: String
    ): AppResult<User>

    suspend fun loginWithEmail(
        email: String,
        password: String
    ) : AppResult<User>

    suspend fun loginWithGoogle(idToken: String): AppResult<Unit>

    suspend fun getCurrentUser(): AppResult<User>

    suspend fun logout(): AppResult<Unit>
}