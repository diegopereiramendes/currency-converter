package com.diegomendes.service

import com.diegomendes.dao.CurrencyConverterDAO
import com.diegomendes.exceptions.RecordsNotFound
import com.diegomendes.model.CurrencyConverter
import com.diegomendes.model.CurrencyConverterRequest

class CurrencyConverterService {
    val currencyConverterDAO = CurrencyConverterDAO();

    fun convertCurrency(
        currencyConverterRequest: CurrencyConverterRequest,
        conversionRate: Float
    ): CurrencyConverter? {
        val currencyConverter = CurrencyConverter.fromCurrencyConverterRequest(currencyConverterRequest)
        return currencyConverterDAO.insert(currencyConverter, conversionRate);
    }

    fun findAllByUser(idUser: Int): List<CurrencyConverter> {
        val transactions = currencyConverterDAO.findAllByUser(idUser)

        if (transactions.isEmpty())
            throw RecordsNotFound("Não foram enconrtadas transações para este usuário: ${idUser}")
        return transactions
    }
}