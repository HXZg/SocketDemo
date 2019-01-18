package com.zgkjd.baseui.utils

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView

/**
 * Created by xian_zhong on 2019/1/11.
 * des : ${class}
 */
object ProgressDialogUtils {

    const val tvID = 0x123
    var proColor : Int = android.R.color.holo_blue_dark
    var tvColor : Int = android.R.color.holo_blue_dark
    var dialog : AlertDialog? = null

    fun initDialog(context: Context){
        if (dialog == null){
            dialog = AlertDialog.Builder(context)
                    .setView(createProgressView(context,""))
                    .create()
            dialog!!.setCanceledOnTouchOutside(false)
            if (dialog!!.window != null){
                dialog!!.window.setBackgroundDrawable(BitmapDrawable())
            }
        }
    }

    fun showDialog(context: Context,text: String?){
        if (dialog == null){
            initDialog(context)
        }
        dialog!!.findViewById<TextView>(tvID)?.text = text
        if (!dialog!!.isShowing){
            dialog?.show()
        }
    }

    fun dismissDialog(){
        if (dialog != null){
            dialog?.dismiss()
        }
    }

    fun createProgressView(context:Context,text : String) : View?{

        val linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.gravity = Gravity.CENTER
        val p = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        linearLayout.layoutParams = p

        val contentLoadingProgressBar = ProgressBar(context)
        contentLoadingProgressBar.indeterminateDrawable
                .setColorFilter(context.resources.getColor(proColor),PorterDuff.Mode.MULTIPLY)
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        contentLoadingProgressBar.layoutParams = layoutParams
        linearLayout.addView(contentLoadingProgressBar)

        val textView = TextView(context)
        textView.id = tvID
        textView.textSize = 14f
        textView.setTextColor(context.resources.getColor(tvColor))
        textView.text = text
        linearLayout.addView(textView)

        return linearLayout
    }
}