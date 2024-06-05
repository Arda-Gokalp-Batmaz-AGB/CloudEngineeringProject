package com.arda.core_ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.arda.auth_ui.R


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

