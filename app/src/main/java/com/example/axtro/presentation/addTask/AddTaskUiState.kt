package com.example.axtro.presentation.addTask

data class AddTaskUiState(
    val title: String = "",
    val date: Long = 0L,
    val priority: String = "LOW",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)