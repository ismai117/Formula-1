package ui

import com.slack.circuit.runtime.CircuitUiEvent
import teams.Team

sealed class TeamEvent : CircuitUiEvent {
    class NavigateToTeamDetail(val teamName: String) : TeamEvent()
    data object Pop : TeamEvent()
}