package com.dwiaziprasetya.feature_home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwiaziprasetya.api_auth.domain.usecase.GetCurrentUser
import com.dwiaziprasetya.api_auth.domain.usecase.Logout
import com.dwiaziprasetya.api_task.domain.usecase.DeleteTask
import com.dwiaziprasetya.api_task.domain.usecase.GetTasks
import com.dwiaziprasetya.api_task.domain.usecase.UpdateTaskStatus
import com.dwiaziprasetya.core_common.util.AppResult
import com.dwiaziprasetya.core_ui.util.SnackbarController
import com.dwiaziprasetya.core_ui.util.SnackbarEvent
import com.dwiaziprasetya.core_ui.util.SnackbarType
import com.dwiaziprasetya.feature_home.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTasks: GetTasks,
    private val updateTaskStatus: UpdateTaskStatus,
    private val deleteTask: DeleteTask,
    private val getCurrentUser: GetCurrentUser,
    private val logout: Logout
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state

    init {
        observeTasks()
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {

            delay(1000)

            _state.update { it.copy(isUserLoading = true) }

            when (val result = getCurrentUser()) {

                is AppResult.Success -> {
                    val user = result.data
                    _state.update {
                        it.copy(
                            userName = user.name,
                            userPhotoUrl = user.photoUrl,
                            email = user.email ?: "",
                            isUserLoading = false
                        )
                    }
                }

                is AppResult.Error -> {
                    _state.update {
                        it.copy(
                            isUserLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
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

    fun logoutUser() {
        viewModelScope.launch {

            _state.update { it.copy(isLogoutLoading = true) }

            delay(1000)

            val result = logout()

            when(result){

                is AppResult.Success -> {
                    _state.update {
                        it.copy(
                            isLogoutLoading = false,
                            isLogoutSuccess = true
                        )
                    }

                    SnackbarController.sendEvent(
                        SnackbarEvent("Logout success", SnackbarType.SUCCESS)
                    )
                }

                is AppResult.Error -> {
                    _state.update {
                        it.copy(isLogoutLoading = false)
                    }
                }
            }
        }
    }

    fun resetSuccess() {
        _state.update { it.copy(isLogoutSuccess = false) }
    }
}