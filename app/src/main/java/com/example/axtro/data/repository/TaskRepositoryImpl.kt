package com.example.axtro.data.repository

import com.example.axtro.core.util.AppResult
import com.example.axtro.domain.model.Task
import com.example.axtro.domain.repository.TaskRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : TaskRepository {

    override suspend fun addTask(
        title: String,
        date: Long,
        priority: String
    ): AppResult<Unit> {

        return try {

            val taskId = firestore.collection("tasks").document().id

            val task = Task(
                id = taskId,
                title = title,
                date = date,
                priority = priority,
                status = "ACTIVE"
            )

            firestore
                .collection("tasks")
                .document(taskId)
                .set(task)
                .await()

            AppResult.Success(Unit)

        } catch (e: Exception) {

            AppResult.Error(
                message = e.message ?: "Failed to add task",
                throwable = e
            )
        }
    }

    override fun getTasks(): Flow<List<Task>> {
        TODO("Not yet implemented")
    }
}