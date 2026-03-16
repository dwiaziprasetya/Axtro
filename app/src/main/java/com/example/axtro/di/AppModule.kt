package com.example.axtro.di

import com.example.axtro.domain.repository.AuthRepository
import com.example.axtro.domain.repository.TaskRepository
import com.example.axtro.domain.usecase.AddTask
import com.example.axtro.domain.usecase.DeleteTask
import com.example.axtro.domain.usecase.GetTasks
import com.example.axtro.domain.usecase.LoginWithEmail
import com.example.axtro.domain.usecase.LoginWithGoogle
import com.example.axtro.domain.usecase.Logout
import com.example.axtro.domain.usecase.RegisterWithEmail
import com.example.axtro.domain.usecase.UpdateTaskStatus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideRegisterWithEmail(
        repository: AuthRepository
    ): RegisterWithEmail {
        return RegisterWithEmail(repository)
    }

    @Provides
    @Singleton
    fun provideLoginWithEmail(
        repository: AuthRepository
    ): LoginWithEmail {
        return LoginWithEmail(repository)
    }

    @Provides
    @Singleton
    fun provideLoginWithGoogle(
        repository: AuthRepository
    ) : LoginWithGoogle {
        return LoginWithGoogle(repository)
    }

    @Provides
    @Singleton
    fun provideAddTask(
        repository: TaskRepository
    ) : AddTask {
        return AddTask(repository)
    }

    @Provides
    @Singleton
    fun provideGetTask(
        repository: TaskRepository
    ) : GetTasks {
        return GetTasks(repository)
    }

    @Provides
    @Singleton
    fun updateTaskStatus(
        repository: TaskRepository
    ) : UpdateTaskStatus {
        return UpdateTaskStatus(repository)
    }

    @Provides
    @Singleton
    fun deleteTask(
        repository: TaskRepository
    ) : DeleteTask {
        return DeleteTask(repository)
    }

    @Provides
    @Singleton
    fun logout(
        repository: AuthRepository
    ) : Logout {
        return Logout(repository)
    }
}