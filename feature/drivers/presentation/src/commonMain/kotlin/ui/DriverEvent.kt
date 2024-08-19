package ui

import com.slack.circuit.runtime.CircuitUiEvent

sealed class DriverEvent : CircuitUiEvent {
    class NavigateToDriverDetail(val driverNumber: Int) : DriverEvent()
    data object Pop : DriverEvent()
}