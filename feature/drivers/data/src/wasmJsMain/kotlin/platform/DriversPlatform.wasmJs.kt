package platform

import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.storage.storeOf
import local.DriverEntity
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual fun driversPlatformModule(): Module = module {
    single<KStore<List<DriverEntity>>>(named("drivers_kstore")) {
        drivers()
    }
}

private fun drivers(): KStore<List<DriverEntity>> {
    return storeOf(key = "drivers", default = emptyList())
}