package com.diegomendes.controller

import com.diegomendes.enuns.Currency
import com.diegomendes.model.CurrencyConverter
import com.diegomendes.service.CurrencyConverterService
import io.javalin.Javalin
import io.javalin.http.Context
import khttp.responses.Response
import org.apache.http.HttpStatus

object CurrencyConverterController {
    const val RATES_KEY = "rates";
    val currencyConverterService = CurrencyConverterService();

    fun createRoute(app: Javalin){
        app.post("/convert") { ctx ->
            ctx.json(currencyConverter(ctx))
            ctx.status(HttpStatus.SC_CREATED)
        }
        app.get("/convert") { ctx ->
            ctx.json(findAllByUser(ctx))
            ctx.status(HttpStatus.SC_OK)
        }
    }

    private fun findAllByUser(ctx: Context) : List<CurrencyConverter> {
        try {
            val idUser = ctx.queryParam("idUser")!!
            return currencyConverterService.findAllByUser(idUser.toInt())
        }catch (ex: KotlinNullPointerException){
            var i: Int = 10
            throw Exception("Parâmetros inválidos")
        }
    }

    private fun currencyConverter(ctx: Context) : CurrencyConverter {
        try {
            val body = ctx.bodyAsClass(CurrencyConverter::class.java)
            val response = khttp.get(
                url = "https://api.exchangeratesapi.io/latest",
                params = mapOf("base" to body.currencyOrigin.name, "symbols" to body.currencyDestiny.name))
            return currencyConverterService.currencyConverter(body, getConversionRate(response, body.currencyDestiny))!!
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