package com.dwiaziprasetya.api_auth.domain.model

data class User(
    val id: String,
    val email: String?,
    val name: String?,
    val photoUrl: String?
)