package com.tinyellow.testcompose.ui

import android.util.Log
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


@Composable
fun DrawableLeft(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    Row(verticalAlignment = Alignment.CenterVertically,
    modifier = modifier) {
        content()
    }
}


//fun <T> repeatclick(m: MutableState<T>): @Composable (Int) -> Unit {
//
//    return {
//        repeatClick {}
//    }
//}

//@Composable
//fun ButtonDefaults.noElevation() = elevation(0.dp,0.dp,0.dp,0.dp,0.dp)

/**
 * 防止重复点击,比如用在Button时直接传入onClick函数
 */
@Composable
fun repeatClick(time: Long = 1000, onClick: () -> Unit): () -> Unit {
    var clicked by remember { mutableStateOf(false) }
    if(clicked){
        LaunchedEffect(Unit) {
            delay(time)
            Log.e("onClick", "delay(1000)")
            clicked = !clicked
        }
    }

    return {
        if (!clicked) {
            clicked = !clicked
            onClick()
        }
    }
}

@Composable
 fun repeatClick2(
    time: Long = 1000,
     onClick: () -> Unit
): () -> Unit {
    var clicked = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = clicked, block = {
        if (clicked.value) {
            delay(time)
            clicked.value = !clicked.value
        }
    })
    return {
        clicked.value = !clicked.value
        onClick()

    }
//    var lastClickTime = remember { 0L }//使用remember函数记录上次点击的时间
//    return {
//        val currentTimeMillis = System.currentTimeMillis()
//        if (currentTimeMillis - lastClickTime > time) {//判断点击间隔,如果在间隔内则不回调
//            onClick()
//            lastClickTime = currentTimeMillis
//        }
//    }
}

//Modifier 的扩展方法处理如下
@Stable
fun Modifier.repeatClickable(
    delay: Long = 800,
    interactionSource: MutableInteractionSource,
    indication: Indication?,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit) =
    composed {
        var clicked by remember { mutableStateOf(!enabled) }
        LaunchedEffect(key1 = clicked, block = {
            if (clicked) {
                delay(delay)
                clicked = !clicked
            }
        })

        Modifier.clickable(
            interactionSource = interactionSource,
            indication = indication,
            enabled = if (enabled) !clicked else false,
            onClickLabel = onClickLabel,
            role = role) {
            clicked = !clicked
            onClick()
        }
    }

@Composable
fun handRepeatClick(enabled: Boolean,delay: Long):MutableState<Boolean>{
    var clicked = remember { mutableStateOf(!enabled) }
    LaunchedEffect(key1 = clicked, block = {
        if (clicked.value) {
            delay(delay)
            clicked.value = !clicked.value
        }
    })
    return clicked
}
