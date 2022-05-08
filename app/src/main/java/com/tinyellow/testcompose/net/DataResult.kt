package com.littleyellow.kotlinlib

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.littleyellow.kotlinlib.framework.net.Result
import com.littleyellow.kotlinlib.framework.net.SimpleParser
import org.json.JSONObject
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


abstract class DataParser<T>(private val mGson: Gson) : SimpleParser<T>(){

    companion object{
        inline fun <reified T> get(mGson: Gson = gson): SimpleParser<T> {
            return object : DataParser<T>(mGson){}
        }
    }

    override fun parseData(json: String?):Result<T>{
        return try {
            val jsonObject = JSONObject(json)
            val httpCode = jsonObject.optString("httpCode")
            if("200"==httpCode){
                val dataStr = jsonObject.optString("data")
                val type: Type? = javaClass.genericSuperclass
                (type as? ParameterizedType)?.actualTypeArguments?.let {
                    val data :T = mGson.fromJson(dataStr, it[0])
                    Result.Success(data)
                }?:Result.Error(Exception("泛型异常"))
            }else{//业务异常
                val message = jsonObject.optString("message")
                Result.Error(Exception(message))
            }
        } catch (e: JsonParseException) {
            Result.Error(Exception("Json解析异常：${e.message}"))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
