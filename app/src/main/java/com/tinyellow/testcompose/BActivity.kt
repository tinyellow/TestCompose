package com.tinyellow.testcompose

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text

class BActivity :AppCompatActivity(){

    companion object{
        @JvmStatic
        fun startForResult(context: Activity,requestCode:Int) {
            val starter = Intent(context, BActivity::class.java)
            context.startActivityForResult(starter,requestCode)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column() {
                Button(onClick = {
                setResult(RESULT_OK)
                finish()
                }
                ) {
                    Text(text = "RESULT_OK返回AActivity")

                }

                Button(onClick = {
                    AActivity.start(this@BActivity)
                }
                ) {
                    Text(text = "reStart返回AActivity")

                }
            }

        }
    }

}