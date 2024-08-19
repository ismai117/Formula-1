package ui

import com.slack.circuit.runtime.CircuitUiState
import teams.Team

data class TeamsState(

    val isLoading: Boolean = false,
    val status: Boolean = false,
    val message: String = "",

    val teams: List<Team> = emptyList(),
    val team: Team? = null,

    val eventSink: (TeamEvent) -> Unit

) : CircuitUiState