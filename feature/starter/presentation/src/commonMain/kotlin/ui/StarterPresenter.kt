package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import drivers.DriversRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import starter.StarterRepository
import teams.TeamsRepository
import utils.Resource

class StarterPresenter(
    private val navigator: Navigator,
    private val starterRepository: StarterRepository,
    private val driversRepository: DriversRepository,
    private val teamsRepository: TeamsRepository,
) : Presenter<StarterState> {

    @Composable
    override fun present(): StarterState {

        val scope = rememberCoroutineScope()

        var isLoading by rememberSaveable { mutableStateOf(false) }
        var status by rememberSaveable { mutableStateOf(false) }
        var message by rememberSaveable { mutableStateOf("") }

        fun getData() {
            scope.launch {
                combine(
                    driversRepository.getDrivers(),
                    teamsRepository.getTeams()
                ) { drivers, teams ->
                    when {
                        drivers is Resource.Loading || teams is Resource.Loading -> {
                            isLoading = true
                        }

                        drivers is Resource.Success && teams is Resource.Success -> {
                            isLoading = false
                            status = true
                        }

                        drivers is Resource.Error -> {
                            isLoading = false
                            message = teams.message
                        }

                        teams is Resource.Error -> {
                            isLoading = false
                            message = teams.message
                        }
                    }
                }
            }
        }

        fun setStartedState(){
            scope.launch {
                starterRepository.setStartedState()
            }
        }

        return StarterState(
            isLoading = isLoading,
            status = status,
            message = message
        ) { event ->
            when (event) {
                StarterEvent.LoadData -> {
                    getData()
                }

                StarterEvent.NavigateToMainScreen -> {
                    setStartedState()
                }
            }
        }

    }

}