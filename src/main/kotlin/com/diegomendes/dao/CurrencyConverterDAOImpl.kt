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
        return transaction {
            CurrencyConverterTable.insert {
                it[idUser] = currencyConverter.idUser
                it[currencyOrigin] = currencyConverter.currencyOrigin.name
                it[valueOrigin] = currencyConverter.valueOrigin
                it[currencyDestiny] = currencyConverter.currencyDestiny.name
                it[valueDestiny] = currencyConverter.valueOrigin * rate
                it[conversionRate] = rate
            }.let { currencyConverter.fromInsertStatement(it) }
        }
    }

    override fun findAllByUser(idUser: Int): List<CurrencyConverter> {
        return transaction {
            CurrencyConverterTable.select { CurrencyConverterTable.idUser.eq(idUser) }
                .map { row -> CurrencyConverterTable.toDomain(row) }.toList()
        }
    }
}