package com.diegomendes.dao

import com.diegomendes.domain.model.CurrencyConverter
import com.diegomendes.domain.table.CurrencyConverterTable
import com.diegomendes.domain.table.fromInsertStatement
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class CurrencyConverterDAOImpl : CurrencyConverterDAO {

    override fun insert(
        currencyConverter: CurrencyConverter, rate: Float
    ): CurrencyConverter {
        val currencyConverterSaved = transaction {
            CurrencyConverterTable.insert {
                it[idUser] = currencyConverter.idUser
                it[currencyOrigin] = currencyConverter.currencyOrigin.name
                it[valueOrigin] = currencyConverter.valueOrigin
                it[currencyDestiny] = currencyConverter.currencyDestiny.name
                it[valueDestiny] = currencyConverter.valueOrigin * rate
                it[conversionRate] = rate
            }.let { currencyConverter.fromInsertStatement(it) }
        }
        return currencyConverterSaved
    }

    override fun findAllByUser(idUser: Int): List<CurrencyConverter> {
        var transactions = transaction {
            CurrencyConverterTable.select { CurrencyConverterTable.idUser.eq(idUser) }
                .map { row -> CurrencyConverterTable.toDomain(row) }.toList()
        }
        return transactions
    }
}