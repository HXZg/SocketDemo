package com.zgkjd.baseui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by xian_zhong on 2019/1/11.
 * des : ${class}
 */
abstract class BaseFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (getFragmentLayoutId() > 0){
            inflater.inflate(getFragmentLayoutId(),container,false)
        }else{
            createView(inflater, container, savedInstanceState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.isClickable = true
        initData()
        initEvent()
    }

    abstract fun getFragmentLayoutId() : Int

    open fun createView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) : View? = null

    abstract fun initData()

    abstract fun initEvent()
}