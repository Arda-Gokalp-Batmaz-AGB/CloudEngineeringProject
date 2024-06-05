package com.arda.dystherapy.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import com.arda.dystherapy.util.ResourceProvider
import com.arda.dystherapy.validation.StringResourceEnum

enum class InteractionItemEnum(val value : String){
    SPEAK("Speak"),
    LISTEN("Listen"),
    CAMERA("Camera"),
    RESTART("Restart"),
    NON_PERMISSION("NonPermission"),
    STOP_OR_CONTINUE_TEXT("StopOrContinueText")
}
sealed class InteractionItem(
    val title: InteractionItemEnum,
    val icon: ImageVector,
    val active: MutableState<Boolean> = mutableStateOf(false)
) {
    open val desc: String
        get() {
            return ""
        }

    object Speak : InteractionItem(
        title = InteractionItemEnum.SPEAK,
        icon = Icons.Filled.Mic,
    ) {
        override val desc: String
            get() {
                return ResourceProvider(StringResourceEnum.SPEAK)
            }
    }

    object Listen : InteractionItem(
        title = InteractionItemEnum.LISTEN,
        icon = Icons.Filled.Headphones
    ) {
        override val desc: String
            get() {
                return ResourceProvider(StringResourceEnum.LISTEN)
            }
    }
    object StopOrContinueText : InteractionItem( //aktifken yaz deaktif oldugunda dur
        title = InteractionItemEnum.STOP_OR_CONTINUE_TEXT,
        icon = Icons.Filled.PlayArrow,
        //active = mutableStateOf(true)
    ) {
        override val desc: String
            get() {
                return ResourceProvider(StringResourceEnum.REPLAY_TEXT)
            }
    }
    object RestartGame : InteractionItem(
        title = InteractionItemEnum.RESTART,
        icon = Icons.Filled.Replay
    ) {
        override val desc: String
            get() {
                return ResourceProvider(StringResourceEnum.REPLAY_TEXT)
            }
    }
    object ItemController {
        private val itemList = listOf<InteractionItem>(
            Speak, Listen,
            StopOrContinueText,
            RestartGame,)
        fun switchButton(item: InteractionItem, customBool : Boolean? = null) {
            for (i in itemList.indices) {
                if (item.title == itemList[i].title) {
                    if(customBool == null)
                        itemList[i].active.value = !item.active.value
                    else
                        itemList[i].active.value = customBool
                } else {
                    itemList[i].active.value = false
                }
            }
        }

        fun deActivateAll() {
            for (i in itemList.indices) {
                itemList[i].active.value = false
            }
        }
        fun deActivateAllExceptContinue() {
            for (i in itemList.indices) {
                if(itemList[i].title != InteractionItemEnum.SPEAK && itemList[i].title != InteractionItemEnum.STOP_OR_CONTINUE_TEXT)//itemList[i].title != InteractionItemEnum.SPEAK ||
                    itemList[i].active.value = false
            }
        }
        fun getSpeakerItem(): InteractionItem {
            return itemList[0]
        }

        fun getListenItem(): InteractionItem {
            return itemList[1]
        }
        fun getStopOrContinueItem() : InteractionItem {
            return itemList[2]
        }
        fun getRestartGame(): InteractionItem {
            return itemList[3]
        }

    }
}