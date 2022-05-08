package com.tinyellow.testcompose.ui.xqy.contract

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.DialogProperties
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.tinyellow.testcompose.R
import com.tinyellow.testcompose.ui.theme.xqy_162D43
import com.tinyellow.testcompose.ui.theme.xqy_689CFF
import com.tinyellow.testcompose.ui.theme.xqy_90A4BE
import com.tinyellow.testcompose.ui.theme.xqy_F6F6F8
import com.tinyellow.testcompose.ui.xqy.message.MessageActivity
import kotlinx.coroutines.launch

class ContractListActivity :AppCompatActivity(){

    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, ContractListActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val frameLayout = FrameLayout(this).apply { id = View.generateViewId() }
        setContent {
            Layout(frameLayout)
            LaunchedEffect(frameLayout){
                supportFragmentManager.commitNow(true){
                    replace(frameLayout.id,ContractFragment.newInstance())
                }
            }
        }
    }


}

fun preView(){

}

//@Preview
@OptIn(ExperimentalMaterialApi::class,
)
@Composable
fun Layout(frameLayout: FrameLayout){
    val context = LocalContext.current
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val sheetContent  = remember{ mutableStateOf<(@Composable BoxScope.() -> Unit)?>(null)} //  rememberUpdatedState<(@Composable ColumnScope.() -> Unit)?>(null)
    ModalBottomSheetLayout(
        sheetContent = {
            Box(Modifier.defaultMinSize(minHeight = 1.dp)){
                sheetContent.value?.let { it() }
            }
            },
        sheetState = state,
        sheetBackgroundColor = Color.Transparent,
        sheetElevation = 0.dp
//        topBar = {
//
//
//        }

    ) {
        Column {
            Box(Modifier.fillMaxWidth()) {
                Image(painter = painterResource(id = R.mipmap.xqy_icon_previous), contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 6.dp)
                        .clickable {
                            (context as? Activity)?.finish()
                        }
                        .padding(10.dp))
                Text(text = "合同管理",color = xqy_162D43,fontSize = 18.sp,fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.Center))
            }
            Row(verticalAlignment = Alignment.CenterVertically){

                var openDialog = remember{mutableStateOf(false)}
                showDialog(context,openDialog)
                TextButton(onClick = {
                        openDialog.value = !openDialog.value
//                        if(context is FragmentActivity){
//                            CustomDialog(
//                                windowSize = {w ,h ->
//                                    width = (w*0.5f).toInt()
//                                },
//                                window = {
//                                },
//                                gravity = Gravity.BOTTOM,
//                                content = {
//                                Box(contentAlignment = Alignment.Center){
//                                    Text(text = "fragment",modifier = Modifier
//                                        .fillMaxWidth()
//                                        .background(Color.White),
//                                        textAlign = TextAlign.Center)
//                                }
//                            }).show(context.supportFragmentManager,null)
//                        }

                    },
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(16.dp,13.dp)
                ){
                    Text(text = "全部合同·0",modifier = Modifier.padding(end = 4.dp),fontSize = 14.sp,fontWeight = FontWeight.Bold)
                    Image(painter = painterResource(id = R.mipmap.xqy_icon_arrow_down),contentDescription = null)
                }


                Spacer(modifier = Modifier.weight(1f))


                TextButton(
                    onClick = {
                        sheetContent.value = {
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(18.dp)
                                    .background(Color.White)){
                                Text("Text1")
                                Text("Text2")
                            }
                        }
                        scope.launch {
                            state.apply {
                                if (isVisible) hide() else show()
                            }
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(8.dp,13.dp)
                ) {
                    Image(painter = painterResource(id = R.mipmap.xqy_icon_search), contentDescription = null)
                    Text(text = "搜索",fontSize = 14.sp,color = xqy_90A4BE,modifier = Modifier.padding(start = 4.dp))
                }

                TextButton(
//                    elevation = ButtonDefaults.noElevation(),
                    shape = RoundedCornerShape(10.dp),
//                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    onClick = {
//                        sheetContent.value = {
//                            Button(onClick = {
//                                scope.launch {
//                                    state.apply { hide() }
//                                }
//                            }) {
//                                Text("Button")
//                            }
//                        }
//                        scope.launch {
//                            state.apply {
//                                if (isVisible) hide() else show()
//                            }
//                        }
                        MessageActivity.start(context,"7dc67300-0439-4400-8c30-6ab4f0246790")
                    },
                    contentPadding = PaddingValues(8.dp,13.dp)
                ) {
                    Image(painter = painterResource(id = R.mipmap.xqy_icon_filter_normal), contentDescription = null)
                    Text(text = "筛选",fontSize = 14.sp,color = xqy_90A4BE,modifier = Modifier.padding(start = 4.dp))
                }
            }

            AndroidView(
                factory = {
                    frameLayout
                 },
                modifier = Modifier.fillMaxSize()
            )
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun showButtomDialog(state: BottomDrawerState){
//    BottomDrawer(
//        drawerState = state,
//        drawerContent = {
//            Button(onClick = { }) {
//                Text("Button")
//            }
//        }
//    ) {
//        // Screen content
//    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Persstion(){
        val permission = rememberMultiplePermissionsState(
        permissions = listOf(android.Manifest.permission.CAMERA,android.Manifest.permission.ACCESS_FINE_LOCATION))
    TextButton(onClick = { permission.launchMultiplePermissionRequest()}) {
        if(permission.allPermissionsGranted){
            Text(text = "通过")
        }else if(permission.shouldShowRationale){
            Text(text = "拒绝")
        }else{
            Text(text = "不再授权")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun showDialog(context:Context,openDialog: MutableState<Boolean>) {
    if(openDialog.value){
        AlertDialog(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    openDialog.value = false
                }
                .padding(horizontal = 20.dp)
                .clickable { }
                .background(color = Color.White, shape = RoundedCornerShape(9.dp)),
            shape = RoundedCornerShape(9.dp),
            onDismissRequest = {openDialog.value = false },
            properties  = DialogProperties(usePlatformDefaultWidth = false),
            buttons = {
                Column() {
                    Text("这是标题")
                    Text("这是内容`F`%TUnB")
                    TextButton(onClick = { openDialog.value = false }) {
                        Text(text = "确认")
                    }
                }

            }
        )

    }

//        AlertDialog(
//            onDismissRequest = {openDialog.value = false },
//            title = {Text("这是标题")},
//            text = {Text("这是内容`F`%TUnB")},
//            confirmButton = {
//                TextButton(onClick = { openDialog.value = false }) {
//                    Text(text = "确认")
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = { openDialog.value = false }) {
//                    Text(text = "取消")
//                }
//            },
//        )
}

class ContractFragment:Fragment(){

    companion object{
        fun newInstance(): ContractFragment{
            val args = Bundle()
            val fragment = ContractFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        setContent {
            Box(contentAlignment = Alignment.Center){
                Text(text = "fragment",modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),textAlign = TextAlign.Center)
            }
        }
    }
}
