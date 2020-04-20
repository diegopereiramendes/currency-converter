package com.diegomendes.utils

import com.diegomendes.enuns.Currency
import com.diegomendes.model.CurrencyConverter
import com.diegomendes.model.CurrencyConverterTable
import com.diegomendes.model.CurrencyConverterTable.dateTime
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toCurrencyConverter() = CurrencyConverter(
    idTransaction = this[CurrencyConverterTable.id].value,
    idUser = this[CurrencyConverterTable.idUser],
    currencyOrigin = Currency.valueOf(this[CurrencyConverterTable.currencyOrigin]),
    valueOrigin = this[CurrencyConverterTable.valueOrigin],
    currencyDestiny = Currency.valueOf(this[CurrencyConverterTable.currencyDestiny]),
    valueDestiny = this[CurrencyConverterTable.valueDestiny],
    conversionRate = this[CurrencyConverterTable.conversionRate],
    dateTime = this[CurrencyConverterTable.dateTime]
)