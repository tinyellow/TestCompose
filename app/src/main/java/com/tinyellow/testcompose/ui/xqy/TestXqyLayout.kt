package com.tinyellow.testcompose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.tinyellow.testcompose.R
import com.tinyellow.testcompose.ui.theme.xqy_1C2D41
import com.tinyellow.testcompose.ui.theme.xqy_415268
import com.tinyellow.testcompose.ui.theme.xqy_90A4BE
import com.tinyellow.testcompose.ui.theme.xqy_E9EAEE


@Preview
@Composable
fun XqyMainListItem(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
    ){
        Column {
            Row(){
               Column(
                   Modifier
                       .weight(1f)
                       .padding(top = 18.dp)
               ) {
                   Text(text = "zxcvbnmasdfghjklqwertyuiop1234567890",
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(start = 17.dp),
                       color = xqy_1C2D41,
                       fontSize = 16.sp,
                       fontWeight = FontWeight.Bold,
                       maxLines = 1,
                       overflow = TextOverflow.Ellipsis
                   )

                   DrawableLeft(Modifier.padding(start = 17.dp,top = 4.dp)){
                       Image(painterResource(id = R.mipmap.xqy_icon_main_list_send),null,Modifier.padding(end = 3.dp))
                       Text(text = "zxcvbnmasdfghjklqwertyuiop1234567890",
                           color = xqy_415268,
                           fontSize = 12.sp)
                   }

                   DrawableLeft(Modifier.padding(start = 17.dp,top = 6.dp)){
                       Image(painterResource(id = R.mipmap.xqy_icon_main_list_receive),null,Modifier.padding(end = 3.dp))
                       Text(text = "XXXXXXXXXXXXX",
                           color = xqy_415268,
                           fontSize = 12.sp)
                   }
               }
               Image(painter = painterResource(id = R.mipmap.xqy_icon_approval_spz),
                   contentDescription = null,
               modifier = Modifier.offset(x = 6.dp,y = (-14).dp))
            }
            Divider(color = xqy_E9EAEE
                ,modifier = Modifier
                    .padding(top = 17.dp)
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .padding(start = 17.dp, end = 16.dp))
            
            Row{
                Text(text = "发起人:张三",color = xqy_415268,fontSize = 12.sp,
                    modifier = Modifier
                        .padding(start = 17.dp,top = 11.dp,bottom = 11.dp)
                        )
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "发起时间:2022-04-04",color = xqy_90A4BE,fontSize = 12.sp,
                    modifier = Modifier
                        .padding(start = 17.dp,top = 11.dp,end = 16.dp ,bottom = 11.dp)
                )
            }

            Box(Modifier.width(100.dp).aspectRatio(2f).background(Color.Green))
                    
        }
    }
}

@Preview
@Composable
fun TestConstrain(){
//    constrain

    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 15.dp)
        .clipToBounds()
        .background(Color.White, RoundedCornerShape(12.dp))
    ) {
        val (text,send,receive,image,divider,expire,sign_date) = createRefs()

        Image(painter = painterResource(id = R.mipmap.xqy_icon_approval_spz), contentDescription = null,
              modifier = Modifier
                  .constrainAs(image) {
                  top.linkTo(parent.top)
                  end.linkTo(parent.end)
              }
                  .offset(x = 6.dp,y = (-14).dp)
            )

        Text(

            text = "zxcvbnmasdfghjklqwertyuiop1234567890zxcvbnm",
            fontSize = 16.sp,
            color = xqy_1C2D41,
            fontWeight = FontWeight.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(text) {
                    start.linkTo(parent.start,margin = 17.dp)
                    top.linkTo(parent.top,18.dp)
                    end.linkTo(image.start,10.dp)
                    linkTo(startMargin = 17.dp,start = parent.start,end = image.start,endMargin = 10.dp,bias = 0f)
                    width = Dimension.preferredWrapContent
                }

//                .padding(top = 18.dp, start = 17.dp)
//                .fillMaxWidth()
            )

        DrawableLeft(
            Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
                .constrainAs(send) {
                    start.linkTo(parent.start,17.dp)
                    top.linkTo(text.bottom)
                    end.linkTo(image.start)
                    width = Dimension.fillToConstraints
                }
        ){
            Image(painterResource(id = R.mipmap.xqy_icon_main_list_send),null,Modifier.padding(end = 3.dp))
            Text(text = "zxcvbnmasdfghjklqwertyuiop12345678901111111111111",
                color = xqy_415268,
                fontSize = 12.sp)
        }

        DrawableLeft(
            Modifier
//                .aspectRatio(1f)
                .constrainAs(receive) {
                    top.linkTo(send.bottom,6.dp)
//                    linkTo(start = parent.start,end = image.start, bias = 0.25f)
                    start.linkTo(parent.start,17.dp)
                    end.linkTo(image.start)
                    width = Dimension.preferredWrapContent
//                    height = Dimension.percent(1f)
                }

        ){
            Image(painterResource(id = R.mipmap.xqy_icon_main_list_receive),null,Modifier.padding(end = 3.dp))
            Text(text = "XXXXXXXXXXXXX",
                color = xqy_415268,
                fontSize = 12.sp)
        }

        Divider(color = xqy_E9EAEE,
        modifier = Modifier
            .height(0.5.dp)
            .constrainAs(divider){
                 start.linkTo(parent.start,17.dp)
                 top.linkTo(receive.bottom,17.dp)
                 end.linkTo(parent.end)
                 width = Dimension.fillToConstraints
            })

        Text(text = "发起人:张三",
            color = xqy_415268,
            fontSize = 12.sp,
            modifier = Modifier
                .constrainAs(expire){
                    start.linkTo(parent.start,17.dp)
                    bottom.linkTo(parent.bottom,11.dp)
                    top.linkTo(receive.bottom,25.dp)
                    width = Dimension.preferredWrapContent
                }
        )

        Text(text = "发起时间:2022-04-04",
            color = xqy_90A4BE,fontSize = 12.sp,
            textAlign = TextAlign.End,
            modifier = Modifier
                .constrainAs(sign_date){
//                    top.linkTo(expire.top)
//                    bottom.linkTo(expire.bottom)
//                    end.linkTo(parent.end,16.dp)
//                    start.linkTo(expire.end,20.dp)
                    width = Dimension.preferredWrapContent
                    baseline.linkTo(expire.baseline)
                    linkTo(start = expire.end,startMargin = 5.dp,end = parent.end,endMargin = 16.dp,bias = 1f)
                }
        )


//        val (button1, button2, button3) = createRefs()
//
//        val horizontalChainReference = createHorizontalChain(button1, button2, button3, chainStyle = ChainStyle.Packed)
//        horizontalChainReference.start
//        Button(onClick = { }, modifier = Modifier
//            .constrainAs(button1) {
////                horizontalChainWeight = 0f
//            }
//        ){
//            Text(text = "Button 1")
//        }
//
//        Button(onClick = { }, modifier = Modifier.constrainAs(button2) {
//        }) {
//            Text(text = "Button 2")
//        }
//
//        Button(onClick = { }, modifier = Modifier.constrainAs(button3) {
//        }) {
//            Text(text = "Button 3")
//        }
    }
}