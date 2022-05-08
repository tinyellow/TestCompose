package com.tinyellow.testcompose.ui.xqy

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tinyellow.testcompose.AActivity
import com.tinyellow.testcompose.R
import com.tinyellow.testcompose.ui.repeatClick
import com.tinyellow.testcompose.ui.theme.*
import com.tinyellow.testcompose.util.CodeUtils


@Preview
@Composable
fun Login(){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
    ) {
//        BottomNavigation()
//        leading
        val context = LocalContext.current
        val composeScope = this
        val tabs = remember{listOf("密码登录","验证码登录")}
        var index by remember{ mutableStateOf(0)}
        TabRow(selectedTabIndex = index,
            backgroundColor = Color.White,
            indicator = {
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .tabIndicatorOffset(it[index])
                        .padding(horizontal = 50.dp),
                    height = 2.dp,
                    color = xqy_757AB8
                )
            },
            divider = {},
            modifier = Modifier
                .fillMaxWidth()
        ) {
            tabs.forEachIndexed { i, s ->
                Tab(selected = i == index,
//                        modifier = Modifier.fillMaxHeight(),
                    onClick = {
                        if(index == i){
                            return@Tab
                        }
                        index = i
                        Toast
                            .makeText(context, s, Toast.LENGTH_SHORT)
                            .show()
                    }) {
                    Text(text = s,color = if(i == index)xqy_757AB8 else xqy_333333 ,fontSize = 14.sp,fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 15.dp))
                }

            }
        }

        
        
        Text("手机号",fontSize = 14.sp,color = xqy_666666,
        modifier = Modifier.padding(start = 20.dp,top = 40.dp))

        var mobile by remember { mutableStateOf("") }
        var password by remember{ mutableStateOf("")}
        var loginEnable by remember{ mutableStateOf(false)}

//        var mobileFous   by remember { mutableStateOf(false) }
        val mobileInteractionSource = remember{ MutableInteractionSource()}
        val mobileFocus by mobileInteractionSource.collectIsFocusedAsState()
        val mobilefr = remember{ FocusRequester() }
        val passwordfr = remember{ FocusRequester() }

        val diableGradient = remember{ Brush.horizontalGradient(listOf(xqy_7F7272E3,xqy_806893FF)) }

        Row(verticalAlignment = Alignment.CenterVertically){

            BasicTextField(value = mobile,
                onValueChange = {
                    mobile =  it.filterIndexed{i,s->s.isDigit()&&i<11}
                    loginEnable = !(mobile.isEmpty()||password.isEmpty())
                },
                textStyle = TextStyle(fontSize = 16.sp, color = xqy_333333),
                singleLine = true,
                decorationBox = {
                    if(mobile.isEmpty()){
                        Text("请输入手机号码",fontSize = 16.sp,color = xqy_ABABAB)
                    }
                    it()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,imeAction = ImeAction.Next),
                interactionSource = mobileInteractionSource,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .padding(vertical = 16.dp)
                    .weight(1f)
                    .focusRequester(mobilefr)
//                    .onFocusChanged { mobileFous = it.isFocused }
            )
            Image(painter = painterResource(id = R.mipmap.xqy_icon_input_clear)
                , contentDescription = null,
            modifier = Modifier
                .padding(end = 15.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = {
                        mobile = ""
                        loginEnable = false
                        mobilefr.requestFocus()
                    })
                .padding(5.dp))
        }

        Divider(color = if(mobileFocus) xqy_757AB8 else xqy_D3DFEF,modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(horizontal = 20.dp))

        Text("密码",fontSize = 14.sp,color = xqy_666666,
            modifier = Modifier.padding(start = 20.dp,top = 20.dp))


        var passwordFocus   by remember { mutableStateOf(false) }
        var passwordVisual by remember { mutableStateOf(false)}
        Row(verticalAlignment = Alignment.CenterVertically){
            BasicTextField(value = password,
            onValueChange = {
                password = it.filterIndexed{ i,s -> i<20}
                loginEnable = !(it.isEmpty()||mobile.isEmpty())
             },
            textStyle = TextStyle(fontSize = 16.sp, color = xqy_333333),
            singleLine = true,
            decorationBox = {
                if(password.isEmpty()){
                    Text("请输入登录密码",fontSize = 16.sp, color = xqy_CCCCCC)
                }
                it()
            },
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            visualTransformation =  if(!passwordVisual)  PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier
                .padding(start = 20.dp)
                .padding(vertical = 16.dp)
                .weight(1f)
                .focusRequester(passwordfr)
                .onFocusChanged { passwordFocus = it.isFocused })
            Image(painter = painterResource(id = R.mipmap.xqy_icon_input_clear), contentDescription = null,
                modifier = Modifier
                    .clickable {
                        password = ""
                        passwordfr.requestFocus()
                        loginEnable = false
                    }
                    .padding(5.dp))

            Image(painter = painterResource(id = if(!passwordVisual) R.mipmap.icon_pwd_visible else R.mipmap.icon_pwd_invisible)
                , contentDescription = null,
                modifier = Modifier
                    .padding(end = 15.dp)
                    .clickable { passwordVisual = !passwordVisual }
                    .padding(10.dp))

        }

        Divider(color = if(passwordFocus) xqy_757AB8 else xqy_D3DFEF,modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(horizontal = 20.dp))

        Text(text = "图形验证码",color = xqy_666666,fontSize = 14.sp,
        modifier = Modifier.padding(start = 20.dp,top = 20.dp))
        var graphCode by remember{ mutableStateOf("")}
        var graphFocus by remember{ mutableStateOf(false)}
        Row(verticalAlignment = Alignment.CenterVertically){
           BasicTextField(value = graphCode,
               onValueChange = {
                   graphCode = it.filterIndexed{i,s-> i<4}
               },
               textStyle = TextStyle(color = xqy_333333,fontSize = 16.sp),
               decorationBox = {
                   if(graphCode.isEmpty()){
                       Text("请输入验证码",fontSize = 16.sp,color = xqy_ABABAB)
                   }
                   it()
               },
               modifier = Modifier
                   .weight(1f)
                   .padding(start = 20.dp)
                   .padding(vertical = 16.dp)
                   .onFocusChanged { graphFocus = it.isFocused }
           )

            var graph by remember{ mutableStateOf(CodeUtils.getInstance().createBitmap())}
            Image(bitmap = graph.asImageBitmap(),contentDescription = null,
            modifier = Modifier
                .padding(15.dp)
                .clip(RoundedCornerShape(4.dp))
                .border(0.4.dp, xqy_D3DFEF, RoundedCornerShape(4.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = {
                        graph.recycle()
                        graph = CodeUtils
                            .getInstance()
                            .createBitmap()
                    })


                .width(96.dp)
                .height(37.dp))
        }
        Divider(color = if(graphFocus) xqy_757AB8 else xqy_D3DFEF,modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(horizontal = 20.dp))

        Text(text = "忘记密码",fontSize = 14.sp,color = xqy_666666,
            modifier = Modifier
                .clickable(onClick = repeatClick {
                    AActivity.start(context)
                    Toast
                        .makeText(context, "忘记密码", Toast.LENGTH_SHORT)
                        .show()
                })
                .padding(20.dp)
                .align(Alignment.End))
        val gradient = remember{ Brush.horizontalGradient(listOf(xqy_727BE3,xqy_689CFF)) }


        val interactionSource2 = remember{ MutableInteractionSource()}
        val pressedState2 by interactionSource2.collectIsPressedAsState()
        Text(text = "登录",fontSize = 16.sp,color = Color.White,textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .background(
                    brush = if (pressedState2) gradient else if (loginEnable) gradient else diableGradient,
                    shape = RoundedCornerShape(23.dp)
                )
                .clip(shape = RoundedCornerShape(23.dp))
                .clickable(
                    enabled = loginEnable,
                    interactionSource = interactionSource2,
                    indication = rememberRipple(),
                    onClick = repeatClick {
                        mobilefr.freeFocus()
                        passwordfr.freeFocus()
                        Toast
                            .makeText(
                                context,
                                "登录\n账号：${mobile}\n密码：${password}${CodeUtils.getInstance().code}",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                )
                .padding(10.dp)

        )
//        val interaction = remember{ MutableInteractionSource()}
//        val pressState by interaction.collectIsPressedAsState()
//        Button(enabled = loginEnable,
//            onClick = {
//                       Toast .makeText(context,"登录",Toast.LENGTH_SHORT).show()
//
//            },
//            contentPadding = PaddingValues(10.dp),
////            colors = ButtonDefaults.buttonColors(backgroundColor = if(pressState)Color.Blue else Color.Red,disabledBackgroundColor = Color.Gray,contentColor = Color.White,disabledContentColor = Color.DarkGray),
//            shape = RoundedCornerShape(23.dp),
//            elevation = null,
//            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent,disabledBackgroundColor = Color.Transparent,contentColor = Color.White,disabledContentColor = Color.White),
//            interactionSource = interaction,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(20.dp)
//                .shadow()
//                .background(
//                    if (pressState) gradient else if (loginEnable) gradient else diableGradient,
//                    RoundedCornerShape(23.dp)
//                )
//                .padding(10.dp)
//        ){
//            Text(text = "登录",fontSize = 16.sp)
//        }




        Spacer(Modifier.weight(1f))

        val registration = "《用户协议》"
        val privacy = "《隐私政策》"
        val text = buildAnnotatedString {
            withStyle(SpanStyle(color = xqy_666666,fontSize = 12.sp)){
                append("登录即代表您已经同意")
                withStyle(SpanStyle(color = xqy_757AB8)){
                    pushStringAnnotation("tag1","")
                    append(registration)
                    pop()
                    pushStringAnnotation("tag2","")
                    append(privacy)
                    pop()
                }
            }

        }

        ClickableText(text = text,onClick = {
                          text.getStringAnnotations("tag1",it,it)
                              .firstOrNull()?.let{
                                    Toast.makeText(context,registration,Toast.LENGTH_SHORT).show()
                                }

                          val testt = text.getStringAnnotations("tag2",it,it)
                        val testt2 = testt.firstOrNull()
                                testt2?.let{
                                  Toast.makeText(context,privacy,Toast.LENGTH_SHORT).show()
                              }
        },
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(bottom = 40.dp))
    }
}