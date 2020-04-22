package com.diegomendes.dao

import com.diegomendes.domain.model.CurrencyConverter

interface CurrencyConverterDAO {
    fun insert(currencyConverter: CurrencyConverter, rate: Float): CurrencyConverter
    fun findAllByUser(idUser: Int): List<CurrencyConverter>
}