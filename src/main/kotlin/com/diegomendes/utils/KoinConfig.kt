package com.diegomendes.utils

import com.diegomendes.controller.CurrencyConverterController
import com.diegomendes.dao.CurrencyConverterDAO
import com.diegomendes.dao.CurrencyConverterDAOImpl
import com.diegomendes.service.CurrencyConverterService
import com.diegomendes.service.CurrencyConverterServiceImpl
import org.koin.dsl.module.module

object KoinConfig {
    private val currencyConverterModule = module {
        single { CurrencyConverterController(get()) }
        single<CurrencyConverterService> { CurrencyConverterServiceImpl(get()) }
        single<CurrencyConverterDAO> { CurrencyConverterDAOImpl() }
    }

    internal val allModules = listOf(
        currencyConverterModule
    )
}