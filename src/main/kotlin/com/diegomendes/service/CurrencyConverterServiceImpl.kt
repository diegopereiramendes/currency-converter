package com.diegomendes.service

import com.diegomendes.dao.CurrencyConverterDAO
import com.diegomendes.domain.CurrencyConverterRequest
import com.diegomendes.domain.model.CurrencyConverter
import com.diegomendes.exceptions.RecordsNotFound

class CurrencyConverterServiceImpl(val currencyConverterDAO: CurrencyConverterDAO) : CurrencyConverterService {

    override fun convertCurrency(
        currencyConverterRequest: CurrencyConverterRequest,
        conversionRate: Float
    ): CurrencyConverter? {
        val currencyConverter = CurrencyConverter.fromCurrencyConverterRequest(currencyConverterRequest)
        return currencyConverterDAO.insert(currencyConverter, conversionRate);
    }

    override fun findAllByUser(idUser: Int): List<CurrencyConverter> {
        val transactions = currencyConverterDAO.findAllByUser(idUser)

        if (transactions.isEmpty())
            throw RecordsNotFound("Não foram encontradas transações para este usuário: ${idUser}")
        return transactions
    }
}