package com.example.tip

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.NumberFormat

class TipTests {
    @Test
    fun calculateTip_20PercentNoRoundup() {
        val amount = 10.00
        val tipPercent = 20.00
        val expectedTip = NumberFormat.getCurrencyInstance().format(2)
        val actualTip = calculateTip(billAmount = amount, tipPercentage = tipPercent, false)
        assertEquals(expectedTip, actualTip)
    }

}