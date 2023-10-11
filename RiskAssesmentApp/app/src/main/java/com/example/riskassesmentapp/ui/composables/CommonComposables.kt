package com.example.riskassesmentapp.ui.composables

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.riskassesmentapp.ui.theme.RiskAssesmentAppTheme

    @Composable
    fun Title(title: String, modifier: Modifier) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontFamily = FontFamily.Serif
        )
    }
    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        RiskAssesmentAppTheme {
            Greeting("Android")
        }
    }
