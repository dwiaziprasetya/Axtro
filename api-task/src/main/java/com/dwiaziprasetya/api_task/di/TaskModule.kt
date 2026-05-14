package com.dwiaziprasetya.api_task.di

import com.dwiaziprasetya.api_task.data.repository.TaskRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class TaskModule {

    @Binds
    abstract fun bindTaskRepository(
        impl: TaskRepositoryImpl
    ): TaskRepository
}