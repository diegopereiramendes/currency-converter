package com.diegomendes.service

import com.diegomendes.domain.CurrencyConverterRequest
import com.diegomendes.domain.model.CurrencyConverter

interface CurrencyConverterService {
    fun convertCurrency(currencyConverterRequest: CurrencyConverterRequest, conversionRate: Float): CurrencyConverter?
    fun findAllByUser(idUser: Int): List<CurrencyConverter>
}