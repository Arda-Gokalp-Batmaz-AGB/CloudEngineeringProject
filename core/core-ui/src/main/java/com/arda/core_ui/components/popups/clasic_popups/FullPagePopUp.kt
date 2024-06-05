package com.arda.dystherapy.components.popups.clasic_popups

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.arda.dystherapy.util.ResourceProvider
import com.arda.dystherapy.validation.StringResourceEnum

@Composable
fun FullPagePopUpWrapper(
    dialogState: Boolean,
    setShowDialog: (Boolean) -> Unit,
    content: @Composable () -> Unit,
) {
    if (dialogState)
        Dialog(onDismissRequest = {
            setShowDialog(false)
        }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                modifier = Modifier.fillMaxSize(1f)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .fillMaxHeight(1f)
                    )
                    {
                        TopBarWithCancel(setShowDialog)
                        content()


                    }
                }
            }
        }
}
@Composable
private fun TopBarWithCancel(setShowDialog: (Boolean) -> Unit){
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(1f),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = {  }) {
            Box(modifier = Modifier.fillMaxSize(1f)
                .background(color = Color.LightGray, shape = CircleShape), contentAlignment = Alignment.Center) {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Filled.Cancel,
                    contentDescription = "",
                    tint = Color.Blue
                )

            }
        }
        Spacer(Modifier.weight(1f))
        Text(
            modifier = Modifier,//.weight(1f)
            text = ResourceProvider(StringResourceEnum.SUPPORT), //"Account",//settingNavItem.title,
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(Modifier.weight(1f))
    }
}

@Preview
@Composable
fun previewTopBar(){
    TopBarWithCancel({})
}