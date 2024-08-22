package platform

import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import io.github.xxfast.kstore.file.utils.DocumentDirectory
import io.github.xxfast.kstore.utils.ExperimentalKStoreApi
import local.DriverEntity
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import platform.Foundation.NSFileManager

actual fun driversPlatformModule(): Module = module {
    single<KStore<List<DriverEntity>>>(named("drivers_kstore")) {
        drivers()
    }
}

@OptIn(ExperimentalKStoreApi::class)
private fun drivers(): KStore<List<DriverEntity>> {
    val filesDir: String? = NSFileManager.defaultManager.DocumentDirectory?.relativePath
    requireNotNull(filesDir) { "Document directory not found" }
    return storeOf(file = "$filesDir/drivers.json".toPath(), default = emptyList())
}