package com.diegomendes.model

import org.joda.time.DateTime

class CurrencyConverterResponse{

    var idTransaction: Int = 0
    var idUser: Int = 0
    var currencyOrigin : String = ""
    var valueOrigin : Float = 0f
    var currencyDestiny : String = ""
    var valueDestiny : Float = 0f
    var conversionRate :Float = 0f
    var dateTime :DateTime = DateTime.now()

    companion object Factory {
        fun create(idTransaction: Int,currencyConverterRequest: CurrencyConverterRequest) : CurrencyConverterResponse {
            val currencyConverterResponse = CurrencyConverterResponse()

            currencyConverterResponse.idTransaction = idTransaction
            currencyConverterResponse.idUser = currencyConverterRequest.idUser
            currencyConverterResponse.currencyOrigin = currencyConverterRequest.currencyOrigin
            currencyConverterResponse.valueOrigin = currencyConverterRequest.valueOrigin
            currencyConverterResponse.currencyDestiny = currencyConverterRequest.currencyDestiny
            currencyConverterResponse.conversionRate = currencyConverterRequest.conversionRate
            currencyConverterResponse.valueDestiny = currencyConverterRequest.valueOrigin * currencyConverterRequest.conversionRate
            currencyConverterResponse.dateTime = currencyConverterRequest.dateTime
            return currencyConverterResponse
        }
    }
}