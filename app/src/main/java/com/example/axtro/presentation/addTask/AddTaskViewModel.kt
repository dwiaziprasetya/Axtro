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
import java.util.Calendar
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

    fun onDayChange(value: Int) {
        _state.update { it.copy(day = value) }
    }

    fun onMonthChange(value: Int) {
        _state.update { it.copy(month = value) }
    }

    fun onYearChange(value: Int) {
        _state.update { it.copy(year = value) }
    }

    fun onPriorityChange(value: String) {
        _state.update { it.copy(priority = value) }
    }

    fun createTask() {
        viewModelScope.launch {

            _state.update { it.copy(isLoading = true) }

            val calendar = Calendar.getInstance().apply {
                set(
                    _state.value.year,
                    _state.value.month - 1,
                    _state.value.day
                )
            }

            val timestamp = calendar.timeInMillis

            val result = addTask(
                title = _state.value.title,
                date = timestamp,
                priority = _state.value.priority
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

    fun resetSuccess() {
        _state.update { it.copy(isSuccess = false) }
    }
}