package com.diegomendes.domain

import com.diegomendes.enuns.Currency

data class CurrencyConverterRequest(
    val idUser: Int, val currencyOrigin: Currency,
    val valueOrigin: Float, val currencyDestiny: Currency
)
