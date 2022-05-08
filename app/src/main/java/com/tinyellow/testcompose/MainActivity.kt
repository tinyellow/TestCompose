package com.tinyellow.testcompose

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.tinyellow.testcompose.ui.DialogSignType
import com.tinyellow.testcompose.ui.xqy.Login
import com.tinyellow.testcompose.ui.xqy.user.LocalUserModel
import com.tinyellow.testcompose.ui.xqy.user.UserInfo
import com.tinyellow.testcompose.ui.xqy.user.UserViewModel
import com.tinyellow.testcompose.ui.xqy.user.isLogin

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<ComposeView>(R.id.compose_view).setContent {
            Login()
//            CompositionLocalProvider(LocalUserModel provides UserViewModel(LocalContext.current)){
//            val userViewModel = LocalUserModel.current
//
//            Column(Modifier.fillMaxSize()) {
////                val isLogin by userViewModel.isLogin()
//                val userInfo by userViewModel.userInfo()
//
//                Text(text = "${if(userInfo.isLogin()) userInfo?.name else "未登录"}")
//                TextButton(onClick = {
//                    userViewModel.savaUserInfo(UserInfo("小李"))
//                }) {
//                    Text(text = "先模拟登录")
//                }
//                BoxWithConstraints() {
////                    with(LocalDensity.current){ X.toPx()}
//                  Text(text = "屏幕宽：${constraints.maxWidth},高：${constraints.maxHeight}")
//                }
//            }
//            }
        }
//        setContent{
////            Test("aaaa")
//            DefaultPrevieww()
//        }
    }

}

@Composable
fun test(){
    val openDialog =  remember{ mutableStateOf(false) }
    addDialog(openDialog)
    Column(modifier = Modifier.padding(10.dp)) {
        Text("aaaa")
        Text("bbbb",modifier = Modifier
            .padding(top = 10.dp)
            .padding(top = 20.dp)
            .background(Color.Gray))
        AndroidView(factory = {
            Button(it).apply {
                setOnClickListener {
                    openDialog.value = true
//                            Toast.makeText(this@MainActivity,"",Toast.LENGTH_SHORT).show()
                }
            }
        },
            update = {
                it.text = "cccc"
            }
        )
        DialogSignType()
    }
}

@Composable
private fun addDialog(openDialog: MutableState<Boolean>) {
    if(openDialog.value){
        AlertDialog(
            onDismissRequest = {openDialog.value = false },
            title = {Text("这是标题")},
            text = {Text("这是内容`F`%TUnB")},
            confirmButton = {
                TextButton(onClick = { openDialog.value = false }) {
                Text(text = "确认")
            }},
            dismissButton = {
                TextButton(onClick = { openDialog.value = false }) {
                   Text(text = "取消")
                }
                
            },
//            modifier = Modifier.s

            )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
private fun DialogPreview(){
    val open = remember{ mutableStateOf(true) }
    addDialog(open)
}

@Composable
private fun Test(name: AnnotatedString, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!",
        modifier = modifier
            .padding(20.dp))
}

@Preview(showBackground = true)
@Composable
fun DefaultPrevieww() {
    Row(verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
        .height(IntrinsicSize.Max)
        .clickable { }
        .fillMaxWidth()
//        .border(5.dp,Color.Blue, CutCornerShape(30.dp))
    ) {
        val infiniteTransition = rememberInfiniteTransition()
        val alpha = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 1000
                    1f to 500
                },
                repeatMode = RepeatMode.Reverse
            )
        )

        Image(painter = painterResource(id = R.mipmap.icon_user_def_photo),
            contentDescription = null,
        modifier = Modifier
            .size(150.dp)
//            .alpha(alpha.value)
            .background(Color.Blue.copy(alpha = alpha.value))
//            .padding(4.dp)
//            .shadow(1.dp, shape = RoundedCornerShape(topStart = 30.dp))
            .clip(shape = CutCornerShape(topStart = 8.dp))
        )
//        Spacer(modifier = Modifier.weight(1f))
        Divider(color = Color.Black,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp))
        Column(horizontalAlignment = Alignment.End) {

            val tag = buildAnnotatedString {
                append("this is a ")
                withStyle(SpanStyle(color = Color.Red,background = Color.Green)){
                    append("Red Text:aaaa")
                }
            }

            Text(text = tag,Modifier.background(Color.Blue))
            Text("bbbb")
        }
    }



}