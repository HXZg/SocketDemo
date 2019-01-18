package com.zgkjd.socketbase

import android.support.multidex.MultiDexApplication
import com.blankj.utilcode.util.Utils
import com.zgkjd.kjdsdk.KJDSDKManager

/**
 * Created by xian_zhong on 2019/1/11.
 * des : ${class}
 */
class MyApplication : MultiDexApplication(){

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        KJDSDKManager.init()
    }

}