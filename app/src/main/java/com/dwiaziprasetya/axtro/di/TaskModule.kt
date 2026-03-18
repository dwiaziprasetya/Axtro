package com.dwiaziprasetya.axtro.di

import com.dwiaziprasetya.axtro.data.repository.TaskRepositoryImpl
import com.dwiaziprasetya.axtro.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TaskModule {

    @Binds
    abstract fun bindTaskRepository(
        impl: TaskRepositoryImpl
    ): TaskRepository
}