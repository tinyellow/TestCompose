package com.tinyellow.testcompose

sealed class BooleanExt<out T>

class Success<T>(val data:T):BooleanExt<T>()

object OtherWise:BooleanExt<Nothing>()

inline fun <T> Boolean.yes(block:()->T):BooleanExt<T> =
    when{
        this -> Success(block())
        else -> OtherWise
    }

inline fun <T> Boolean.no(block:()->T):BooleanExt<T> =
    when{
        this -> OtherWise
        else -> Success(block())
    }

inline fun <T> BooleanExt<T>.otherwise(block:()->T):T =
    when(this){
        is Success -> this.data
        OtherWise -> block()
    }

inline fun Boolean.yes2(block:()->Unit):Boolean =
    when{
        this -> {
            block()
            true
        }

        else -> false
    }

inline fun Boolean.no2(block:()->Unit):Boolean =
    when{
        !this -> {
            block()
            false
        }
        else -> true
    }