package com.diegomendes.controller

import com.diegomendes.domain.CurrencyConverterRequest
import com.diegomendes.domain.model.CurrencyConverter
import com.diegomendes.enuns.Currency
import com.diegomendes.exceptions.RecordsNotFound
import com.diegomendes.service.CurrencyConverterService
import io.javalin.http.Context
import io.javalin.plugin.openapi.annotations.OpenApi
import io.javalin.plugin.openapi.annotations.OpenApiContent
import io.javalin.plugin.openapi.annotations.OpenApiParam
import io.javalin.plugin.openapi.annotations.OpenApiRequestBody
import io.javalin.plugin.openapi.annotations.OpenApiResponse
import khttp.responses.Response
import org.apache.http.HttpStatus
import org.slf4j.LoggerFactory

class CurrencyConverterController(val service: CurrencyConverterService) {
    private val logger = LoggerFactory.getLogger(javaClass)

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
    fun findAllByUser(ctx: Context) {
        val idUser = ctx.pathParam("user-id").toInt()
        try {
            ctx.json(service.findAllByUser(idUser))
            ctx.status(HttpStatus.SC_OK)
            logger.info("Successful query for user: $idUser")
        } catch (ex: RecordsNotFound) {
            ctx.status(404)
            ctx.result(ex.message!!)
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
    fun convertCurrency(ctx: Context) {
        try {
            val body = ctx.bodyAsClass(CurrencyConverterRequest::class.java)
            val response = khttp.get(
                url = "https://api.exchangeratesapi.io/latest",
                params = mapOf("base" to body.currencyOrigin.name, "symbols" to body.currencyDestiny.name)
            )
            val currencyConverter = service.convertCurrency(body, getConversionRate(response, body.currencyDestiny))!!
            ctx.json(currencyConverter)
            ctx.status(HttpStatus.SC_CREATED)
            logger.info("New currency conversion transaction performed and saved: $currencyConverter")
        } catch (ex: Exception) {
            logger.error("Error converting currency")
            throw Exception("Moedas permitidas para conversão: BRL, USD, EUR, JPY")
        }
    }

    private fun getConversionRate(response: Response, currencyDestiny: Currency): Float {
        val jsonObject = response.jsonObject
        val jsonObjectRates = jsonObject.optJSONObject(RATES_KEY)
        return jsonObjectRates.getDouble(currencyDestiny.name).toFloat()
    }

    companion object {
        const val RATES_KEY = "rates";
    }
}