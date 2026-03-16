package com.example.axtro.presentation.home

import com.example.axtro.domain.model.Task

data class HomeUiState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val userName: String = "",
    val userPhotoUrl: String? = null,
    val isUserLoading: Boolean = true
)
