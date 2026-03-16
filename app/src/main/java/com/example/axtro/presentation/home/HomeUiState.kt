package com.example.axtro.presentation.home

import com.example.axtro.domain.model.Task

data class HomeUiState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = true
)
