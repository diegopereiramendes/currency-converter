package com.diegomendes.service

import com.diegomendes.dao.CurrencyConverterDAO
import com.diegomendes.exceptions.RecordsNotFound
import com.diegomendes.model.CurrencyConverter

class CurrencyConverterService {
    val currencyConverterDAO = CurrencyConverterDAO();

    fun currencyConverter(
        currencyConverter: CurrencyConverter,
        conversionRate: Float
    ): CurrencyConverter?{
        return currencyConverterDAO.insert(currencyConverter, conversionRate);
    }

    fun findAllByUser(idUser: Int):List<CurrencyConverter> {
        val transactions = currencyConverterDAO.findAllByUser(idUser)

        if(transactions.isEmpty())
            throw RecordsNotFound("Não foram enconrtadas transações para este usuário: ${idUser}")
        return transactions
    }
}