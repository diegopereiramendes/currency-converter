package com.diegomendes.domain.model

import com.diegomendes.domain.CurrencyConverterRequest
import com.diegomendes.enuns.Currency
import java.util.*

data class CurrencyConverter(
    val idTransaction: Int,
    val idUser: Int,
    val currencyOrigin: Currency,
    val valueOrigin: Float,
    val currencyDestiny: Currency,
    val valueDestiny: Float,
    val conversionRate: Float,
    val dateTime: Date
) {
    companion object Factory {
        fun fromCurrencyConverterRequest(currencyConverterRequest: CurrencyConverterRequest): CurrencyConverter {
            return CurrencyConverter(
                idTransaction = 0,
                idUser = currencyConverterRequest.idUser,
                currencyOrigin = currencyConverterRequest.currencyOrigin,
                valueOrigin = currencyConverterRequest.valueOrigin,
                currencyDestiny = currencyConverterRequest.currencyDestiny,
                valueDestiny = 0.0f,
                conversionRate = 0.0f,
                dateTime = Date()
            )
        }
    }
}
