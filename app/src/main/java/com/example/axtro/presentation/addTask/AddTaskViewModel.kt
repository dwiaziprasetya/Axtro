package com.example.axtro.presentation.addTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.axtro.core.util.AppResult
import com.example.axtro.core.util.SnackbarController
import com.example.axtro.core.util.SnackbarEvent
import com.example.axtro.core.util.SnackbarType
import com.example.axtro.domain.usecase.AddTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val addTask: AddTask
) : ViewModel() {

    private val _state = MutableStateFlow(AddTaskUiState())
    val state: StateFlow<AddTaskUiState> = _state

    fun onTitleChange(value: String) {
        _state.update { it.copy(title = value) }
    }

    fun onDateChange(value: Long) {
        _state.update { it.copy(date = value) }
    }

    fun onPriorityChange(value: String) {
        _state.update { it.copy(priority = value) }
    }

    fun createTask() {
        viewModelScope.launch {

            _state.update { it.copy(isLoading = true) }

            val result = addTask(
                _state.value.title,
                _state.value.date,
                _state.value.priority
            )

            when (result) {

                is AppResult.Success -> {

                    _state.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = true
                        )
                    }

                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = "Task created",
                            type = SnackbarType.SUCCESS
                        )
                    )
                }

                is AppResult.Error -> {

                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }

                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = result.message,
                            type = SnackbarType.ERROR
                        )
                    )
                }
            }
        }
    }
}