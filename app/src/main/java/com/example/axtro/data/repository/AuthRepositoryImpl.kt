package com.example.axtro.data.repository

import com.example.axtro.core.util.AppResult
import com.example.axtro.domain.model.User
import com.example.axtro.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override suspend fun registerWithEmail(
        email: String,
        password: String
    ): AppResult<User> {

        return try {

            val result = firebaseAuth
                .createUserWithEmailAndPassword(email, password)
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
                AppResult.Error("Failed to create User")
            }

        } catch (e: Exception) {
            AppResult.Error(
                message = e.message ?: "Failed to register",
                throwable = e
            )
        }
    }

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
                AppResult.Error("User not found")
            }
        } catch (e: Exception) {
            AppResult.Error(
                message = e.message ?: "Failed to login",
                throwable = e
            )
        }
    }

    override suspend fun loginWithGoogle(idToken: String): AppResult<Unit> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            firebaseAuth.signInWithCredential(credential).await()
            AppResult.Success(Unit)
        } catch (e: Exception) {
            AppResult.Error(
                message = e.message ?: "Failed to login with Google",
                throwable = e
            )
        }
    }
}