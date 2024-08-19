package ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import commonMain.DriversModule
import commonMain.StarterModule
import commonMain.TeamsModule
import drivers.DriversRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import starter.StarterRepository
import teams.TeamsRepository
import utils.Resource
import kotlin.reflect.KClass

data class StarterState(
    val isLoading: Boolean = false,
    val status: Boolean = false,
    val message: String = "",
    val isStarted: Boolean = false
)

class StarterViewModel(
    private val driversRepository: DriversRepository,
    private val teamsRepository: TeamsRepository,
    private val starterRepository: StarterRepository
) : ViewModel() {

    private val _state = MutableStateFlow(StarterState())
    val state = _state.asStateFlow()

    init {
        getStartedState()
    }

    fun onEvent(event: StarterOnEvent){
        when(event){
            StarterOnEvent.STARTED -> {
                setStartedState()
            }
        }
    }

    private fun getStartedState(){
        viewModelScope.launch {
            starterRepository.getStartedState()
                .collect { result ->
                    _state.update { it.copy(isStarted = result ?: false) }
                }
        }
    }

    fun getData(){
        viewModelScope.launch {
            combine(driversRepository.getDrivers(), teamsRepository.getTeams()){ drivers, teams ->
                when {
                    drivers is Resource.Loading || teams is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                    drivers is Resource.Success && teams is Resource.Success -> {
                        _state.update { it.copy(isLoading = false, status = true) }
                    }
                    drivers is Resource.Error -> {
                        _state.update { it.copy(isLoading = false, message = drivers.message) }
                    }
                    teams is Resource.Error -> {
                        _state.update { it.copy(isLoading = false, message = teams.message) }
                    }
                }
            }
        }
    }

    private fun setStartedState(){
        viewModelScope.launch {
            starterRepository.setStartedState()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                return StarterViewModel(
                    starterRepository = StarterModule.starterRepository,
                    driversRepository = DriversModule.driversRepository,
                    teamsRepository = TeamsModule.teamsRepository
                ) as T
            }
        }
    }

}

sealed interface StarterOnEvent {
    data object STARTED : StarterOnEvent
}