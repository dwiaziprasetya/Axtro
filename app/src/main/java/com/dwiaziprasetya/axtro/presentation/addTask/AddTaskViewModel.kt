package com.dwiaziprasetya.axtro.presentation.addTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwiaziprasetya.axtro.core.util.AppResult
import com.dwiaziprasetya.axtro.core.util.SnackbarController
import com.dwiaziprasetya.axtro.core.util.SnackbarEvent
import com.dwiaziprasetya.axtro.core.util.SnackbarType
import com.dwiaziprasetya.axtro.domain.usecase.AddTask
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

            val current = _state.value

            if (current.day == null || current.month == null || current.year == null) {
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = "Date is incomplete",
                        type = SnackbarType.ERROR
                    )
                )
                return@launch
            }

            val calendar = Calendar.getInstance().apply {
                isLenient = false
                set(current.year, current.month - 1, current.day)
            }

            val timestamp = try {
                calendar.timeInMillis
            } catch (e: Exception) {
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = "Invalid date",
                        type = SnackbarType.ERROR
                    )
                )
                return@launch
            }

            _state.update { it.copy(isLoading = true) }

            val result = addTask(
                title = current.title,
                date = timestamp,
                priority = current.priority
            )

            when (result) {
                is AppResult.Success -> {
                    _state.update {
                        it.copy(isLoading = false, isSuccess = true)
                    }
                    SnackbarController.sendEvent(
                        SnackbarEvent("Task created", SnackbarType.SUCCESS)
                    )
                }

                is AppResult.Error -> {
                    _state.update {
                        it.copy(isLoading = false, error = result.message)
                    }
                    SnackbarController.sendEvent(
                        SnackbarEvent(result.message, SnackbarType.ERROR)
                    )
                }
            }
        }
    }

    fun resetSuccess() {
        _state.update { it.copy(isSuccess = false) }
    }
}