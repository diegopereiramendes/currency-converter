package com.diegomendes.service

import com.diegomendes.dao.CurrencyConverterDAO
import com.diegomendes.model.CurrencyConverterRequest
import com.diegomendes.model.CurrencyConverterResponse

class CurrencyConverterService {
    val currencyConverterDAO = CurrencyConverterDAO();

    fun currencyConverter(currencyConverterRequest: CurrencyConverterRequest): CurrencyConverterResponse{
        return CurrencyConverterResponse.create(currencyConverterRequest)
    }
}