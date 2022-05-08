package com.tinyellow.testcompose.ui.xqy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.ihsg.patternlocker.OnPatternChangeListener
import com.github.ihsg.patternlocker.PatternIndicatorView
import com.github.ihsg.patternlocker.PatternLockerView
import com.tinyellow.testcompose.ui.theme.*
import com.tinyellow.testcompose.util.*
import com.tinyellow.testcompose.util.LockerLinkedLineView.dp2px


class GestureOptionsActivity:AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            GestureOptionsLayout()
        }
    }
}

@Preview
@Composable
fun GestureOptionsLayout() {
//    val resources = LocalContext.current.resources
//    dimensionResource()
    var hint = remember { mutableStateOf("请绘制手势密码图案，至少连接5个点") }
    var indicatorState by remember { mutableStateOf(Pair(false,listOf<Int>()))}
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        AndroidView(
            factory = {
                Log.e("update","factory---->")
            PatternIndicatorView(it).apply{
                val normalColor = (0xFFD8D8D8).toInt()
                val centerColor = (0xFF727BE3).toInt()
                linkedLineView = CustomIndicatorLinkedLineView()
                hitCellView = CustomIndicatorHitCellView(centerColor,normalColor)
            }
        },
            update = {
                if(!indicatorState.first){
                    it.updateState(indicatorState.second,false)
                }
                Log.e("update","update---->")
//                if("请绘制手势密码图案，至少连接5个点" == hint.value) xqy_333333 else xqy_F15856,
            }
            ,
        modifier = Modifier
            .padding(top = 50.dp)
            .size(size = 57.dp)
        )

        Text(
            text = hint.value,//"请绘制手势密码图案，至少连接5个点",
            color = if("请绘制手势密码图案，至少连接5个点" == hint.value||
                    "请再绘制手势密码图案"==hint.value) xqy_333333 else xqy_F15856,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 63.dp)
        )

        AndroidView(
            factory = {
            PatternLockerView(it).apply {
                enableAutoClean = true
//                lineWidth = 3.dp
                enableSkip = true

                val color = (0xFFD1D3EA).toInt()//xqy_D1D3EA.value.toInt()
                val centerColor =( 0xFF727BE3).toInt()//xqy_727BE3.value.toInt()
                val errorColor = (0xFFF15856).toInt()//xqy_F15856.value.toInt()
                val normalColor = (0xFFD8D8D8).toInt()//xqy_D8D8D8.value.toInt()
                val lineWidth = dp2px(2f)

                normalCellView = NormalCellView().setHitColor(normalColor)
                linkedLineView = LockerLinkedLineView().setShowTrack(true).setNormalColor(centerColor).setErrorColor(errorColor).setLineWidth(lineWidth.toFloat())
                hitCellView = HitCellView().setCenterColor(centerColor).setHitColor(color).setErrorColor(errorColor)

                setOnPatternChangedListener(object : OnPatternChangeListener {
                    var firstPwd = ""
                    val maxErrCount = 4
                    var errCount = 0
                    override fun onStart(patternLockerView: PatternLockerView) {}
                    override fun onChange(patternLockerView: PatternLockerView, list: List<Int>) {}
                    override fun onComplete(patternLockerView: PatternLockerView, list: List<Int>) {
                        val curPwd = convert2String(list)
                        if (curPwd.length >= 5 && TextUtils.isEmpty(firstPwd)) {
                            firstPwd = curPwd
//                            patternHelper.validateForSetting(list)
                            hint.value = "请再绘制手势密码图案" //patternHelper.getMessage());
                            patternLockerView.updateStatus(false)
//                            pattern_indicator_view.updateState(list, false)
                            indicatorState = false to list
                            errCount = 0
                        } else if (firstPwd == curPwd) {
                            patternLockerView.updateStatus(false)
                            val str = convert2String(list)
                            hint.value = "绘制手势密码图案成功"
                        } else {
                            if(!TextUtils.isEmpty(firstPwd)){
                                hint.value = "请保持与上一步手势解锁图案一致"
                            }
                            patternLockerView.updateStatus(true)
                            errCount++
                        }
                        //当用户首次设置手势密码或修改手势密码，手势轨迹输入错误最大次数为4次，如果达到最大次数，则提示:手势密码已失效，请重新登录，系统会强制用户退出。
                        if (errCount > maxErrCount) {
                            hint.value = "手势密码已失效，请重新登录。"
                            patternLockerView.updateStatus(true)
//                            XqyApplication.getInstance().onTokenFailure("手势密码已失效，请重新登录。")
                        }
                    }

                    override fun onClear(patternLockerView: PatternLockerView) {
                    }
                })
            }
        },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, start = 48.dp, end = 48.dp)
                .aspectRatio(1f)
        )
    }

}

fun convert2String(hitIndexList: List<Int>): String {
    val sb = StringBuilder()
    for (va in hitIndexList) {
        sb.append(va)
    }
    return sb.toString()
}

