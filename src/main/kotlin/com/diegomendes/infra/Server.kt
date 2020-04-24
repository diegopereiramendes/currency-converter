package com.diegomendes.infra

import com.diegomendes.controller.CurrencyConverterController
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.plugin.json.JavalinJackson
import io.javalin.plugin.openapi.OpenApiOptions
import io.javalin.plugin.openapi.OpenApiPlugin
import io.javalin.plugin.openapi.ui.SwaggerOptions
import io.swagger.v3.oas.models.info.Info
import org.apache.http.HttpStatus
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.SimpleDateFormat

object Server : KoinComponent {
    private val currencyConverterController: CurrencyConverterController by inject()

    fun start(): Javalin {
        val app = Javalin.create {
            this.configureMapper()
            it.registerPlugin(getConfiguredOpenApiPlugin())
            it.defaultContentType = "application/json"
        }.routes {
            path("convert") {
                get("/:user-id", currencyConverterController::findAllByUser)
                post(currencyConverterController::convertCurrency)
            }
        }.start(getHerokuAssignedPort())
        createRoutesErrors(app)
        return app
    }

    private fun createRoutesErrors(app: Javalin) {
        app.exception(Exception::class.java) { e, ctx ->
            ctx.json(e.message!!)
            ctx.status(HttpStatus.SC_BAD_REQUEST)
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

    private fun configureMapper() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        JavalinJackson.configure(
            jacksonObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(dateFormat)
                .configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, true)
        )
    }
}