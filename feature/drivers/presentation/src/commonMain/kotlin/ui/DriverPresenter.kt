package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import drivers.DriversRepository
import kotlinx.coroutines.launch
import utils.Resource

class DriverPresenter(
    private val navigator: Navigator,
    private val driversRepository: DriversRepository
) : Presenter<DriverState> {

    @Composable
    override fun present(): DriverState {

        val scope = rememberCoroutineScope()

        var state by rememberSaveable { mutableStateOf(DriverState(eventSink = {})) }

        LaunchedEffect(Unit){
            scope.launch{
                driversRepository.getDrivers()
                    .collect { result ->
                        state = when (result) {
                            is Resource.Success -> {
                                state.copy(
                                    drivers = result.data.orEmpty()
                                )
                            }
                            else -> { DriverState(eventSink = {}) }
                        }
                    }
            }
        }

        fun getDriverByDriverNumber(driverNumber: Int) {
            scope.launch {
                state = state.copy(driver = driversRepository.getDriverByDriverNumber(driverNumber))
            }
        }

        return DriverState(
            drivers = state.drivers,
            driver = state.driver
        ) { event ->
            when (event) {
                is DriverEvent.NavigateToDriverDetail -> {
                    getDriverByDriverNumber(event.driverNumber)
                }

                DriverEvent.Pop -> { navigator.pop() }
            }
        }

    }

}