package com.dwiaziprasetya.axtro.di

import com.dwiaziprasetya.axtro.data.repository.AuthRepositoryImpl
import com.dwiaziprasetya.axtro.domain.repository.AuthRepository
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