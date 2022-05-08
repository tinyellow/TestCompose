package com.littleyellow.kotlinlib.framework.net

import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.*

interface ApiService {

    @POST
    suspend fun post(@Url url: String?, @Body jsonStr: RequestBody?, @HeaderMap headers: Map<String, String>?): Response<ResponseBody>

    @FormUrlEncoded
    @POST
    suspend fun post(@Url url: String?, @FieldMap params: Map<String, String>?, @HeaderMap headers: Map<String, String>?): Response<ResponseBody>

    @POST
    suspend fun uploadFile(@Url url: String?, @Body multipartBody: RequestBody?, @QueryMap params: Map<String, String>?, @HeaderMap headers: Map<String, String>?): Response<ResponseBody>

}

suspend fun <T> ApiService.postJson(url: String?, jsonStr: String?, headers: Map<String?, String?>?,parser: IParser<T>): Result<T> {
    val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr)
    return RetrofitClient.request(post(url,requestBody,RetrofitClient.securityMap(headers)),parser)
}

suspend fun <T> ApiService.postForm(url: String?, params: Map<String?, String?>?, headers: Map<String?, String?>?,parser: IParser<T>): Result<T>{
    return RetrofitClient.request(post(url,RetrofitClient.securityMap(params),RetrofitClient.securityMap(headers)),parser)
}

object RetrofitClient {

    private const val BASE_URL = "https://5e510330f2c0d300147c034c.mockapi.io/"

    val apiService: ApiService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    fun <T> request(response: Response<ResponseBody>,parser: IParser<T>): Result<T> {
        return try {
            val responseBody = response.body()
            val data = responseBody?.string()
            parser.parseData(data)
        }catch (e: Exception){
            e.printStackTrace()
            parser.parseThrowable(e)
        }
    }

    fun securityMap(map: Map<String?, String?>?): Map<String, String> {
        val result = HashMap<String, String>()
        map?.forEach {
            if(null != it.key&&null != it.value){
                result[it.key!!] = it.value!!
            }
        }
        return result
    }
}

sealed class Result<out R>(open val data: R?, open val exception: Exception?) {
    data class Success<out T>(override val data: T?) : Result<T>(data,null)
    data class Error(override val exception: Exception?) : Result<Nothing>(null,exception)
    fun isError() = this !is Success
}

fun <T> Result<T>.successOr(fallback: T): T {
    return (this as? Result.Success<T>)?.data ?: fallback
}

interface IParser<out T> {
    fun parseData(json:String?): Result<T>
    fun parseThrowable(e: Exception):Result<Nothing>
}

abstract class SimpleParser<T> : IParser<T> {
    companion object{
        val gson = Gson()
        inline fun <reified T> get():SimpleParser<T>{
            return object : SimpleParser<T>(){}
        }
    }

    override fun parseData(json: String?): Result<T> {
        val type: Type? = javaClass.genericSuperclass
        return (type as? ParameterizedType)?.actualTypeArguments?.let {
            val data:T? = gson.fromJson(json, it[0])
            Result.Success(data)
        }?:Result.Error(Exception("A generic exception"))
    }

    override fun parseThrowable(e: Exception): Result<Nothing> = Result.Error(e)
}

abstract class StringParser : SimpleParser<String>(){
    companion object{
        fun get() = object : StringParser(){}
    }
    override fun parseData(json: String?) = Result.Success(json)
}