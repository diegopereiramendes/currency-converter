package com.diegomendes.service

import com.diegomendes.model.CurrencyConverter
import com.diegomendes.model.CurrencyConverterRequest

interface CurrencyConverterService {
    fun convertCurrency(currencyConverterRequest: CurrencyConverterRequest, conversionRate: Float): CurrencyConverter?
    fun findAllByUser(idUser: Int): List<CurrencyConverter>
}