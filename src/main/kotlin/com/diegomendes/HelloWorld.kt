package com.diego

import io.javalin.Javalin

fun main(args: Array<String>) {
    val app = Javalin.create().start(getHerokuAssignedPort())
    app.get("/") { ctx -> ctx.result("Hello World") }
}

fun getHerokuAssignedPort(): Int {
    val herokuPort = System.getenv("PORT")
    return herokuPort?.toInt() ?: 7000
}
