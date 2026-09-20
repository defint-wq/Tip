package com.example.tip

import android.health.connect.datatypes.units.Percentage
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.tip.ui.theme.TipTheme
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import kotlin.math.round

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipTheme {
                    TipApp(modifier = Modifier.verticalScroll(rememberScrollState()))
            }
        }
    }
}

@Composable
fun TipApp( modifier: Modifier = Modifier) {
    var amountInput by remember { mutableStateOf(value = "") }
    var tipInput by remember { mutableStateOf(value = "") }
    var roundUp by remember { mutableStateOf(value = false) }

    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = tipInput.toDoubleOrNull() ?: 0.0

    val tip = calculateTip(billAmount = amount, tipPercentage = tipPercent, roundUp = roundUp)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 50.dp, vertical = 80.dp)
            ,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            modifier = Modifier
                .align(alignment = Alignment.Start)
                .fillMaxWidth(),
        )
        InputValue(
            modifier = Modifier,
            value = amountInput,
            onValueChange = { amountInput = it},
            label = R.string.bill_amount
        )
        InputValue(
            modifier = Modifier,
            value = tipInput,
            onValueChange = { tipInput = it },
            label = R.string.how_was_the_service
        )
        RoundTheTipRow(
            modifier = Modifier.padding(bottom = 5.dp),
            roundUp = roundUp,
            onRoundUpChanged = { roundUp = it }
        )
        Row(
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(text = stringResource((R.string.tip_amount)))
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = tip)
        }
    }
}

@Composable
fun InputValue(
    @StringRes label: Int,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    TextField(
        label = {Text(stringResource(label))},
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        )
    )
}

@Composable
fun RoundTheTipRow(
    modifier: Modifier = Modifier,
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.round_up_tip))
        Spacer(modifier = modifier.width(8.dp))
        Switch(
            checked = roundUp,
            onCheckedChange = onRoundUpChanged,
        )
    }
}


private fun calculateTip(billAmount: Double, tipPercentage: Double, roundUp: Boolean): String {
    var tip = (billAmount / 100) * tipPercentage
    if (roundUp) tip = kotlin.math.ceil(tip)

    return NumberFormat.getCurrencyInstance().format(tip)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TipTheme {
        TipApp()
    }
}