package com.diegomendes.dao

import com.diegomendes.model.CurrencyConverter
import com.diegomendes.model.CurrencyConverterTable
import com.diegomendes.model.fromInsertStatement
import com.diegomendes.utils.toCurrencyConverter
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class CurrencyConverterDAO {

    @Throws(Exception::class)
    fun insert(currencyConverter: CurrencyConverter, rate: Float) : CurrencyConverter {
        val currencyConverterSaved = transaction {
            CurrencyConverterTable.insert{
                it[idUser] = currencyConverter.idUser
                it[currencyOrigin] = currencyConverter.currencyOrigin.name
                it[valueOrigin] = currencyConverter.valueOrigin
                it[currencyDestiny] = currencyConverter.currencyDestiny.name
                it[valueDestiny] = currencyConverter.valueOrigin *  rate
                it[conversionRate] = rate
            }.let{currencyConverter.fromInsertStatement(it)}
        }

        return currencyConverterSaved
    }

    fun findAllByUser(idUser: Int): List<CurrencyConverter> {
        var transactions = transaction {
                CurrencyConverterTable.select{CurrencyConverterTable.idUser.eq(idUser)}.map {
                    it.toCurrencyConverter()
                }.toList()
            }
        return transactions
    }
}