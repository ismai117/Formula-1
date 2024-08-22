package drivers

import kotlinx.coroutines.flow.Flow
import utils.Resource

interface DriversRepository {
    fun getDrivers(): Flow<Resource<List<Driver>>>
    suspend fun getDriverByDriverNumber(driverNumber: Int): Driver?
    suspend fun clear()
}