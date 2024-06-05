package com.arda.dystherapy.components.popups.clasic_popups

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.arda.dystherapy.components.SpecialText

@Composable
fun DefaultPopUp(
    popUpType: ClassicPopUpTypesEnum,
    dialogState: Boolean,
    setShowDialog: (Boolean) -> Unit,
    title: String,
    text: String,
    confirmationCode: () -> Unit = {}
) {
    if (dialogState)
        Dialog(onDismissRequest = {
            setShowDialog(false)
        }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    InsideDialog(popUpType, setShowDialog, title, text, confirmationCode)
                }
            }
        }
}

@Composable
private fun TopBarPopUp(
    title: String,
    setShowDialog: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        com.arda.dystherapy.components.SpecialText(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = title,
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center
        )
        IconButton(modifier = Modifier, onClick = { setShowDialog(false) }) {
            Icon(
                imageVector = Icons.Filled.Cancel,
                "cancel",
                tint = Color.Red
            )
        }
    }

}

@Composable
private fun InsideDialog(
    popUpType: ClassicPopUpTypesEnum,
    setShowDialog: (Boolean) -> Unit,
    title: String,
    text: String,
    confirmationCode: () -> Unit
) {
    Column(
    ) {
        TopBarPopUp(title, setShowDialog)
        BodyPopUp(text)
        ButtonPart(popUpType, setShowDialog, confirmationCode)
    }

}

@Composable
private fun BodyPopUp(
    text: String,
) {
    Card()
    {
        com.arda.dystherapy.components.SpecialText(text = text)
    }
}


@Composable
private fun ButtonPart(
    popUpType: ClassicPopUpTypesEnum,
    setShowDialog: (Boolean) -> Unit,
    confirmationCode: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (popUpType) {
            ClassicPopUpTypesEnum.DECISION_POP_UP -> {
                Button(onClick = {
                    setShowDialog(false)
                    confirmationCode.invoke()
                }) {
                    com.arda.dystherapy.components.SpecialText(text = "Yes")
                }
                Button(onClick = { setShowDialog(false) }) {
                    com.arda.dystherapy.components.SpecialText(text = "No")
                }
            }

            ClassicPopUpTypesEnum.CONFIRMATION_POP_UP -> {
                Button(onClick = {
                    setShowDialog(false)
                    confirmationCode.invoke()
                }) {
                    com.arda.dystherapy.components.SpecialText(text = "OK")
                }
            }
        }

    }

}