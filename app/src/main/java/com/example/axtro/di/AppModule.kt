package com.example.axtro.di

import com.example.axtro.domain.repository.AuthRepository
import com.example.axtro.domain.usecase.LoginWithEmail
import com.example.axtro.domain.usecase.RegisterWithEmail
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideRegisterWithEmail(
        repository: AuthRepository
    ): RegisterWithEmail {
        return RegisterWithEmail(repository)
    }
}