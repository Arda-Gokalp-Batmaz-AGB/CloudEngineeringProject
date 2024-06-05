package com.arda.dystherapy.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
private fun matchColorAndChar(originalColor : Color,chars : List<Char>, colors : List<Color>, currentChar : Char) : Color{
    val idx = chars.indexOf(currentChar)
    if(idx == -1)
        return originalColor
    return colors[idx]
}
val charColor_1 = Color(0xFFE53935)
val charColor_2 = Color(0xFFFB8C00)
val charColor_3 = Color(0xFF00897B)
val charColor_4 = Color(0xFF9C27B0)
val charColor_5 = Color(0xFFF509DD)
val charColor_6 = Color(0xFF00FFB7)
class BoldAndColorNeededCharactersUtils {
    companion object {
        operator fun invoke(
            originalColor : Color = Color.Black,
            text: String,
            fontSize: TextUnit = TextUnit.Unspecified,
            boldFirstHalf: Boolean = true,
            diffChars : List<Char>
        ): AnnotatedString {
            val charColors = listOf(charColor_1, charColor_2, charColor_3, charColor_4, charColor_5,
                charColor_6)
            val words = text.split(" ")
            val annotatedText = buildAnnotatedString {
                words.forEach { word ->
                    val firstHalfBoldedWord = buildAnnotatedString {
                        var maxBoldCount = (word.length / 2) + 1
                        word.forEach { char ->
                            val boldness = if (maxBoldCount > 0 && boldFirstHalf) {
                                FontWeight.Bold
                            } else {
                                FontWeight.Normal
                            }//zorlandıgı harfleri aynı sırayla tutarsın ve aynı sırayla renk skalası olur o renkleri koyarsın
                            val charColor = matchColorAndChar(originalColor,diffChars, charColors, char)

                            withStyle(
                                style = SpanStyle(
                                    fontSize = fontSize,
                                    fontWeight = boldness,
                                    color = charColor
                                )
                            ) {
                                append(char)
                            }
                            maxBoldCount -= 1
                        }
                        append(" ")
                    }
                    append(firstHalfBoldedWord)
                }
            }
            return annotatedText
        }
    }
}