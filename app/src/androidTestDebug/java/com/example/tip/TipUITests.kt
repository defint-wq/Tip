package com.example.tip

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import com.example.tip.ui.theme.TipTheme
import org.junit.Rule
import org.junit.Test
import java.text.NumberFormat

class TipUITests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calculate_20_percent_tip() {
        composeTestRule.setContent {
            TipTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TipApp(modifier = Modifier.verticalScroll(rememberScrollState()))
                }
            }
        }

        composeTestRule.onNodeWithText("Bill Amount")
            .performScrollTo()
            .performTextInput("10")

        composeTestRule.onNodeWithText("Tip Percentage")
            .performScrollTo()
            .performTextInput("20")

        val expectedTip = NumberFormat.getCurrencyInstance().format(2)

        composeTestRule.onNodeWithText(expectedTip).assertExists(
            "No node with this text was found."
        )
    }
}