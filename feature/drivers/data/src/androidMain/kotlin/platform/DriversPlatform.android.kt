package platform

import android.content.Context
import applicationContext
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import local.DriverEntity
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual fun driversPlatformModule(): Module = module {
    single<KStore<List<DriverEntity>>>(named("drivers_kstore")) {
        drivers(get())
    }
}

private fun drivers(ctx: Context): KStore<List<DriverEntity>> {
    val filesDir: String = ctx.filesDir.path
    return storeOf(file = "$filesDir/drivers.json".toPath(), default = emptyList())
}