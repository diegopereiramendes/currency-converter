package com.diegomendes.infra

import com.diegomendes.controller.CurrencyConverterController
import com.diegomendes.exceptions.RecordsNotFound
import com.diegomendes.utils.KoinConfig.allModules
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.plugin.json.JavalinJackson
import io.javalin.plugin.openapi.OpenApiOptions
import io.javalin.plugin.openapi.OpenApiPlugin
import io.javalin.plugin.openapi.ui.SwaggerOptions
import io.swagger.v3.oas.models.info.Info
import org.apache.http.HttpStatus
import org.koin.core.KoinProperties
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject
import java.text.SimpleDateFormat

object Server : KoinComponent {

    private val currencyConverterController: CurrencyConverterController by inject()

    fun start() {
        StandAloneContext.startKoin(
            allModules,
            KoinProperties(true, true)
        )
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

    private fun configureMapper() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        JavalinJackson.configure(
            jacksonObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(dateFormat)
                .configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, true)
        )
    }
}