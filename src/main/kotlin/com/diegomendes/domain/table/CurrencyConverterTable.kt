package com.diegomendes.domain.table

import com.diegomendes.domain.model.CurrencyConverter
import com.diegomendes.enuns.Currency
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.joda.time.DateTime

object CurrencyConverterTable : IntIdTable() {
    var idUser = integer("id_user")
    var currencyOrigin = varchar("currency_origin", 3)
    var valueOrigin = float("value_origin")
    var currencyDestiny = varchar("currency_destiny", 3)
    var conversionRate = float("convertion_rate")
    var valueDestiny = float("valueDestiny")
    var dateTime = datetime("datetime").default(DateTime.now())

    fun toDomain(row: ResultRow): CurrencyConverter {
        return CurrencyConverter(
            idTransaction = row[CurrencyConverterTable.id].value,
            idUser = row[idUser],
            currencyOrigin = Currency.valueOf(row[currencyOrigin]),
            valueOrigin = row[valueOrigin],
            currencyDestiny = Currency.valueOf(row[currencyDestiny]),
            valueDestiny = row[valueDestiny],
            conversionRate = row[conversionRate],
            dateTime = row[dateTime].toDate()
        )
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