package com.diegomendes.controller

import io.mockk.every
import io.mockk.mockkConstructor
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Teste {

    @Test
    fun diego() {
        mockkConstructor(MockCls::class)

        every { anyConstructed<MockCls>().add(1, 2) } returns 4

        assertEquals(4, MockCls().add(1, 2)) // note new object is created

//        verify { anyConstructed<MockCls>().add(1, 2) }
    }

}

class MockCls {
    fun add(a: Int, b: Int) = a + b
}
