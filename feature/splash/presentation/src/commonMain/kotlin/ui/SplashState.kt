
package ui

import com.slack.circuit.runtime.CircuitUiState

data class SplashState (
    val isStarted: Boolean = false,
    val eventSink: (SplashEvent) -> Unit
) : CircuitUiState