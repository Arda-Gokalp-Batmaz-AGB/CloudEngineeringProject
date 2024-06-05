package com.arda.dystherapy.components.snackbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arda.dystherapy.components.SpecialText
import com.arda.dystherapy.components.providers.LocalSnackbarHostState
import com.arda.dystherapy.components.userDiffChars
import com.arda.dystherapy.core.R
import com.arda.dystherapy.theme.DystherapyPREVIEWTheme
import com.arda.dystherapy.theme.Theme
import com.arda.dystherapy.util.ResourceProvider
import com.arda.dystherapy.validation.StringResourceEnum

@Composable
fun SnackBarShowcase(receivedSnackbarData: SnackbarData) {
    val snackbarHostState = LocalSnackbarHostState.current
    Box(
        modifier = Modifier
            .fillMaxSize(1f)
            .clickable {
                snackbarHostState.currentSnackbarData?.dismiss()

                       },
        contentAlignment = Alignment.Center,
    )
    {
//        Image(
//            modifier = Modifier.fillMaxSize(),
//            painter = rememberAsyncImagePainter(R.drawable.bg_new),
//            contentScale = ContentScale.FillBounds,
//            contentDescription = "bg"
//        )
        when (receivedSnackbarData.snackBarTypeEnum) {
            SnackBarTypeEnum.OPERATION_SUCESS -> Operation_Sucess_Snackbar(
                receivedSnackbarData.message,
                snackbarHostState
            )

            SnackBarTypeEnum.OPERATION_ERROR -> Operation_Error_Snackbar(
                receivedSnackbarData.message,
                snackbarHostState
            )

            SnackBarTypeEnum.QUESTION_CORRECT -> Question_Correct_Snackbar(
                receivedSnackbarData.message,
                snackbarHostState
            )

            SnackBarTypeEnum.QUESTION_WRONG -> Question_Wrong_Snackbar(
                receivedSnackbarData.message,
                snackbarHostState
            )

            SnackBarTypeEnum.QUESTIONS_FINISHED -> Question_Finished_Snackbar(
                receivedSnackbarData.message,
                snackbarHostState
            )
        }
    }

}

@Composable
private fun Operation_Sucess_Snackbar(message: String, snackbarHostState: SnackbarHostState) {
    GenericQuestionSituationSnackbar(
        modifier = Modifier,
        backgroundColor = Color(0xFF00897B),
        buttonText = ResourceProvider(StringResourceEnum.TAP_TO_CONTINUE_TEXT),
        message = message,
        snackbarHostState = snackbarHostState
    )
}

@Composable
private fun Operation_Error_Snackbar(message: String, snackbarHostState: SnackbarHostState) {
    GenericQuestionSituationSnackbar(
        modifier = Modifier,
        backgroundColor = Color.Red,
        message = message,
        buttonText = ResourceProvider(StringResourceEnum.GIVE_IT_ANOTHER_GO_TEXT),
        snackbarHostState = snackbarHostState
    )
}

@Composable
private fun Question_Correct_Snackbar(message: String, snackbarHostState: SnackbarHostState) {
    GenericQuestionSituationSnackbar(
        modifier = Modifier,
        backgroundColor = Color(0xFF00897B),
        message = message,
        buttonText = ResourceProvider(StringResourceEnum.TAP_TO_CONTINUE_TEXT),
        snackbarHostState = snackbarHostState
    )
}
@Composable
private fun Question_Wrong_Snackbar(message: String, snackbarHostState: SnackbarHostState) {
    GenericQuestionSituationSnackbar(
        modifier = Modifier,
        backgroundColor = Color(0xFFEB385B),
        message = message,
        buttonText = ResourceProvider(StringResourceEnum.GIVE_IT_ANOTHER_GO_TEXT),
        snackbarHostState = snackbarHostState
    )
}

@Composable
private fun Question_Finished_Snackbar(message: String, snackbarHostState: SnackbarHostState) {
    GenericQuestionSituationSnackbar(
        modifier = Modifier,
        backgroundColor = Color(0xFF00897B),
        message = message,
        buttonText = ResourceProvider(StringResourceEnum.TAP_TO_GO_BACK_TEXT),
        snackbarHostState = snackbarHostState
    )
}


@Composable
private fun GenericQuestionSituationSnackbar(modifier: Modifier = Modifier, backgroundColor : Color,message: String, buttonText : String,snackbarHostState: SnackbarHostState){
    Snackbar(
        modifier = modifier
            .padding(12.dp)
            .fillMaxWidth(0.5f)
            .clip(RoundedCornerShape(20.dp)),
        containerColor = backgroundColor,
        action = {
        }
    ) {
        Box(contentAlignment = Alignment.TopCenter){
            Image(
                modifier = Modifier.fillMaxWidth(0.2f).aspectRatio(1f).align(Alignment.TopStart)
                    .graphicsLayer {
                        // This scales the X dimension to -1, flipping the image horizontally
                        scaleX = -1f
                    },
                painter = painterResource(R.drawable.ballon_assest),
                contentScale = ContentScale.Fit,
                contentDescription = "bg"
            )
            Image(
                modifier = Modifier.fillMaxWidth(0.2f).aspectRatio(1f).align(Alignment.TopEnd),
                painter = painterResource(R.drawable.ballon_assest),
                contentScale = ContentScale.Fit,
                contentDescription = "bg"
            )
            Column(modifier = Modifier.fillMaxWidth(1f),horizontalAlignment = Alignment.CenterHorizontally) {
                Row() {
                    SpecialText(message)
                    Icon(
                        imageVector = Icons.Filled.Star,
                        "volumeup",
                        tint = Color.Yellow
                    )
                }
                Button(
                    modifier = Modifier.wrapContentSize(Alignment.Center),
                    shape = RoundedCornerShape(15.dp),
                    enabled = true,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    onClick = {
                        snackbarHostState.currentSnackbarData?.dismiss()
                    }) {
                    SpecialText(buttonText)
                }
            }
        }

    }
}
@Composable
@Preview(device = "spec:parent=pixel_5,orientation=landscape")
fun PreviewSnackBarShowcase() {
    val snackbarHostState = remember { SnackbarHostState() }
    CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
        CompositionLocalProvider(userDiffChars provides listOf('b', 'p')) {
            DystherapyPREVIEWTheme(theme = Theme.CHILD_THEME) {
                SnackBarShowcase(
                    receivedSnackbarData = SnackbarData(
                        message = "ancillae",
                        snackBarTypeEnum = SnackBarTypeEnum.QUESTION_CORRECT
                    )
                )
            }
        }
    }

}