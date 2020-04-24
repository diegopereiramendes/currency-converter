package com.diegomendes.domain.model

import com.diegomendes.domain.CurrencyConverterRequest
import com.diegomendes.enuns.Currency
import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.koin.test.KoinTest


class CurrencyConverterTest : KoinTest {
    val currencyConverterRequest = CurrencyConverterRequest(10, Currency.USD, 10.0f, Currency.BRL)
    val currencyConverter = CurrencyConverter(0, 10, Currency.USD, 10.0f, Currency.BRL, 0.0f, 0.0f, null)

    @Test
    fun shouldReturnCorrectMessage() {
        val currencyConverter = CurrencyConverter.fromCurrencyConverterRequest(currencyConverterRequest)
        assertEquals(currencyConverter, this.currencyConverter)
    }
}