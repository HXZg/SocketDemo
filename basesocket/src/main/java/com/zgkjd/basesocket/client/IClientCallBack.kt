package com.zgkjd.basesocket.client

import com.zgkjd.basesocket.bean.response.BaseResponseStatusEntity

/**
 * Created by xian_zhong on 2019/1/10.
 * des : ${class}
 */
interface IClientCallBack {

    fun onSuccess(entity:BaseResponseStatusEntity)

    fun onFail(e : Exception)

    interface ISocketCallBack{
        fun onSuccess(s : String)

        fun onFail(api:String)
    }

}