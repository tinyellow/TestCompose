package com.tinyellow.testcompose.ui.xqy.message

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import androidx.paging.compose.itemsIndexed
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.tinyellow.testcompose.R
import com.tinyellow.testcompose.ui.theme.*
import com.zj.refreshlayout.SwipeRefreshLayout
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MessageActivity:AppCompatActivity() {

    companion object{
        @JvmStatic
        fun start(context: Context,token:String) {
            val starter = Intent(context, MessageActivity::class.java)
                .putExtra("token",token)
            context.startActivity(starter)
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val navController = rememberNavController()
            NavHost(navController = navController,
                startDestination = MessageActivity::class.java.simpleName,
                modifier = Modifier.fillMaxSize()){

                composable(MessageActivity::class.java.simpleName){
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            Box(Modifier.fillMaxWidth()) {
                                Image(painter = painterResource(id = R.mipmap.xqy_icon_previous), contentDescription = null,
                                    modifier = Modifier
                                        .align(Alignment.CenterStart)
                                        .padding(start = 6.dp)
                                        .clickable {
                                            (context as? Activity)?.finish()
                                        }
                                        .padding(10.dp))
                                Text(text = "消息中心",color = xqy_162D43,fontSize = 18.sp,fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .align(Alignment.Center))
                            }
                        }
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            val tabs = remember{listOf(MessageType("全部",""),MessageType("合同消息","CONTRACT"), MessageType("其他消息","OTHER"),MessageType("审批消息","APPROVE"))}
                            val pagerState = rememberPagerState()
                            val coroutineScope = rememberCoroutineScope()
                            TabRow(selectedTabIndex = pagerState.currentPage,
                                backgroundColor = Color.White,
                                indicator = {
                                    TabRowDefaults.Indicator(
                                        modifier = Modifier
                                            .tabIndicatorOffset(it[pagerState.currentPage])
//                                    .pagerTabIndicatorOffset(pagerState, it)
                                            .padding(horizontal = 25.dp),
                                        height = 2.dp,
                                        color = xqy_757AB8
                                    )
                                },
                                divider = {},
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                tabs.forEachIndexed { i, s ->
                                    Tab(selected = i == pagerState.currentPage,
//                        modifier = Modifier.fillMaxHeight(),
                                        onClick = {  }) {
                                        TextButton(onClick = {
                                            if(pagerState.currentPage == i){ return@TextButton }
                                            coroutineScope.launch {
                                                pagerState.animateScrollToPage(i)
                                            }
                                        }) {
                                            Text(text = s.name,color = if(i == pagerState.currentPage) xqy_757AB8 else xqy_666666 ,fontSize = 14.sp,//fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(vertical = 1.dp))
                                        }

                                    }

                                }
                            }

                            HorizontalPager(
                                count = tabs.size,
                                state = pagerState,
                                // Add 16.dp padding to 'center' the pages
//                        contentPadding = PaddingValues(16.dp),
                                modifier = Modifier
//                                    .weight(1f)
                                    .fillMaxSize()
                            ){
                                val token = remember {intent.getStringExtra("token")?:""}
                                MessageList(it,pagerState.currentPage,token,tabs[it].type, {
                                    navController.navigate("detail/${it.msgText}")
                                })
                            }
                        }

                    }
                }

                composable(
                    route = "detail/{content}",
                    arguments = listOf(navArgument("content"){
                         type = NavType.StringType
                    }),
                ){
                    Box(modifier = Modifier.fillMaxSize()){
                        Text("详情:${it.arguments?.getString("content")}",modifier = Modifier
                            .align(Alignment.Center)
                            .clickable {
                                navController.popBackStack()
                            })
                    }
                }
            }




        }
    }

}

@Composable
fun MessageList(
    pager:Int,
    currentPager:Int,
    token:String,
    msgCode:String,
    onItemClick:(Message)->Unit,
    viewModel: MessageViewModel = viewModel(
        key = msgCode,
        factory = MessageViewModel.factory(token,msgCode)
    )){

    val collectItem = viewModel.messages.collectAsLazyPagingItems()
//    var firstLoad by remember{ mutableStateOf(false) }
//    if(!firstLoad){
//        if(pager == currentPager){
//            firstLoad = true
//            collectItem.refresh()
//            Log.e("message","MessageList($msgCode)-->getList")
//        }
//    }
//    Log.e("message","MessageList($msgCode)")
//    LaunchedEffect(Unit){
//        Log.e("message","MessageList($msgCode)-->LaunchedEffect")
//        if(pager == currentPager){
//            viewModel.getList()
////            Log.e("message","MessageList($msgCode)-->LaunchedEffect->getList")
//        }
//    }
//    DisposableEffect(Unit){
//        onDispose { Log.e("message","MessageList($msgCode)-->DisposableEffect") }
//    }

//    LazyColumn(
//        contentPadding = PaddingValues(horizontal = 12.dp),
//        verticalArrangement = Arrangement.spacedBy(9.dp)
//    ){
    var isRefreshing by viewModel.isRefreshing

    SwipeRefreshLayout(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = isRefreshing,
        onRefresh = { collectItem.refresh()}
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            items(collectItem) {
                it?.let { it1 -> MessageItem(it1) { onItemClick(it1) } }
            }
            when (collectItem.loadState.append) {
                is LoadState.Loading -> item {
                    Text(
                        text = "加载中",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                is LoadState.Error -> item {
                    TextButton(onClick = { collectItem.retry() }) {
                        Text(
                            text = "加载失败，点击重新加载",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
        isRefreshing = when(collectItem.loadState.refresh){
            is LoadState.Loading -> true
            is LoadState.NotLoading -> false
            is LoadState.Error -> false
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MessageItem(message:Message,onClick: () -> Unit){
    Card(onClick = onClick,
        elevation = 0.dp,
        backgroundColor = Color.Transparent,
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(0.2.dp, xqy_CCCCCC),
        modifier = Modifier.fillMaxWidth()
        ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 18.dp, end = 18.dp, bottom = 18.dp)) {
            Text(text = message.msgTitle.toString(),fontSize = 14.sp)

            Text(text = message.msgText.toString(),fontSize = 12.sp,maxLines = 2,overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 6.dp))

            Text(text = dateStr(message.createTime),color = xqy_999999,fontSize = 12.sp,maxLines = 2,overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 6.dp))


        }
    }
}

@Composable
fun dateStr(strDate: String?): String {
    val formatter = remember { SimpleDateFormat("yyyy-MM-dd HH:mm:ss") }

    val minute = (60 * 1000).toLong() // 1分钟
    val hour = 60 * minute // 1小时
    val day = 24 * hour // 1天
    val month = 31 * day // 月
    var result = ""
    return try {
        val date: Date = formatter.parse(strDate)
        val s = System.currentTimeMillis()
        val diff = s - date.time

        result = if (diff < hour) {
            val r: Long = diff / hour
            r.toString() + "分钟前"
        } else if (diff < day) {
            val r: Long = diff / day
            r.toString() + "小时前"
        } else if (diff < day * 2) {
            SimpleDateFormat("昨天 HH:mm").format(date.time)
        } else if (diff < 12 * month) {
            SimpleDateFormat("MM月dd日 HH:mm").format(date.time)
        } else {
            SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(date.time)
        }
        result
    } catch (ex: Exception) {
        result
    }
}