package com.example.axtro.presentation.addTask

data class AddTaskUiState(
    val title: String = "",
    val day: Int = 0,
    val month: Int = 0,
    val year: Int = 0,
    val priority: String = "Low",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)