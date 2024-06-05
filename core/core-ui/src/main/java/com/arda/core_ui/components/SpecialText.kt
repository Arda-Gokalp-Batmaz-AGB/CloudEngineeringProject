package com.arda.dystherapy.components

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.arda.dystherapy.util.DebugTagsEnumUtils
import com.arda.dystherapy.utils.BoldAndColorNeededCharactersUtils

private val TAG = DebugTagsEnumUtils.UITag.tag

val userDiffChars =
    compositionLocalOf<List<Char>> { error("User chars not found!") }

@Composable
fun SpecialText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,//MaterialTheme.typography.bodyLarge,// LocalTextStyle.current,
    boldFirstHalf: Boolean = true,
    zoomable: Boolean = false,
//    allowTypeWriterEffect: Boolean = false,
//    vocalizeTypeEnum: TextVocalizeTypeEnum = TextVocalizeTypeEnum.DIRECT,
) {
//    val diffChars = loggedCheckState.current.uiState.collectAsState().value.detailedUser?.let {
//        it.diffChars
//    } ?: listOf('b', 'p', 'd')
    //Log.v(TAG,"Loggedcheckstate: ${LoggedCheckStateProvider.}")


        val diffChars = userDiffChars.current

        var annotatedText = BoldAndColorNeededCharactersUtils(
            originalColor = color,
            text = text,
            fontSize = fontSize,
            boldFirstHalf = boldFirstHalf,
            diffChars = diffChars
        )

        val textToDisplay by rememberUpdatedState(newValue = annotatedText)

        MakeTextZoomable(isZoomable = zoomable) {
            Text(
                text = textToDisplay,
                modifier = modifier,
                color = color,
                fontSize = fontSize,
                fontStyle = fontStyle,
                fontWeight = fontWeight,
                fontFamily = fontFamily,
                letterSpacing = letterSpacing,
                textDecoration = textDecoration,
                textAlign = textAlign,
                lineHeight = lineHeight,
                overflow = overflow,
                softWrap = softWrap,
                maxLines = maxLines,
                inlineContent = inlineContent,
                onTextLayout = onTextLayout,
                style = style.copy(lineHeight = 20.sp),
            )
        }

}
