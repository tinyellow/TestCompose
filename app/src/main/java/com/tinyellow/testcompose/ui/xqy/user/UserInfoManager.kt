package com.tinyellow.testcompose.ui.xqy.user

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserInfoManager(private val context: Context) {

    companion object{
        private val Context.userStore:DataStore<Preferences> by preferencesDataStore("user_store")

        val USER_INFO = stringPreferencesKey("user_info")

        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")

        val ACCESS_TOKEN = stringPreferencesKey("access_token")
    }

    suspend fun saveToken(access_token:String, refresh_token:String){
        context.userStore.edit {
            it[ACCESS_TOKEN] = access_token
            it[REFRESH_TOKEN] = refresh_token
        }
    }

    suspend fun saveUserInfo(userInfo:String){
        context.userStore.edit {
            it[USER_INFO] = userInfo
        }
    }

    val accessToken:Flow<String> = context.userStore.data.map { it[ACCESS_TOKEN] ?: ""}

    val refreshToken:Flow<String> = context.userStore.data.map { it[REFRESH_TOKEN] ?: ""}

    val userInfo:Flow<String> = context.userStore.data.map { it[USER_INFO] ?: "" }
}