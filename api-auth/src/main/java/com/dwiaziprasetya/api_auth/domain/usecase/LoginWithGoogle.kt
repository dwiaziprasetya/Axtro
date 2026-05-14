package com.dwiaziprasetya.api_auth.domain.usecase

import com.dwiaziprasetya.api_auth.domain.repository.AuthRepository
import com.dwiaziprasetya.core_common.util.AppResult

class LoginWithGoogle (
    private val repository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): AppResult<Unit> {
        return repository.loginWithGoogle(idToken)
    }
}