package com.tinyellow.testcompose.ui.xqy.message

//data class MessageResult(val httpCode:String?,val data:MessageList?)

data class MessageType(val name:String,val type:String)

data class MessageList(val list:List<Message>?)

data class Message (val msgText:String?,val createTime:String?,val msgTitle:String?)