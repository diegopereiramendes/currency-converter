package com.diegomendes.model

import io.javalin.http.Context
import org.jetbrains.exposed.dao.IntIdTable
import org.joda.time.DateTime

class CurrencyConverterRequest{
    var idUser: Int = 0
    var currencyOrigin : String = ""
    var valueOrigin : Float = 0f
    var currencyDestiny : String = ""
    var conversionRate : Float = 0f
    var dateTime : DateTime = DateTime.now()

    companion object Factory {
        fun create(context: Context, conversionRate: Float) : CurrencyConverterRequest {
            val currencyConverterRequest = CurrencyConverterRequest()
            val currencyOrigin = context.queryParam("currencyOrigin")!!;
            val valueOrigin = context.queryParam("originValue")!!.toFloat();
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

object CurrencyConverterTable: IntIdTable() {
    var idUser = integer("id_user")
    var currencyOrigin = varchar("currency_origin", 3)
    var valueOrigin = float("value_origin")
    var currencyDestiny = varchar("currency_destiny", 3)
    var conversionRate = float("convertion_rate")
    var datetimeRequest = datetime("datetime")
}