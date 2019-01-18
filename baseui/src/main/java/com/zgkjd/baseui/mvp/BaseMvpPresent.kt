package com.zgkjd.baseui.mvp

import android.os.Handler
import java.lang.ref.WeakReference

/**
 * Created by xian_zhong on 2019/1/11.
 * des : ${class}
 */
open class BaseMvpPresent<V:BaseMvpView> {

    private var vWeakReference: WeakReference<V>? = null
    private var mHandler : Handler? = null
    private var dialogRunnable = Runnable {
        getView()?.showLoadingDialog()
    }

    fun attachView(view: BaseMvpView) {
        vWeakReference = WeakReference(view as V)
    }

    open fun detachView() {
        if (vWeakReference != null) {
            vWeakReference!!.clear()
            vWeakReference = null
        }
    }

    protected fun showDialog(){
        mHandler?.postDelayed(dialogRunnable,300)
    }

    protected fun dismissDialog(){
        mHandler?.removeCallbacks(dialogRunnable)
        getView()?.dismissLoadingDialog()
    }

    /**
     * activity onStart method
     */
    open fun startListener(){
        mHandler = Handler()
    }

    /**
     * activity onStop method
     */
    open fun stopListener(){

    }

    /**
     * 获取V实例
     */
    protected fun getView(): V? {
        return if (vWeakReference == null) null else vWeakReference!!.get()
    }


}