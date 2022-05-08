package com.tinyellow.testcompose.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.tinyellow.testcompose.ui.theme.xqy_162D43
import com.tinyellow.testcompose.ui.theme.xqy_DFE5EE


@Preview
@Composable
fun DialogSignType(){
    ConstraintLayout(Modifier.fillMaxWidth().background(Color.White)){
        val g1 = createGuidelineFromStart(32.dp)
        val g2 = createGuidelineFromEnd(32.dp)

        val (d1,d2,d3,face_auth,message_auth,voice_auth,cancel) = createRefs()
        val context = LocalContext.current
        Button(onClick = { Toast.makeText(context ,"",Toast.LENGTH_SHORT).show()},
            enabled = true,
            contentPadding = PaddingValues(20.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Gray,
                contentColor = Color.Green,
                disabledBackgroundColor = Color.Black,
                disabledContentColor = Color.Red

            ),
            modifier = Modifier
//                .padding(10.dp)
            .constrainAs(face_auth) {
                start.linkTo(g1)
                top.linkTo(parent.top, 15.dp)
                bottom.linkTo(message_auth.top)
                end.linkTo(g2)
                width = Dimension.fillToConstraints
            }
        ){
            Text(text = "刷脸读数",
                fontSize = 16.sp,
                color = xqy_162D43,
                fontWeight = FontWeight.Black)
        }

        Divider(color = xqy_DFE5EE,
        modifier = Modifier
            .height(0.5.dp)
            .constrainAs(d1){
                start.linkTo(g1)
                end.linkTo(g2)
                top.linkTo(face_auth.bottom)
                width = Dimension.fillToConstraints
            })

        Text(text = "短信验证",
            fontSize = 16.sp,
            color = xqy_162D43,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(20.dp)
                .constrainAs(message_auth) {
                    start.linkTo(g1)
                    top.linkTo(face_auth.bottom)
                    end.linkTo(g2)
                    width = Dimension.fillToConstraints
                }
        )

        Divider(color = xqy_DFE5EE,
            modifier = Modifier
                .height(0.5.dp)
                .constrainAs(d2){
                    start.linkTo(g1)
                    end.linkTo(g2)
                    top.linkTo(message_auth.bottom)
                    width = Dimension.fillToConstraints
                })

        Text(text = "语音验证",
            fontSize = 16.sp,
            color = xqy_162D43,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(20.dp)
                .constrainAs(voice_auth) {
                    start.linkTo(g1)
                    top.linkTo(message_auth.bottom)
                    end.linkTo(g2)
                    width = Dimension.fillToConstraints
                }
        )

    }
}