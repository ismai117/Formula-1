package ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import commonMain.DriversModule
import drivers.Driver
import kotlinx.coroutines.launch
import drivers.DriversRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.reflect.KClass

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

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                return DriversViewModel(
                    driversRepository = DriversModule.driversRepository
                ) as T
            }
        }
    }

}