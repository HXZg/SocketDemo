package com.zgkjd.basesocket.client

import android.util.Log
import com.google.gson.Gson
import com.zgkjd.basesocket.bean.response.BaseResponseStatusEntity
import com.zgkjd.basesocket.exception.ServiceReturnException
import com.zgkjd.basesocket.exception.SocketWriteException

/**
 * Created by xian_zhong on 2019/1/10.
 * des : ${class}
 */
object KJDClient : IClientCallBack.ISocketCallBack{

    var mKS : KJDSocket? = null
    var map : HashMap<String,IClientCallBack?> = HashMap()
    var listener : (s : BaseResponseStatusEntity) -> Unit = {}            //主动监听

    override fun onSuccess(s: String) {
        try {
            val entity = Gson().fromJson(s, BaseResponseStatusEntity::class.java)
            if (entity != null && entity.api.isNotEmpty()){   //不是心跳字段
                entity.rawJsonData = s  //赋值json源数据
                val iClientCallBack = map[entity.api]
                if (iClientCallBack != null){
                    if (entity.isSuccess())
                        iClientCallBack.onSuccess(entity)
                    else
                        iClientCallBack.onFail(ServiceReturnException(entity))
                    map.remove(entity.api)
                    mKS?.getHandler()?.removeApiMsg(entity.api)
                }else{
                    listener.invoke(entity)
                }
            }
        }catch (e : Exception){
            e.printStackTrace()
            Log.i("socket_test_text","${e.message}")
        }
    }

    override fun onFail(api: String) {
        map[api]?.onFail(SocketWriteException("write fail"))
        map.remove(api)
    }

    fun requestApi(api:String, data:Any,timeout:Long = 0) : Callable{
        val callable = Callable()
        map[api] = callable
        mKS?.sendData(api,data)
        mKS?.getHandler()?.sendTimeOut(api,timeout)
        return callable
    }

}
