package commonMain

import local.TeamsLocalService
import org.koin.core.qualifier.named
import org.koin.dsl.module
import platform.teamsPlatformModule
import remote.TeamsRemoteService
import teams.TeamsRepository
import repository.TeamsRepositoryImpl
import ui.TeamsViewModel

val teamsModule = module {
    includes(teamsPlatformModule())
    single { TeamsRemoteService() }
    single { TeamsLocalService(get(named("teams_kstore"))) }
    single<TeamsRepository> { TeamsRepositoryImpl(get(), get()) }
    factory { TeamsViewModel(get()) }
}