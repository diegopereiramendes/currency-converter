package com.diegomendes.model

import io.javalin.http.Context
import java.util.*

class CurrencyConverterRequest{
    var idUser: Int = 0
    var currencyOrigin : String = ""
    var valueOrigin : Double = 0.0
    var currencyDestiny : String = ""
    var conversionRate : Double = 0.0
    var dataHoraUTCTime : String = ""

    companion object Factory {
        fun create(context: Context, conversionRate: Double) : CurrencyConverterRequest {
            val currencyConverterRequest = CurrencyConverterRequest()
            val currencyOrigin = context.queryParam("currencyOrigin")!!;
            val valueOrigin = context.queryParam("originValue")!!.toDouble();
            val currencyDestiny = context.queryParam("currencyDestiny")!!;
            val idUser = context.queryParam("idUser")!!.toInt();

            currencyConverterRequest.idUser = idUser
            currencyConverterRequest.currencyOrigin = currencyOrigin
            currencyConverterRequest.valueOrigin = valueOrigin
            currencyConverterRequest.currencyDestiny = currencyDestiny
            currencyConverterRequest.conversionRate = conversionRate
            return currencyConverterRequest
        }
    }
}