package com.diegomendes.service

import com.diegomendes.dao.CurrencyConverterDAO
import com.diegomendes.model.CurrencyConverterRequest
import com.diegomendes.model.CurrencyConverterResponse

class CurrencyConverterService {
    val currencyConverterDAO = CurrencyConverterDAO();

    fun currencyConverter(currencyConverterRequest: CurrencyConverterRequest): CurrencyConverterResponse{
        var idTransaction = 0
        try {
            idTransaction = currencyConverterDAO.insert(currencyConverterRequest);
        }catch (e: Exception){
            e.printStackTrace()
        }
        return CurrencyConverterResponse.create(idTransaction, currencyConverterRequest)
    }
}