package com.diegomendes.model

import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CurrencyConverterResponse{
    val idTransaction : UUID = UUID.randomUUID()
    var idUser: Int = 0
    var currencyOrigin : String = ""
    var valueOrigin : Double = 0.0
    var currencyDestiny : String = ""
    var valueDestiny : Double = 0.0
    var conversionRate :Double = 0.0
    var dataHoraUTCTime : String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))

    companion object Factory {

        fun create(currencyConverterRequest: CurrencyConverterRequest) : CurrencyConverterResponse {
            val currencyConverterResponse = CurrencyConverterResponse()

            currencyConverterResponse.idUser = currencyConverterRequest.idUser
            currencyConverterResponse.currencyOrigin = currencyConverterRequest.currencyOrigin
            currencyConverterResponse.valueOrigin = currencyConverterRequest.valueOrigin
            currencyConverterResponse.currencyDestiny = currencyConverterRequest.currencyDestiny
            currencyConverterResponse.conversionRate = currencyConverterRequest.conversionRate
            currencyConverterResponse.valueDestiny = currencyConverterRequest.valueOrigin * currencyConverterRequest.conversionRate
            return currencyConverterResponse
        }
    }
}