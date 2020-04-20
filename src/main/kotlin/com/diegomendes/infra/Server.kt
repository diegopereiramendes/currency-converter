package com.diegomendes.infra

import com.diegomendes.controller.CurrencyConverterController
import com.diegomendes.exceptions.RecordsNotFound
import io.javalin.Javalin
import org.apache.http.HttpStatus

object Server {
    fun start() {
        val app = Javalin.create().start(getHerokuAssignedPort())
        createRoutes(app)
        createRoutesErrors(app)
    }

    private fun createRoutes(app: Javalin){
        CurrencyConverterController.createRoute(app);
    }

    private fun createRoutesErrors(app: Javalin){
        app.exception(Exception::class.java) { e, ctx ->
            ctx.json(e.message!!)
            ctx.status(HttpStatus.SC_BAD_REQUEST)
        }
        app.exception(RecordsNotFound::class.java) { e, ctx ->
            ctx.json(e.message!!)
            ctx.status(HttpStatus.SC_NOT_FOUND)
        }
    }

    private fun getHerokuAssignedPort(): Int {
        val herokuPort = System.getenv("PORT")
        return herokuPort?.toInt() ?: 7000
    }
}