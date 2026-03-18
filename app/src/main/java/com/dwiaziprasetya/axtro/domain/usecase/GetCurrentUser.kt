package com.dwiaziprasetya.axtro.domain.usecase

import com.dwiaziprasetya.axtro.core.util.AppResult
import com.dwiaziprasetya.axtro.domain.model.User
import com.dwiaziprasetya.axtro.domain.repository.AuthRepository

class GetCurrentUser(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): AppResult<User> {
        return repository.getCurrentUser()
    }
}