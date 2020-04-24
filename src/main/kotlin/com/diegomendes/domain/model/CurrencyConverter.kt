package com.diegomendes.domain.model

import com.diegomendes.domain.CurrencyConverterRequest
import com.diegomendes.domain.table.CurrencyConverterTable
import com.diegomendes.enuns.Currency
import org.jetbrains.exposed.sql.statements.InsertStatement
import java.util.Date

data class CurrencyConverter(
    val idTransaction: Int,
    val idUser: Int,
    val currencyOrigin: Currency,
    val valueOrigin: Float,
    val currencyDestiny: Currency,
    val valueDestiny: Float,
    val conversionRate: Float,
    val dateTime: Date?
) {
    companion object Factory {
        fun fromCurrencyConverterRequest(
            currencyConverterRequest: CurrencyConverterRequest
        ): CurrencyConverter {
            return CurrencyConverter(
                idTransaction = 0,
                idUser = currencyConverterRequest.idUser,
                currencyOrigin = currencyConverterRequest.currencyOrigin,
                valueOrigin = currencyConverterRequest.valueOrigin,
                currencyDestiny = currencyConverterRequest.currencyDestiny,
                valueDestiny = 0.0f,
                conversionRate = 0.0f,
                dateTime = null
            )
        }
    }
}


fun CurrencyConverter.fromInsertStatement(it: InsertStatement<Number>) =
    CurrencyConverter(
        idTransaction = it[CurrencyConverterTable.id].value,
        idUser = it[CurrencyConverterTable.idUser],
        currencyOrigin = Currency.valueOf(it[CurrencyConverterTable.currencyOrigin]),
        valueOrigin = it[CurrencyConverterTable.valueOrigin],
        currencyDestiny = Currency.valueOf(it[CurrencyConverterTable.currencyDestiny]),
        valueDestiny = it[CurrencyConverterTable.valueDestiny],
        conversionRate = it[CurrencyConverterTable.conversionRate],
        dateTime = it[CurrencyConverterTable.dateTime].toDate()
    )
