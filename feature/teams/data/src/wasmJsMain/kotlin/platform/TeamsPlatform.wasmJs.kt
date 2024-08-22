package platform

import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.storage.storeOf
import local.TeamEntity
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual fun teamsPlatformModule(): Module = module {
    single<KStore<List<TeamEntity>>>(named("teams_kstore")) {
        teams()
    }
}

private fun teams(): KStore<List<TeamEntity>> {
    return storeOf(key = "teams", default = emptyList())
}