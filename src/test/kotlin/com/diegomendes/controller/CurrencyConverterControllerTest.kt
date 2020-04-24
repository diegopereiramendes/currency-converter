package com.diegomendes.controller

import com.diegomendes.domain.CurrencyConverterRequest
import com.diegomendes.domain.model.CurrencyConverter
import com.diegomendes.enuns.Currency
import com.diegomendes.exceptions.RecordsNotFound
import com.diegomendes.service.CurrencyConverterService
import io.javalin.http.Context
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CurrencyConverterControllerTest {

    private val currencyConverterService = mockk<CurrencyConverterService>()
    private val currencyConverterController = CurrencyConverterController(currencyConverterService)
    private lateinit var context: Context

    private val currencyConverter = CurrencyConverter(
        idTransaction = 10,
        idUser = 100,
        currencyOrigin = Currency.USD,
        valueOrigin = 10.0f,
        currencyDestiny = Currency.BRL,
        valueDestiny = 11.0f,
        conversionRate = 1.0f,
        dateTime = null
    )

    private val currencyConverterRequest = CurrencyConverterRequest(
        idUser = 100,
        currencyOrigin = Currency.USD,
        valueOrigin = 10.0f,
        currencyDestiny = Currency.BRL
    )

    @BeforeEach
    fun setup() {
        context = mockk(relaxed = true)
    }

    @Test
    fun whenFindingRecordsForUserYouMustReturnStatus200() {
        every { context.pathParam("user-id") } returns "10"
        every { currencyConverterService.findAllByUser(any()) } returns listOf(currencyConverter)

        currencyConverterController.findAllByUser(context)
        verify { context.status(200) }
        verify { context.json(listOf(currencyConverter)) }
    }

    @Test
    fun whenNotFindingRecordsForUserYouMustReturnStatus404() {
        every { context.pathParam("user-id") } returns "10"
        every { currencyConverterService.findAllByUser(any()) } throws RecordsNotFound("No records were found for this user")

        currencyConverterController.findAllByUser(context)
        verify { context.status(404) }
    }

    @Test
    fun whenSavingUserSuccessfullyReturnWith201() {
        every { context.bodyAsClass(CurrencyConverterRequest::class.java) } returns currencyConverterRequest
        every {
            currencyConverterService.convertCurrency(
                any(),
                any()
            )
        } returns currencyConverter

        currencyConverterController.convertCurrency(context)
        verify { context.status(201) }
        verify { context.json(currencyConverter) }
    }
}