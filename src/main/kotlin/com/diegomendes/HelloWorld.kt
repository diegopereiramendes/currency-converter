package com.diegomendes

import com.diegomendes.infra.Database
import com.diegomendes.infra.Server
import com.diegomendes.utils.KoinConfig.allModules
import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    startKoin {
        modules(allModules)
    }
    Database.start()
    Server.start()
}


