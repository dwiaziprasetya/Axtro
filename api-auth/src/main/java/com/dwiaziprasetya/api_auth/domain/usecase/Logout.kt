package com.dwiaziprasetya.api_auth.domain.usecase

import com.dwiaziprasetya.api_auth.domain.repository.AuthRepository
import com.dwiaziprasetya.core_common.util.AppResult

class Logout(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(): AppResult<Unit> {
        return authRepository.logout()
    }
}