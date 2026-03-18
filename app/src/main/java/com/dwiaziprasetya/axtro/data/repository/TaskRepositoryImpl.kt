package com.dwiaziprasetya.axtro.data.repository

import android.util.Log
import com.dwiaziprasetya.axtro.core.util.AppResult
import com.dwiaziprasetya.axtro.domain.model.Task
import com.dwiaziprasetya.axtro.domain.repository.TaskRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : TaskRepository {

    override suspend fun addTask(
        title: String,
        date: Long,
        priority: String
    ): AppResult<Unit> {

        return try {
            val uid = firebaseAuth.currentUser?.uid
                ?: return AppResult.Error("User not logged in")

            val taskId = firestore.collection("tasks").document().id

            val task = Task(
                id = taskId,
                title = title,
                date = date,
                priority = priority,
                status = "ACTIVE",
                userId = uid
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

    override fun getTasks(): Flow<AppResult<List<Task>>> = callbackFlow {

        val uid = firebaseAuth.currentUser?.uid

        val listener = firestore
            .collection("tasks")
            .whereEqualTo("userId", uid)
            .orderBy("date")
            .addSnapshotListener { snapshot, error ->

                Log.d("TASK_DEBUG", "Snapshot triggered")

                if (error != null) {
                    trySend(AppResult.Error(error.message ?: "Failed to fetch tasks"))
                    return@addSnapshotListener
                }

                val tasks = snapshot?.documents?.mapNotNull {
                    it.toObject(Task::class.java)
                } ?: emptyList()

                Log.d("TASK_DEBUG", tasks.toString())

                trySend(AppResult.Success(tasks))
            }

        awaitClose { listener.remove() }
    }

    override suspend fun updateTaskStatus(
        taskId: String,
        status: String
    ) {
        firestore
            .collection("tasks")
            .document(taskId)
            .update("status", status)
            .await()
    }
    override suspend fun deleteTask(taskId: String) {
        firestore
            .collection("tasks")
            .document(taskId)
            .delete()
            .await()
    }
}