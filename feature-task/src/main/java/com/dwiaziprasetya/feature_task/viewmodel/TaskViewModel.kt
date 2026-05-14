package com.dwiaziprasetya.feature_task.viewmodel

import androidx.lifecycle.ViewModel
import com.dwiaziprasetya.feature_task.state.TaskState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

            delay(1000)

            getTasks().collect { result ->
                when (result) {
                    is AppResult.Success -> {
                        _state.update {
                            it.copy(
                                tasks = result.data ,
                                isLoading = false ,
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

    fun removeTask(
        taskId: String
    ) {
        viewModelScope.launch {
            deleteTask(taskId)
        }
    }
}