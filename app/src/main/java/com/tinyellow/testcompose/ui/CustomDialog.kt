package com.tinyellow.testcompose.ui

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.window.DialogProperties
import androidx.fragment.app.DialogFragment


class CustomDialog(private val windowSize:(WindowManager.LayoutParams.(Int, Int)->Unit)? = null,
                   private val window:(Dialog.(Window) -> Unit)? = null,
                   private val gravity:Int = Gravity.CENTER,
                   private val properties: DialogProperties = DialogProperties(),
                   val content: @Composable () -> Unit) : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = ComposeView(requireContext()).apply {
//        layoutParams = ViewGroup.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.MATCH_PARENT
//        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.let { d->
            d.window?.let{ w ->
                w.attributes = w.attributes.apply {
                    w.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))
                    windowSize?.let {
                        val dm: DisplayMetrics = resources.displayMetrics
                        val width = dm.widthPixels
                        val height = dm.heightPixels
                        it(width,height)
                    }
                    gravity = this@CustomDialog.gravity
                    d.setCanceledOnTouchOutside(properties.dismissOnClickOutside)
                    d.setCancelable(properties.dismissOnBackPress)
                    window?.let {  d.it(w) }
                }
            }
        }
        if(view is ComposeView){
            view.setContent {
                content()
            }
        }
    }
}

////@Composable
//fun FragmentActivity.CustomDialog(
//    initWindow:(Window.(WindowManager.LayoutParams) -> Unit)? = null,
//    content: @Composable () -> Unit){
//    CustomDialog {
//        Box(contentAlignment = Alignment.Center){
//            Text(text = "fragment",modifier = Modifier
//                .fillMaxWidth()
//                .background(androidx.compose.ui.graphics.Color.White),textAlign = TextAlign.Center)
//        }
//    }.window {
//        it.width = getWindowWidth(context as Activity)
//        it.gravity = Gravity.BOTTOM
//    }.show(this.supportFragmentManager,null)
//}

