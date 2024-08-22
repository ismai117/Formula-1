package drivers

import kotlinx.coroutines.flow.Flow
import local.DriversLocalService
import remote.DriversRemoteService
import utils.Resource
import utils.networkBoundResource

class DriversRepositoryImpl(
    private val remote: DriversRemoteService,
    private val local: DriversLocalService
) : DriversRepository {

    override fun getDrivers(): Flow<Resource<List<Driver>>> {
        return networkBoundResource(
            query = { local.selectDrivers() },
            fetch = { remote.getDrivers() },
            saveFetchResult = { local.insertDriver(it) },
            shouldFetch = { it.isEmpty() }
        )
    }

    override suspend fun getDriverByDriverNumber(driverNumber: Int): Driver? {
        return local.selectDriverByDriverNumber(driverNumber)
    }

    override suspend fun clear() {
        local.clear()
    }

}

