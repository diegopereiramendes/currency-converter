package com.diegomendes.infra

import com.diegomendes.domain.table.CurrencyConverterTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object Database {
    
    fun start() {
        val hikariConfig = HikariConfig().apply {
            jdbcUrl = "jdbc:h2:mem:test"
            driverClassName = "org.h2.Driver"
            maximumPoolSize = 3
            isAutoCommit = true
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        Database.connect(HikariDataSource(hikariConfig))
        createTables()
    }

    private fun createTables() {
        transaction {
            SchemaUtils.create(CurrencyConverterTable)
        }
    }
}