package com.zgkjd.baseui.mvp

import java.lang.ref.WeakReference

/**
 * Created by xian_zhong on 2019/1/11.
 * des : ${class}
 */
class BaseMvpPresent<V:BaseMvpView> {

    private var vWeakReference: WeakReference<V>? = null

    fun attachView(view: BaseMvpView) {
        vWeakReference = WeakReference(view as V)
    }

    open fun detachView() {
        if (vWeakReference != null) {
            vWeakReference!!.clear()
            vWeakReference = null
        }
    }

    /**
     * activity onStart method
     */
    open fun startListener(){

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