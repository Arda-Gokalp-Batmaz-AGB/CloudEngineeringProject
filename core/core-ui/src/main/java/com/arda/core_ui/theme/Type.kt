package com.arda.dystherapy.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.arda.dystherapy.core.R


private val opendyslexic = FontFamily(
    Font(R.font.opendyslexic_regular),
    Font(R.font.opendyslexic_bold),
    Font(R.font.opendyslexic_bold_italic),
    Font(R.font.opendyslexic_italic)
)
// Set of Material typography styles to start with
val typography_child = Typography(
    displayLarge = TextStyle(
        fontFamily = opendyslexic,
        fontWeight = FontWeight.W600,
        fontSize = 60.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = opendyslexic,
        fontWeight = FontWeight.W600,
        fontSize = 30.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = opendyslexic,
        fontWeight = FontWeight.W600,
        fontSize = 24.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = opendyslexic,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp
    ),
    titleMedium = TextStyle(
        fontFamily = opendyslexic,
        fontWeight = FontWeight.W500,
        fontSize = 20.sp
    ),
    titleSmall = TextStyle(
        fontFamily = opendyslexic,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = opendyslexic,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = opendyslexic,
        fontSize = 12.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = opendyslexic,
        fontSize = 12.sp,
        lineHeight = 24.sp
    ),
    displaySmall = TextStyle(
        fontFamily = opendyslexic,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    displayMedium = TextStyle(
        fontFamily = opendyslexic,
        fontSize = 12.sp,
        lineHeight = 20.sp
    ),
//    displayLarge = TextStyle(
//        fontFamily = opendyslexic,
//        fontSize = 12.sp,
//        lineHeight = 24.sp
//    ),

    labelLarge = TextStyle(
        fontFamily = opendyslexic,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = opendyslexic,
        fontWeight = FontWeight.W500,
        fontSize = 8.sp
    ),
    labelMedium = TextStyle(
        fontFamily = opendyslexic,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp

    )
)


private val roboto = FontFamily(
    Font(R.font.roboto_black),
    Font(R.font.roboto_medium),
    Font(R.font.roboto_italic),
    Font(R.font.roboto_light),
    Font(R.font.roboto_regular),
)

val typography_parent = Typography(
    headlineLarge = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.W600,
        fontSize = 30.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.W600,
        fontSize = 24.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp
    ),
    titleMedium = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.W500,
        fontSize = 20.sp
    ),
    titleSmall = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = roboto,
        fontSize = 12.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = roboto,
        fontSize = 12.sp,
        lineHeight = 24.sp
    ),
    displaySmall = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    displayMedium = TextStyle(
        fontFamily = roboto,
        fontSize = 12.sp,
        lineHeight = 20.sp
    ),
    displayLarge = TextStyle(
        fontFamily = roboto,
        fontSize = 12.sp,
        lineHeight = 24.sp
    ),

    labelLarge = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.W500,
        fontSize = 8.sp
    ),
    labelMedium = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp

    )
)

