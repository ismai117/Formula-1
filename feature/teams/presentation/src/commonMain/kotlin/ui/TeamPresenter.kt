package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import kotlinx.coroutines.launch
import teams.TeamsRepository
import utils.Resource

class TeamPresenter(
    private val navigator: Navigator,
    private val teamsRepository: TeamsRepository,
) : Presenter<TeamsState> {

    @Composable
    override fun present(): TeamsState {

        val scope = rememberCoroutineScope()

        var state by rememberSaveable { mutableStateOf(TeamsState(eventSink = {})) }

        fun getTeams() {
            scope.launch {
                teamsRepository.getTeams()
                    .collect { result ->
                        state = when (result) {
                            is Resource.Loading -> {
                                state.copy(isLoading = true)
                            }

                            is Resource.Success -> {
                                state.copy(
                                    isLoading = false,
                                    status = true,
                                    teams = result.data.orEmpty()
                                )
                            }

                            is Resource.Error -> {
                                state.copy(isLoading = false, message = result.message)
                            }
                        }
                    }
            }
        }

        fun getTeamByTeamName(name: String) {
            scope.launch {
                state = state.copy(team = teamsRepository.getTeamByTeamName(name))
            }
        }

        return TeamsState(
            isLoading = state.isLoading,
            status = state.status,
            message = state.message,
            teams = state.teams,
            team = state.team
        ) { event ->
            when (event) {
                is TeamEvent.NavigateToTeamDetail -> {
                    getTeamByTeamName(name = event.teamName)
                }

                TeamEvent.Pop -> {
                    navigator.pop()
                }
            }
        }

    }

}
