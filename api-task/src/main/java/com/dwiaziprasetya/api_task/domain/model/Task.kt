package com.dwiaziprasetya.api_task.domain.model

data class Task(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val date: Long = 0L,
    val startTime: Long = 0L,
    val endTime: Long? = null,
    val priority: String = "",
    val status: String = "",
    val userId: String = ""
)