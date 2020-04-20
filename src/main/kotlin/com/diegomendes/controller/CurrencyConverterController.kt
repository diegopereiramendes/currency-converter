package com.diegomendes.controller

import com.diegomendes.enuns.Currency
import com.diegomendes.model.CurrencyConverter
import com.diegomendes.model.CurrencyConverterRequest
import com.diegomendes.service.CurrencyConverterService
import io.javalin.apibuilder.ApiBuilder
import io.javalin.http.Context
import io.javalin.plugin.openapi.annotations.*
import khttp.responses.Response
import org.apache.http.HttpStatus

object CurrencyConverterController {
    const val RATES_KEY = "rates";
    val currencyConverterService = CurrencyConverterService();

    fun createRoutes() = run {
        ApiBuilder.path("convert") {
            ApiBuilder.get("/:user-id", this::findAllByUser)
            ApiBuilder.post(this::convertCurrency)
        }
    }

    @OpenApi(
        summary = "Searches for all a user's currency conversion transactions",
        operationId = "convertCurrency",
        tags = ["Currency Converter"],
        pathParams = [
            OpenApiParam("user-id", Integer::class, "Id of the user who wishes to consult the transactions")],
        responses = [
            OpenApiResponse(
                "200", [OpenApiContent(CurrencyConverter::class)]
            )]
    )
    private fun findAllByUser(ctx: Context) {
        try {
            val idUser = ctx.pathParam("user-id").toInt()
            ctx.json(currencyConverterService.findAllByUser(idUser))
            ctx.status(HttpStatus.SC_OK)
        } catch (ex: KotlinNullPointerException) {
            throw Exception("Parâmetros inválidos")
        }
    }

    @OpenApi(
        summary = "Creates a currency conversion transaction for a user",
        operationId = "convertCurrency",
        tags = ["Currency Converter"],
        requestBody = OpenApiRequestBody(
            [
                OpenApiContent(CurrencyConverterRequest::class)
            ]
        ),
        responses = [
            OpenApiResponse(
                "200", [OpenApiContent(CurrencyConverter::class)]
            )]
    )
    private fun convertCurrency(ctx: Context) {
        try {
            val body = ctx.bodyAsClass(CurrencyConverterRequest::class.java)
            val response = khttp.get(
                url = "https://api.exchangeratesapi.io/latest",
                params = mapOf("base" to body.currencyOrigin.name, "symbols" to body.currencyDestiny.name)
            )

            ctx.json(
                currencyConverterService.convertCurrency(
                    body,
                    getConversionRate(response, body.currencyDestiny)
                )!!
            )
            ctx.status(HttpStatus.SC_CREATED)
        } catch (ex: IllegalArgumentException) {
            throw IllegalArgumentException("Moedas permitidas para conversão: BRL, USD, EUR, JPY")
        } catch (ex: KotlinNullPointerException) {
            throw Exception("Parâmetros inválidos")
        }
    }

    private fun getConversionRate(response: Response, currencyDestiny: Currency): Float {
        val jsonObject = response.jsonObject
        val jsonObjectRates = jsonObject.optJSONObject(RATES_KEY)
        return jsonObjectRates.getDouble(currencyDestiny.name).toFloat()
    }
}