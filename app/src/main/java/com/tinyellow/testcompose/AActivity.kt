package com.tinyellow.testcompose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text

class AActivity :AppCompatActivity(){

    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, AActivity::class.java)
            context.startActivity(starter)
        }
    }

    var url :String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("AActivity","--->onCreate")
        url = savedInstanceState?.getString("url")
        setContent {
            Column() {
                Button(onClick = {
                    url = "1"
                    BActivity.startForResult(this@AActivity,100)
                }
                ) {
                    Text(text = "跳到BActivity")

                }

                Button(onClick = {
                    Toast.makeText(this@AActivity,""+url,Toast.LENGTH_SHORT).show()
                }
                ) {
                    Text(text = "toast:url")

                }
            }

        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.e("AActivity","--->onRestoreInstanceState")

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("url",url)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("AActivity","--->onActivityResult")
        if(100 == requestCode && RESULT_OK == resultCode){

        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.e("AActivity","--->onNewIntent")
    }

}