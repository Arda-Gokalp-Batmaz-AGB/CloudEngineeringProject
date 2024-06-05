package com.arda.core_ui.components.snackbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arda.core_ui.components.providers.LocalSnackbarHostState
import com.arda.core_ui.theme.ProjectTheme

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
        }
    }

}

@Composable
private fun Operation_Sucess_Snackbar(message: String, snackbarHostState: SnackbarHostState) {
    GenericQuestionSituationSnackbar(
        modifier = Modifier,
        backgroundColor = Color(0xFF00897B),
        buttonText = "Operation Sucess",
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
        buttonText = "An error occured",
        snackbarHostState = snackbarHostState
    )
}

@Composable
private fun GenericQuestionSituationSnackbar(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    message: String,
    buttonText: String,
    snackbarHostState: SnackbarHostState,
) {
    Snackbar(
        modifier = modifier
            .padding(12.dp)
            .fillMaxWidth(0.5f)
            .clip(RoundedCornerShape(20.dp)),
        containerColor = backgroundColor,
        action = {
        }
    ) {
        Box(contentAlignment = Alignment.TopCenter) {
            Column(
                modifier = Modifier.fillMaxWidth(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row() {
                    Text(message)
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
                    Text(buttonText)
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
        ProjectTheme() {
            SnackBarShowcase(
                receivedSnackbarData = SnackbarData(
                    message = "ancillae",
                    snackBarTypeEnum = SnackBarTypeEnum.OPERATION_SUCESS
                )
            )
        }
    }

}