package com.example.axtro.data.repository

import com.example.axtro.core.util.AppResult
import com.example.axtro.domain.model.User
import com.example.axtro.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override suspend fun loginWithEmail(
        email: String,
        password: String
    ): AppResult<User> {
        return try {
            val result = firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .await()

            val firebaseUser = result.user

            if (firebaseUser != null) {
                AppResult.Success(
                    User(
                        id = firebaseUser.uid,
                        email = firebaseUser.email
                    )
                )
            } else {
                AppResult.Error("User tidak ditemukan")
            }
        } catch (e: Exception) {
            AppResult.Error(
                message = e.message ?: "Login gagal",
                throwable = e
            )
        }
    }
}