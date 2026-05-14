package com.dwiaziprasetya.api_auth.di

import com.dwiaziprasetya.api_auth.data.repository.AuthRepositoryImpl
import com.dwiaziprasetya.api_auth.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository
}