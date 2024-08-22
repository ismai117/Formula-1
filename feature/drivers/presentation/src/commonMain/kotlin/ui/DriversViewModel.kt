package ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import drivers.Driver
import kotlinx.coroutines.launch
import drivers.DriversRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class DriversState(
    val drivers: List<Driver> = emptyList()
)

class DriversViewModel(
    private val driversRepository: DriversRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DriversState())
    val state = _state.asStateFlow()

    private val _driver = MutableStateFlow<Driver?>(null)
    val driver = _driver.asStateFlow()

    fun getDrivers() {
        viewModelScope.launch{
            driversRepository.getDrivers()
                .collect { result ->
                    _state.update { it.copy(drivers = result.data.orEmpty()) }
                }
        }
    }

    fun getDriverByDriverNumber(driverNumber: Int) {
        viewModelScope.launch {
            _driver.update { driversRepository.getDriverByDriverNumber(driverNumber) }
        }
    }

    fun clear(){
        viewModelScope.launch {
            driversRepository.clear()
        }
    }

}