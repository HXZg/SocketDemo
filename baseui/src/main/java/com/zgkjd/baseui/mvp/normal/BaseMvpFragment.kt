package com.zgkjd.baseui.mvp.normal

import com.zgkjd.baseui.mvp.BaseMvpPresent
import com.zgkjd.baseui.mvp.BaseMvpView
import com.zgkjd.baseui.mvp.BaseMvpViewFragment

/**
 * Created by xian_zhong on 2019/1/11.
 * des : ${class}
 */
abstract class BaseMvpFragment<out P : BaseMvpPresent<out BaseMvpView>> : BaseMvpViewFragment(){

    private var presenter: P? = null

    /**
     * 创建P层
     *
     * @return P层对象
     */
    protected abstract fun createPresenter(): P

    /**
     * 获取P层实例
     *
     * @return P层实例
     */
    private fun getPresenter(): P {
        if (presenter == null) {
            presenter = createPresenter()
        }

        if (this is BaseMvpView) {
            //依附V
            presenter!!.attachView(this)
        }
        return presenter as P
    }

    override fun onStart() {
        super.onStart()
        getPresenter().startListener()
    }

    override fun onStop() {
        super.onStop()
        getPresenter().stopListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter().detachView()
    }
}