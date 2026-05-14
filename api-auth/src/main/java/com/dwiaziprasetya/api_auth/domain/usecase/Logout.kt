package com.dwiaziprasetya.api_auth.domain.usecase

import com.dwiaziprasetya.axtro.core.util.AppResult
import com.dwiaziprasetya.axtro.domain.repository.AuthRepository

class Logout(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(): AppResult<Unit> {
        return authRepository.logout()
    }
}