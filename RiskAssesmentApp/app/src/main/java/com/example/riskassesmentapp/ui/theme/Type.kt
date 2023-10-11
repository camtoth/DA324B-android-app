package com.example.riskassesmentapp.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.riskassesmentapp.R

val Montserrat = FontFamily(
    Font(R.font.montserrat_light, FontWeight.Light),
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.montserrat_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.montserrat_semibold, FontWeight.SemiBold)
)

val NotoSans = FontFamily(
    Font(R.font.notosans_light, FontWeight.Light),
    Font(R.font.notosans_regular, FontWeight.Normal),
    Font(R.font.notosans_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.notosans_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.notosans_semibold, FontWeight.SemiBold)
)


val Typography = Typography(
    titleLarge = TextStyle(         //used for screen titles
            fontFamily = Montserrat,
            fontSize = 24.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp

        ),

    titleMedium = TextStyle(           //used for case detail headings (first, last name),Question titles, etc...
            fontFamily = Montserrat,
            fontSize = 20.sp,
//            lineHeight = 24.sp,
//            letterSpacing = 0.5.sp
        ),

        titleSmall = TextStyle(     //used for case detail sub-headings
            fontFamily = Montserrat,
//            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
//            lineHeight = 24.sp,
        ),

        bodyLarge = TextStyle(          //used for information text
            fontFamily = NotoSans,
//            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
//            lineHeight = 24.sp,

        ),

        bodyMedium = TextStyle(             //used for general text
            fontFamily = NotoSans,
//            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
//            lineHeight = 24.sp,
            ),

        bodySmall = TextStyle (             //used for detail text
            fontFamily = NotoSans,
//            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
//            lineHeight = 24.sp,
        ),

        labelLarge = TextStyle (            //used for Question answers
            fontFamily = NotoSans,
//            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
//            lineHeight = 24.sp,

        ),
        labelMedium = TextStyle(            //used to highlight bottom bar navigation
            fontFamily = NotoSans,
//            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
//            lineHeight = 24.sp,
        ),

        labelSmall = TextStyle(             //used for general bottom bar navigation
            fontFamily = NotoSans,
//            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
//            lineHeight = 24.sp,

        )
)

