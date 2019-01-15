package com.zgkjd.socketbase

import com.zgkjd.baseui.mvp.BaseMvpViewActivity
import com.zgkjd.kjdsdk.KJDSDKManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvpViewActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
    }

    override fun initEvent() {
        tv_hello.setOnClickListener { KJDSDKManager.init() }
    }

}
