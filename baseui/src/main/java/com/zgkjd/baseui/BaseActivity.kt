package com.zgkjd.baseui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.zgkjd.baseui.utils.XZRosourceUtil

/**
 * Created by xian_zhong on 2019/1/11.
 * des : ${class}
 */
abstract class BaseActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeSetView()
        if (getLayoutId() > 0){
            setContentView(getLayoutId())
        }else{
            setView()
        }
        afterSetView()
    }

    open fun beforeSetView(){}

    open fun afterSetView(){
        initData()
        val id = XZRosourceUtil.getId(this, "ib_back")
        findViewById<View>(id)?.setOnClickListener { finish() }
        initEvent()
    }

    open fun setView(){}


    abstract fun getLayoutId() : Int

    abstract fun initData()

    abstract fun initEvent()

}