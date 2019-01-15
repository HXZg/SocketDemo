package com.zgkjd.basesocket.client

import android.util.Log
import com.google.gson.Gson
import com.zgkjd.basesocket.bean.request.BaseRequestEntity
import com.zgkjd.basesocket.socket.ISocketClient
import com.zgkjd.basesocket.socket.SocketClient

/**
 * Created by xian_zhongon 2019/1/10.
 * des : ${class}
 */
class KJDSocket(host:String,port: Int) : ISocketClient{

    internal var app : String = ""
    internal var co : String = ""
    internal var timeOut = 15_000L
    private var host : String = ""
    private var port : Int = 0
    internal var initSuc : () -> Unit = {}
    internal var disConnect : () -> Unit = {}
    internal var mListerer : IClientCallBack.ISocketCallBack? = null
    internal var mHandler : KJDHandler? = null
    internal var socket_out = 30_000L

    init {
        if (host.isEmpty() || port == 0){
            throw IllegalArgumentException("host and port must be not null")
        }
        this.host = host
        this.port = port
    }

    private fun connect(){
        mListerer = KJDClient
        SocketClient.mListener = this
        SocketClient.connect(host,port)  //连接服务端
    }

    override fun connectSuccess() {
        initSuc.invoke()
    }

    override fun disConnect() {
        disConnect.invoke()
    }

    fun getHandler() : KJDHandler{
        if (mHandler == null){
            mHandler = KJDHandler()
        }
        return mHandler!!
    }

    override fun resultMsg(s: String) {
        mListerer?.onSuccess(s)
        mHandler?.removeMessages(KJDHandler.DIS_CONNECT)
        mHandler?.sendDisConnect()
    }

    override fun writeFail(s: String) {
        val entity = Gson().fromJson(s, BaseRequestEntity::class.java)
        mListerer?.onFail(entity.api)
    }

    fun<T> sendData(api:String,t : T){
        val baseRequestEntity = BaseRequestEntity(app, co, api, t)
        SocketClient.writeData(Gson().toJson(baseRequestEntity))
    }

    class Build(host : String,port: Int){

        private var mSocket : KJDSocket = KJDSocket(host, port)

        fun setDisCon(action: () -> Unit) : Build{
            mSocket.disConnect = action
            return this
        }

        fun setHandler(handler : KJDHandler) : Build{
            mSocket.mHandler = handler
            return this
        }

        fun setListener(listener : IClientCallBack.ISocketCallBack) : Build{
            mSocket.mListerer = listener
            return this
        }

        fun setInit(action : () -> Unit) : Build{
            mSocket.initSuc = action
            return this
        }

        fun setTimeOut(time : Long) : Build {
            mSocket.timeOut = time
            return this
        }

        fun setAppName(app:String) : Build{
            mSocket.app = app
            return this
        }

        fun setCoName(co:String) : Build{
            mSocket.co = co
            return this
        }

        fun setSocketOut(time: Long) : Build{
            mSocket.socket_out = time
            return this
        }

        fun build() : KJDSocket{
            mSocket.connect()
            return mSocket
        }
    }
}