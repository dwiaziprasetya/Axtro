package com.dwiaziprasetya.feature_task.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwiaziprasetya.api_task.domain.model.Task
import com.dwiaziprasetya.api_task.domain.usecase.DeleteTask
import com.dwiaziprasetya.api_task.domain.usecase.GetTasks
import com.dwiaziprasetya.api_task.domain.usecase.UpdateTaskStatus
import com.dwiaziprasetya.core_common.util.AppResult
import com.dwiaziprasetya.feature_task.model.SortType
import com.dwiaziprasetya.feature_task.state.TaskState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.sortedBy
import kotlin.collections.sortedByDescending

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTasks: GetTasks ,
    private val deleteTask: DeleteTask ,
    private val updateTaskStatus: UpdateTaskStatus ,
) : ViewModel() {

    private val _state = MutableStateFlow(TaskState())
    val state: StateFlow<TaskState> = _state

    init {
        observeTasks()
    }

    private fun observeTasks() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getTasks().collect { result ->
                when (result) {
                    is AppResult.Success -> {
                        val sortedTasks = applySorting(
                            tasks = result.data,
                            sortType = _state.value.selectedSort
                        )

                        _state.update {
                            it.copy(
                                originalTasks = result.data,
                                tasks = sortedTasks,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                    is AppResult.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false ,
                                error = result.message
                            )
                        }
                    }

                }
            }
        }
    }

    fun updateTaskStatus(
        taskId: String ,
        isCompleted: Boolean
    ) {
        viewModelScope.launch {
            val status = if (isCompleted) {
                "COMPLETED"
            } else {
                "ACTIVE"
            }

            updateTaskStatus(taskId , status)
        }
    }

    private fun applySorting(
        tasks: List<Task>,
        sortType: SortType
    ): List<Task> {

        return when (sortType) {

            SortType.A_TO_Z -> {
                tasks.sortedBy { it.title.lowercase() }
            }

            SortType.Z_TO_A -> {
                tasks.sortedByDescending { it.title.lowercase() }
            }

            SortType.PRIORITY_LOW_TO_HIGH -> {
                tasks.sortedBy { priorityOrder(it.priority) }
            }

            SortType.PRIORITY_HIGH_TO_LOW -> {
                tasks.sortedByDescending { priorityOrder(it.priority) }
            }

            SortType.DATE_ASCENDING -> {
                tasks.sortedBy { it.date }
            }

            SortType.DATE_DESCENDING -> {
                tasks.sortedByDescending { it.date }
            }
        }
    }

    fun removeTask(
        taskId: String
    ) {
        viewModelScope.launch {
            deleteTask(taskId)
        }
    }

    fun updateSort(sortType: SortType) {

        val sortedTasks = applySorting(
            tasks = _state.value.originalTasks,
            sortType = sortType
        )

        _state.update {
            it.copy(
                selectedSort = sortType,
                tasks = sortedTasks
            )
        }
    }
}

private fun priorityOrder(priority: String): Int {
    return when (priority) {
        "Low" -> 0
        "Medium" -> 1
        "High" -> 2
        else -> 3
    }
}