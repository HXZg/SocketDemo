package com.zgkjd.basesocket.client

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.zgkjd.basesocket.exception.SocketTimeOutException
import com.zgkjd.basesocket.socket.SocketClient

/**
 * Created by xian_zhong on 2019/1/11.
 * des : ${class}
 */
class KJDHandler : Handler(Looper.myLooper()) {

    companion object {
        const val WRITE_READ_TIME_OUT = 0X12
        const val CONNECT_SERVICE = 0X13
        const val DIS_CONNECT = 0X14
    }

    val apiList = ArrayList<String>()

    override fun handleMessage(msg: Message?) {
        super.handleMessage(msg)
        when (msg?.what){
            WRITE_READ_TIME_OUT -> handlerTimeOut(msg.obj as String)
            CONNECT_SERVICE -> connectService()
            DIS_CONNECT -> disConnect()
        }
    }

    fun sendTimeOut(api: String,time:Long = 0){
        apiList.add(api)
        val message = obtainMessage(WRITE_READ_TIME_OUT, api)
        val out = if (time == 0L) KJDClient.mKS!!.timeOut else time
        if (KJDClient.mKS != null) sendMessageDelayed(message,out)
    }

    fun removeApiMsg(api: String){
        apiList.indices
                .filter { apiList[it] == api }
                .forEach { apiList.removeAt(it) }
    }

    fun sendConnect(){
        sendEmptyMessageDelayed(CONNECT_SERVICE,300)
    }

    fun sendDisConnect(){
        sendEmptyMessageDelayed(DIS_CONNECT,KJDClient.mKS!!.socket_out)
    }

    fun handlerTimeOut(api : String){
        val indexOf = apiList.indexOf(api)
        if (indexOf in 0 until apiList.size){
            Log.i("socket_test_text","handler time out")
            KJDClient.map[api]?.onFail(SocketTimeOutException("service time out"))  //服务端未回复
            KJDClient.map.remove(api)
            apiList.removeAt(indexOf)
        }
    }

    fun connectService(){
        SocketClient.connect(SocketClient.mHost,SocketClient.mPort)
    }

    fun disConnect(){
        SocketClient.stopConnect()
        sendConnect()
    }
}