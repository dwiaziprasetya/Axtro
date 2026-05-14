package com.dwiaziprasetya.api_auth.domain.usecase

import com.dwiaziprasetya.api_auth.domain.model.User
import com.dwiaziprasetya.api_auth.domain.repository.AuthRepository
import com.dwiaziprasetya.core_common.util.AppResult


class GetCurrentUser(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): AppResult<User> {
        return repository.getCurrentUser()
    }
}