package com.diegomendes.controller

import io.javalin.Javalin

object Server {
    fun start() {
        val app = Javalin.create().apply {
            exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
            error(404) { ctx -> ctx.json("not found") }
        }.start(getHerokuAssignedPort())

        createRoutes(app)
        createRoutesErrors(app)
    }

    private fun createRoutes(app: Javalin){
        CurrencyConverterController.createRoute(app);
    }

    private fun createRoutesErrors(app: Javalin){
        app.exception(Exception::class.java) { e, ctx ->
            ctx.status(400)
        }
    }

    private fun getHerokuAssignedPort(): Int {
        val herokuPort = System.getenv("PORT")
        return herokuPort?.toInt() ?: 7000
    }
}