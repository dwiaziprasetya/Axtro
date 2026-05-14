package com.dwiaziprasetya.api_auth.domain.usecase

import com.dwiaziprasetya.axtro.core.util.AppResult
import com.dwiaziprasetya.axtro.domain.repository.AuthRepository

class LoginWithGoogle (
    private val repository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): AppResult<Unit> {
        return repository.loginWithGoogle(idToken)
    }
}