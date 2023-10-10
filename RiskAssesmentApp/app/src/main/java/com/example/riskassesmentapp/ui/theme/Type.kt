package com.example.riskassesmentapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val Monstserrat = FontFamily(
    Font(R.font.montserrat-light, FontWeight.Light),
    Font(R.font.montserrat-regular, FontWeight.Normal),
    Font(R.font.montserrat-italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.montserrat-lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.montserrat-semibold, FontWeight.SemiBold)
)

private val NotoSans = FontFamily(
    Font(R.font.notosans-light, FontWeight.Light),
    Font(R.font.notosans-regular, FontWeight.Normal),
    Font(R.font.notosans-italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.notosans-lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.notosans-semibold, FontWeight.SemiBold)
)


val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)
