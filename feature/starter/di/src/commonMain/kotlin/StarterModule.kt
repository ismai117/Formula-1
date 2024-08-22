package commonMain

import local.StarterLocalService
import org.koin.dsl.module
import starter.StarterRepository
import starter.StarterRepositoryImpl
import ui.StarterViewModel

val starterModule = module {
    single { StarterLocalService() }
    single<StarterRepository> { StarterRepositoryImpl(get()) }
    factory { StarterViewModel(get(), get(), get()) }
}