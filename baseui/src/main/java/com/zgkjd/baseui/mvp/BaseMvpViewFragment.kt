package com.zgkjd.baseui.mvp

import android.app.Activity
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.Gravity
import com.blankj.utilcode.util.ToastUtils
import com.zgkjd.baseui.BaseFragment
import com.zgkjd.baseui.utils.ProgressDialogUtils

/**
 * Created by xian_zhong on 2019/1/11.
 * des : ${class}
 */
abstract class BaseMvpViewFragment : BaseFragment(){


    fun showToast(msg: String?, isLong: Boolean) {
        if (TextUtils.isEmpty(msg)) {
            return
        }
        ToastUtils.setBgColor(ContextCompat.getColor(context!!, android.R.color.white))
        ToastUtils.setMsgColor(ContextCompat.getColor(context!!, android.R.color.black))
        ToastUtils.setGravity(Gravity.CENTER, 0, 0)
        if (isLong) {
            ToastUtils.showLong(msg)
        } else {
            ToastUtils.showShort(msg)
        }
    }

    fun showLoadingDialog(msg: String?) {
        ProgressDialogUtils.showDialog(context!!,msg)
    }

    fun dismissLoadingDialog() {
        ProgressDialogUtils.dismissDialog()
    }

    fun startActivity(clz: Class<out Activity>) {
        startActivity(Intent(context,clz))
    }

    fun finishActivity() {
        activity?.finish()
    }

    fun startActivityThenFinishSelf(clz: Class<out Activity>) {
        startActivity(clz)
        finishActivity()
    }
}