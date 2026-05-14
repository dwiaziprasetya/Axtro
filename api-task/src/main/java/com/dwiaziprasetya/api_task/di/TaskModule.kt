package com.dwiaziprasetya.api_task.di

import com.dwiaziprasetya.api_task.data.repository.TaskRepositoryImpl
import com.dwiaziprasetya.api_task.domain.repository.TaskRepository
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