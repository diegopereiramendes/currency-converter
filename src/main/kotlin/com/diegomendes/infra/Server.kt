package com.diegomendes.infra

import com.diegomendes.controller.CurrencyConverterController
import com.diegomendes.exceptions.RecordsNotFound
import io.javalin.Javalin
import io.javalin.plugin.openapi.OpenApiOptions
import io.javalin.plugin.openapi.OpenApiPlugin
import io.javalin.plugin.openapi.ui.SwaggerOptions
import io.swagger.v3.oas.models.info.Info
import org.apache.http.HttpStatus


object Server {
    fun start() {
        val app = Javalin.create {
            it.registerPlugin(getConfiguredOpenApiPlugin())
            it.defaultContentType = "application/json"
        }.routes {
            CurrencyConverterController.createRoutes()
        }.start(getHerokuAssignedPort())
        createRoutesErrors(app)
    }

    private fun createRoutesErrors(app: Javalin) {
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

    fun getConfiguredOpenApiPlugin() = OpenApiPlugin(
        OpenApiOptions(
            Info().apply {
                version("1.0")
                description("Converter Currency API")
            }
        ).apply {
            path("/swagger-docs") // endpoint for OpenAPI json
            swagger(SwaggerOptions("/swagger-ui")) // endpoint for swagger-ui
        }
    )
}