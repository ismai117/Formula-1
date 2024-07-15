package drivers


import drivers.DriverEntity.Companion.fromDriver
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.extensions.getOrEmpty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DriversLocalService(
    private val kStore: KStore<List<DriverEntity>>
) {
    fun selectDrivers(): Flow<List<Driver>> {
        return kStore.updates
            .map { entries ->
                entries.orEmpty().map { it.toDriver() }
            }
    }

    suspend fun selectDriverByDriverNumber(driverNumber: Int): Driver? {
        val driverEntity = kStore.getOrEmpty().map { it.toDriver() }
        return driverEntity.find { it.driverNumber == driverNumber }
    }

    suspend fun insertDriver(drivers: List<Driver>) {
        kStore.set(drivers.map { it.fromDriver() })
    }

}