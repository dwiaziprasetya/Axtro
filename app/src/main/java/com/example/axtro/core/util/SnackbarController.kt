package com.example.axtro.core.util

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

enum class SnackbarType {
    SUCCESS, ERROR
}

data class SnackbarEvent(
    val message: String,
    val type: SnackbarType = SnackbarType.SUCCESS,
    val action: SnackbarAction? = null
)

data class SnackbarAction(
    val name: String,
    val action: () -> Unit
)

object SnackbarController {

    private val _events = Channel<SnackbarEvent>()
    val events = _events.receiveAsFlow()

    suspend fun sendEvent(event: SnackbarEvent) {
        _events.send(event)
    }
}