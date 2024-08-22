package commonMain

import drivers.DriversRepository
import drivers.DriversRepositoryImpl
import local.DriversLocalService
import org.koin.core.qualifier.named
import org.koin.dsl.module
import platform.driversPlatformModule
import remote.DriversRemoteService
import ui.DriversViewModel

val driversModule = module {
    includes(driversPlatformModule())
    single { DriversRemoteService() }
    single { DriversLocalService(get(named("drivers_kstore"))) }
    single<DriversRepository> { DriversRepositoryImpl(get(), get()) }
    factory { DriversViewModel(get()) }
}