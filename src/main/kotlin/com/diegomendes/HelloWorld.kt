package com.diegomendes

import com.diegomendes.infra.Database
import com.diegomendes.infra.Server

fun main(args: Array<String>) {
    Database.start()
    Server.start()
}


