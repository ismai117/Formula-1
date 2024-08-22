package ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import teams.Team
import teams.TeamsRepository
import kotlin.reflect.KClass

data class TeamsState(
    val teams: List<Team> = emptyList()
)

class TeamsViewModel(
    private val teamsRepository: TeamsRepository
) : ViewModel(){

    private val _state = MutableStateFlow(TeamsState())
    val state = _state.asStateFlow()

    private val _team = MutableStateFlow<Team?>(null)
    val team = _team.asStateFlow()

    fun getTeams() {
        viewModelScope.launch {
            teamsRepository.getTeams()
                .collect { result ->
                    _state.update { it.copy(teams = result.data.orEmpty()) }
                }
        }
    }

    fun getTeamByTeamName(name: String) {
        viewModelScope.launch {
            _team.update { teamsRepository.getTeamByTeamName(name) }
        }
    }

}