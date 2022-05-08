package com.tinyellow.testcompose.ui.xqy.user

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

val gson = Gson()

val LocalUserModel = compositionLocalOf<UserViewModel> { error("No Init UserMode") }

fun UserInfo?.isLogin():Boolean{
    return null != this
}

class UserViewModel(context: Context) :ViewModel(){

    private val userManager = UserInfoManager(context)

    private var _userInfo = mutableStateOf<UserInfo?>(null)//:UserInfo? = null

    private var accessToken :String? = null
    private var refreshToken:String? = null

    init {
        viewModelScope.launch {
            accessToken = userManager.accessToken.firstOrNull()
            refreshToken = userManager.refreshToken.firstOrNull()
            val userInfoString = userManager.userInfo.firstOrNull()
            val userInfo = gson.fromJson(userInfoString,UserInfo::class.java)
            _userInfo.value = userInfo
        }
    }

//    fun isLogin():Boolean{
//        return null != _userInfo//null != refreshToken && null != _userInfo
//    }

//    suspend fun getUserInfo() :UserInfo{
//        val userInfoString = userManager.userInfo.firstOrNull()
//        _userInfo = gson.fromJson(userInfoString,UserInfo::class.java)
//        return _userInfo ?: UserInfo(null)
//    }

    fun savaUserInfo(userInfo:UserInfo){
        viewModelScope.launch {
            userManager.saveUserInfo(gson.toJson(userInfo))
            _userInfo.value = userInfo
        }

    }

//    @SuppressLint("RememberReturnType")
//    @Composable
//    fun isLogin(): State<Boolean> {
//        val isLogin = remember {
//            derivedStateOf {
//                null != _userInfo
//            }
//        }
//        return isLogin
//    }


//    @Composable
    fun userInfo(): State<UserInfo?> {
//        LaunchedEffect(Unit){
//            val userInfoString = userManager.userInfo.firstOrNull()
//            val userInfo = gson.fromJson(userInfoString,UserInfo::class.java)
//            _userInfo.value = userInfo
//        }
        return _userInfo
//        return produceState(initialValue = _userInfo.value,_userInfo){
//            val userInfoString = userManager.userInfo.firstOrNull()
//            val userInfo = gson.fromJson(userInfoString,UserInfo::class.java)
//            userInfo?.let {
//                value = it
//            }
//            _userInfo.value = userInfo
//        }
    }

}