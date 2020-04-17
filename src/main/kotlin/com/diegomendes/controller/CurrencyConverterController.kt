package com.diegomendes.controller

import com.diegomendes.enuns.Currency
import com.diegomendes.model.CurrencyConverterRequest
import com.diegomendes.model.CurrencyConverterResponse
import com.diegomendes.service.CurrencyConverterService
import io.javalin.Javalin
import io.javalin.http.Context
import khttp.responses.Response
import org.apache.http.HttpStatus
import java.lang.Exception
import java.lang.IllegalArgumentException

object CurrencyConverterController {
    const val RATES_KEY = "rates";
    val currencyConverterService = CurrencyConverterService();

    fun createRoute(app: Javalin){
        app.get("/convert") { ctx ->
            ctx.json(currencyConverter(ctx))
            ctx.status(HttpStatus.SC_CREATED)
        }
    }

    private fun currencyConverter(ctx: Context) : CurrencyConverterResponse {
        try {
            val currencyOrigin = Currency.valueOf(ctx.queryParam("currencyOrigin")!!)
            val currencyDestiny = Currency.valueOf(ctx.queryParam("currencyDestiny")!!)
            val response = khttp.get(
                url = "https://api.exchangeratesapi.io/latest",
                params = mapOf("base" to currencyOrigin.name, "symbols" to currencyDestiny.name))
            val currencyConverterRequest = CurrencyConverterRequest.create(ctx, getConversionRate(response, currencyDestiny));
            return currencyConverterService.currencyConverter(currencyConverterRequest)
        }catch (ex: IllegalArgumentException){
            throw IllegalArgumentException("Moedas permitidas para conversão: BRL, USD, EUR, JPY")
        }catch (ex: KotlinNullPointerException){
            throw Exception("Parâmetros inválidos")
        }
    }

    private fun getConversionRate(response: Response, currencyDestiny: Currency): Float{
        val jsonObject = response.jsonObject
        val jsonObjectRates = jsonObject.optJSONObject(RATES_KEY)
        return jsonObjectRates.getDouble(currencyDestiny.name).toFloat()
    }
}