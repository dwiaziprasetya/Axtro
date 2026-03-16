package com.example.axtro.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.axtro.core.util.AppResult
import com.example.axtro.domain.usecase.DeleteTask
import com.example.axtro.domain.usecase.GetTasks
import com.example.axtro.domain.usecase.UpdateTaskStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTasks: GetTasks,
    private val updateTaskStatus: UpdateTaskStatus,
    private val deleteTask: DeleteTask
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state

    init {
        observeTasks()
    }

    private fun observeTasks() {

        viewModelScope.launch {

            _state.update { it.copy(isLoading = true) }

            getTasks().collect { result ->

                when (result) {

                    is AppResult.Success -> {

                        _state.update {
                            it.copy(
                                tasks = result.data,
                                isLoading = false,
                                error = null
                            )
                        }

                    }

                    is AppResult.Error -> {

                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }

                    }

                }

            }

        }
    }

    fun updateTaskStatus(
        taskId: String,
        isCompleted: Boolean
    ) {
        viewModelScope.launch {
            val status = if (isCompleted) {
                "COMPLETED"
            } else {
                "ACTIVE"
            }

            updateTaskStatus(taskId, status)
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