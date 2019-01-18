package com.zgkjd.kjdsdk.received

import android.telecom.GatewayInfo
import com.zgkjd.kjdsdk.bean.response.GateWayInfo

/**
 * @title com.zgkjd.kjdsdk.received  SocketBase
 * @author xian_zhong  admin
 * @version 1.0
 * @Des 列表发生改变
 */
class ListChangReceived {

    /**
     * 存储有改变的列表状态
     */
    private val mListStatus = arrayListOf<String>()

    var mAllListener : ListStatusUpdate? = null

    private val map = HashMap<String,ListStatusUpdate.SingleStatusUpdate>()

    /**
     * 列表改变
     * 存储所有新的version，与data的version进行比较
     * 请求列表时，也存储对应的version
     */
    fun handlerUpdateList(bean : GateWayInfo.DataVerBean){

    }

    /**
     * 单个列表的监听 ， 增加监听的contract
     */
    fun addStatusListener(constract : String,listener : ListStatusUpdate.SingleStatusUpdate){
        map[constract] = listener
    }

    /**
     * 取消某个列表的监听
     */
    fun removeStatusListener(constract: String = ""){
        if (constract.isNotEmpty()) map.remove(constract) else map.clear()
    }

    /**
     * 监听调用
     */
    private fun returnListener(){
        if (mListStatus.isNotEmpty()){
            mAllListener?.updateList(mListStatus)
        }
    }

    private fun returnSingle(constract: String){
        for (i in map.keys){
            if (constract == i){
                map[i]?.updateSingle(i)
            }
        }
    }
}