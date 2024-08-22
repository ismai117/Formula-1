package di

import commonMain.driversModule
import commonMain.starterModule
import commonMain.teamsModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        modules(
            starterModule,
            driversModule,
            teamsModule
        )
        appDeclaration()
    }

fun initKoin() = initKoin(enableNetworkLogs = false) {}