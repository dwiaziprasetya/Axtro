package com.example.axtro.domain.usecase

import com.example.axtro.core.util.AppResult
import com.example.axtro.domain.repository.AuthRepository

class Logout(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(): AppResult<Unit> {
        return authRepository.logout()
    }
}