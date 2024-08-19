package ui

import com.slack.circuit.runtime.CircuitUiState
import drivers.Driver

data class DriverState(
    val drivers: List<Driver> = emptyList(),
    val driver: Driver? = null,
    val eventSink: (DriverEvent) -> Unit
) : CircuitUiState