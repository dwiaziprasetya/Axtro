package com.dwiaziprasetya.axtro.presentation.home

import com.dwiaziprasetya.axtro.domain.model.Task

data class HomeUiState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val userName: String? = null,
    val email: String = "",
    val userPhotoUrl: String? = null,
    val isUserLoading: Boolean = true,
    val isLogoutLoading: Boolean = false,
    val isLogoutSuccess: Boolean = false
)
