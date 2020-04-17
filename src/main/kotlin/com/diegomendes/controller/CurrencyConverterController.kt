package com.diegomendes.controller

import com.diegomendes.model.CurrencyConverterRequest
import com.diegomendes.model.CurrencyConverterResponse
import com.diegomendes.service.CurrencyConverterService
import io.javalin.Javalin
import io.javalin.http.Context
import khttp.responses.Response

object CurrencyConverterController {
    const val RATES_KEY = "rates";
    val currencyConverterService = CurrencyConverterService();

    fun createRoute(app: Javalin){
        app.get("/convert") { ctx ->
            ctx.json(currencyConverter(ctx))
        }.error(400) { ctx ->
            ctx.result("Ocorreu um erro interno, por favor entre em contato com o administrador do sistema.")
        }
    }

    private fun currencyConverter(ctx: Context) : CurrencyConverterResponse {
        val currencyOrigin = ctx.queryParam("currencyOrigin")!!;
        val currencyDestiny = ctx.queryParam("currencyDestiny")!!;

        val response = khttp.get(
            url = "https://api.exchangeratesapi.io/latest",
            params = mapOf("base" to currencyOrigin, "symbols" to currencyDestiny))

        val currencyConverterRequest = CurrencyConverterRequest.create(ctx, getConversionRate(response, currencyDestiny));
        return currencyConverterService.currencyConverter(currencyConverterRequest)
    }

    private fun getConversionRate(response: Response, currencyDestiny: String): Double{
        val jsonObject = response.jsonObject
        val jsonObjectRates = jsonObject.optJSONObject(RATES_KEY)
        return jsonObjectRates.getDouble(currencyDestiny)
    }
}