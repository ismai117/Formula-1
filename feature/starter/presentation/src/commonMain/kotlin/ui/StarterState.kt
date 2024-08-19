package ui

import com.slack.circuit.runtime.CircuitUiState

data class StarterState(
    val isLoading: Boolean = false,
    val status: Boolean = false,
    val message: String = "",
    val eventSink: (StarterEvent) -> Unit
) : CircuitUiState
