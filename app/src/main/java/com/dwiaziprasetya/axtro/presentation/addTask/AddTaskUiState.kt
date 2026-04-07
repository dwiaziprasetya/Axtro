package com.dwiaziprasetya.axtro.presentation.addTask

data class AddTaskUiState(
    val title: String = "",
    val description: String = "",
    val day: Int? = null,
    val month: Int? = null,
    val year: Int? = null,
    val priority: String = "Low",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)