package com.dwiaziprasetya.api_task.data.repository

import android.util.Log
import kotlinx.coroutines.flow.callbackFlow
import kotlin.jvm.java

class TaskRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : TaskRepository {

    override suspend fun addTask(
        title: String,
        description: String,
        date: Long,
        startTime: Long,
        endTime: Long?,
        priority: String
    ): AppResult<Unit> {

        return try {
            val uid = firebaseAuth.currentUser?.uid
                ?: return AppResult.Error("User not logged in")

            val taskId = firestore.collection("tasks").document().id

            val task = Task(
                id = taskId,
                title = title,
                description = description,
                date = date,
                startTime = startTime,
                endTime = endTime,
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