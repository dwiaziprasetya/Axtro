package com.dwiaziprasetya.axtro.domain.model

data class Task(
    val userId: String = "",
    val id: String = "",
    val title: String = "",
    val date: Long = 0L,
    val priority: String = "Low",
    val status: String = "ACTIVE",
    val createdAt: Long = System.currentTimeMillis()
)
