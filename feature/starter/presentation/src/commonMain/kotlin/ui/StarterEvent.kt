package ui

import com.slack.circuit.runtime.CircuitUiEvent

sealed class StarterEvent : CircuitUiEvent {
    data object LoadData : StarterEvent()
    data object NavigateToMainScreen : StarterEvent()
}