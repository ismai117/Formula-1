package ui

import com.slack.circuit.runtime.CircuitUiEvent

sealed interface SplashEvent : CircuitUiEvent {
    data object NavigateToMainScreen : SplashEvent
    data object NavigateToStarterScreen : SplashEvent
}