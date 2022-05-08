package com.tinyellow.testcompose.ui.xqy.message
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.littleyellow.kotlinlib.DataParser
import com.littleyellow.kotlinlib.framework.net.*

val api : ApiService get() = RetrofitClient.apiService

class MessageViewModel(private val token:String,private val msgCode:String):ViewModel() {

    companion object{
        fun factory(token:String,msgCode:String) = object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MessageViewModel(token,msgCode) as T
            }
        }
    }

//    "requestBody":"{"companyId":"0","page":"1","rows":"15","msgCode":""}"

    val isRefreshing = mutableStateOf(false)

    val messages = Pager(PagingConfig(15,1)){
        SimplePaging(15){
            val data = getList(it)
            data
        }
    }.flow.cachedIn(viewModelScope)


    private suspend fun getList(rows:Int):List<Message>?{

        val url = ""
        val heads = HashMap<String?, String?>()

        val map  = hashMapOf<String?,String?>()
        map["page"] = rows.toString()
        map["rows"] = "15"
        map["companyId"] = "0"
        map["msgCode"] = msgCode
        Log.e("message",map.toString())
        val result : Result<MessageList> = api.postForm(url,map,heads, DataParser.get())
        if(result.isError()){
            return  null
        }else{
            return result.data?.list?.let {
                return it
            } ?: emptyList()
        }
    }
}