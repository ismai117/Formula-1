package platform

import android.content.Context
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import local.TeamEntity
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual fun teamsPlatformModule(): Module = module {
    single<KStore<List<TeamEntity>>>(named("teams_kstore")) {
        teams(get())
    }
}

private fun teams(ctx: Context): KStore<List<TeamEntity>> {
    val filesDir: String = ctx.filesDir.path
    return storeOf(file = "$filesDir/teams.json".toPath(), default = emptyList())
}