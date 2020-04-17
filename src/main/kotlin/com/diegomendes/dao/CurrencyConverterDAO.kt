package com.diegomendes.dao

import com.diegomendes.model.CurrencyConverterRequest
import com.diegomendes.model.CurrencyConverterTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class CurrencyConverterDAO {

    @Throws(Exception::class)
    fun insert(currencyConverterRequest: CurrencyConverterRequest) : Int {
        var idTransaction = transaction {
            CurrencyConverterTable.insert{
                it[idUser] = currencyConverterRequest.idUser
                it[currencyOrigin] = currencyConverterRequest.currencyOrigin
                it[valueOrigin] = currencyConverterRequest.valueOrigin
                it[currencyDestiny] = currencyConverterRequest.currencyDestiny
                it[conversionRate] = currencyConverterRequest.conversionRate
                it[datetimeRequest] = currencyConverterRequest.dateTime
            }get CurrencyConverterTable.id
        }
        return idTransaction.value
    }
}