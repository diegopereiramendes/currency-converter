package com.diegomendes.dao

import com.diegomendes.domain.model.CurrencyConverter
import com.diegomendes.domain.model.fromInsertStatement
import com.diegomendes.domain.table.CurrencyConverterTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

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
                it[dateTime] = DateTime.now()
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