package com.zgkjd.basesocket.client

import com.zgkjd.basesocket.bean.response.BaseResponseStatusEntity

/**
 * Created by xian_zhong on 2019/1/11.
 * des : ${class}
 */
class Callable : IClientCallBack{

    var fail : (e : Exception) -> Unit = {}
    var suc : (t : BaseResponseStatusEntity) -> Unit = {}

    override fun onSuccess(entity: BaseResponseStatusEntity) {
        suc.invoke(entity)
    }

    override fun onFail(e:Exception) {   //客户端没有回复，或写入失败
        fail.invoke(e)
    }

    fun addCall(suc : (t : BaseResponseStatusEntity) -> Unit,fail : (e : Exception) -> Unit){
        this.suc = suc
        this.fail = fail
    }
}