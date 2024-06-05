package com.arda.dystherapy.components

import android.view.MotionEvent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.arda.dystherapy.util.DebugTagsEnumUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val shadowSize = 5.dp
private val TAG = DebugTagsEnumUtils.UITag.tag

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SpecialExtendedFloatingActionButton(
    text: @Composable () -> Unit,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    expanded: Boolean = true,
    shape: Shape = FloatingActionButtonDefaults.extendedFabShape,
    containerColor: Color = FloatingActionButtonDefaults.containerColor,
    contentColor: Color = contentColorFor(containerColor),
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
){
    val shadowSize = 7.dp
    ConstraintLayout(
        modifier = modifier.padding(bottom = shadowSize)
    ) {
        val (back, btn) = createRefs()

        var animatedY by remember { mutableStateOf(0.dp) }

        Box(
            modifier = Modifier
                .constrainAs(back) {
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints

                    start.linkTo(btn.start)
                    end.linkTo(btn.end)
                    top.linkTo(btn.top, shadowSize)
                    bottom.linkTo(btn.bottom)

                    translationY = shadowSize / 2
                }
                .clip(CircleShape)
                .background(Color(0xFFA39F52))
        )

        val myAnim by animateDpAsState(
            targetValue = animatedY,
            animationSpec = tween(70),
            label = ""
        )

        var btnSize by remember { mutableStateOf(IntSize.Zero) }
        ExtendedFloatingActionButton(
            onClick = { },
            shape = shape,
            text = text,
            icon = icon,
            contentColor = contentColor,
            elevation = elevation,
            expanded = expanded,
            interactionSource = interactionSource,
            modifier = Modifier
                .constrainAs(btn) {
                    translationY = myAnim
                }
                .indication(
                    interactionSource = interactionSource,
                    indication = null
                )
                .onGloballyPositioned {
                    btnSize = it.size
                }
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            animatedY = shadowSize
                        }

                        MotionEvent.ACTION_UP -> {
                            animatedY = 0.dp
                            onClick.invoke()
                        }

                        MotionEvent.ACTION_MOVE -> {
                            animatedY = 0.dp
                        }
                    }
                    true
                }
        )
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SpecialButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(20.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    ConstraintLayout(
        modifier = modifier.padding(bottom = shadowSize)
    ) {
        val (back, btn) = createRefs()

        var animatedY by remember { mutableStateOf(0.dp) }

        Box(
            modifier = Modifier
                .constrainAs(back) {
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints

                    start.linkTo(btn.start)
                    end.linkTo(btn.end)
                    top.linkTo(btn.top, shadowSize)
                    bottom.linkTo(btn.bottom)

                    translationY = shadowSize / 2
                }
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFA39F52))
        )

        val myAnim by animateDpAsState(
            targetValue = animatedY,
            animationSpec = tween(70),
            label = ""
        )

        var btnSize by remember { mutableStateOf(IntSize.Zero) }
        val coroutineScope = rememberCoroutineScope()

        Button(
            onClick = { },
            enabled = enabled,
            shape = shape,
            elevation = elevation,
            border = border,
            contentPadding = contentPadding,
            interactionSource = interactionSource,
            modifier = Modifier
                .constrainAs(btn) {
                    translationY = myAnim
                }
                .indication(
                    interactionSource = interactionSource,
                    indication = null
                )
                .onGloballyPositioned {
                    btnSize = it.size
                }
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            animatedY = shadowSize
                        }

                        MotionEvent.ACTION_UP -> {
                            animatedY = 0.dp
                            onClick.invoke()
                        }

                        MotionEvent.ACTION_MOVE -> {
                            animatedY = 0.dp
                        }
                    }
                    true
                },
            colors = colors,
        ) {
            content()
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SpecialTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.textShape,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
){
    ConstraintLayout(
        modifier = modifier.padding(bottom = shadowSize)
    ) {
        val (back, btn) = createRefs()

        var animatedY by remember { mutableStateOf(0.dp) }

        Spacer(
            modifier = Modifier
                .constrainAs(back) {
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints

                    start.linkTo(btn.start)
                    end.linkTo(btn.end)
                    top.linkTo(btn.top, shadowSize)
                    bottom.linkTo(btn.bottom)

                    translationY = shadowSize / 2
                }
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFA39F52))
        )

        val myAnim by animateDpAsState(
            targetValue = animatedY,
            animationSpec = tween(200),
            label = ""
        )

        var btnSize by remember { mutableStateOf(IntSize.Zero) }

        val coroutineScope = rememberCoroutineScope()
        TextButton(
            onClick = {
//                animatedY = shadowSize
//                onClick.invoke()
//                animatedY = 0.dp
                coroutineScope.launch {
                    animatedY = shadowSize
                    onClick.invoke()
                    delay(200)  // Delay for 300 milliseconds
                    animatedY = 0.dp
                }
                      },
            enabled = enabled,
            shape = shape,
            elevation = elevation,
            border = border,
            contentPadding = contentPadding,
            interactionSource = interactionSource,
            modifier = Modifier
                .constrainAs(btn) {
                    translationY = myAnim
                }
                .indication(
                    interactionSource = interactionSource,
                    indication = null
                )
                .onGloballyPositioned {
                    btnSize = it.size
                },
            colors = colors,
        ) {
            content()
        }
    }
}
@Composable
fun SpecialIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    ConstraintLayout(
        modifier = modifier.padding(start = 15.dp, end = 15.dp,bottom = shadowSize)
    ) {
        val (back, btn) = createRefs()

        var animatedY by remember { mutableStateOf(0.dp) }

        Spacer(
            modifier = Modifier
//                .aspectRatio(1f)
                .constrainAs(back) {
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints

                    start.linkTo(btn.start)
                    end.linkTo(btn.end)
                    top.linkTo(btn.top, shadowSize)
                    bottom.linkTo(btn.bottom)

                    translationY = shadowSize / 2
                }
                .clip(RoundedCornerShape(100))
                .background(Color(0xFFA39F52))
        )

        val myAnim by animateDpAsState(
            targetValue = animatedY,
            animationSpec = tween(200),
            label = ""
        )

        var btnSize by remember { mutableStateOf(IntSize.Zero) }

        val coroutineScope = rememberCoroutineScope()
        IconButton(
            onClick = {
                coroutineScope.launch {
                    animatedY = shadowSize
                    onClick.invoke()
                    delay(200)  // Delay for 300 milliseconds
                    animatedY = 0.dp
                }
            },
            enabled = enabled,
            interactionSource = interactionSource,
            modifier = Modifier.padding(0.dp)
                .constrainAs(btn) {
                    translationY = myAnim
                }
                .indication(
                    interactionSource = interactionSource,
                    indication = null
                )
                .onGloballyPositioned {
                    btnSize = it.size
                },
            colors = colors,
        ) {
            icon()
        }
    }
}
@Composable
@Preview
fun SpecialButton() {

}

@Preview(showBackground = true)
@Composable
private fun PreviewDuolingoButton() {
    Box(modifier = Modifier.fillMaxSize(1f)) {
        SpecialButton(onClick = { /*TODO*/ }) {

        }
//        SpecialButton(
//            modifier = Modifier,
//            text = "Preview",
//            color =Color(0xFFD3D062),
//            enabled = false,
//            shape = RoundedCornerShape(15.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = MaterialTheme.colorScheme.secondary
//            ),
//            onClick = {},
//        ) {}
    }

}