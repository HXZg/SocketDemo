package com.zgkjd.kjdsdk

import com.zgkjd.basesocket.client.IClientCallBack
import com.zgkjd.basesocket.client.KJDHandler
import com.zgkjd.basesocket.client.KJDSocket

/**
 * Created by xian_zhong on 2019/1/11.
 * des : 外部无法直接访问KJDSocket,所以提供这一套
 */
class KJDSDKBuild(host:String,port:Int) {

    internal companion object {
        /**
         * KJD SDK 常量
         */
        const val SDK_HOST = "120.76.27.28"
        const val SDK_PORT = 24411
        const val SDK_APP = "smart"
        const val SDK_CO = "kjd"
        const val SDK_TIME_OUT = 15_000L   //回复的超时时间
        const val SDK_SOCKET_OUT = 30_000L  //长时间未收到服务端消息

        fun getKJDSocket(action: () -> Unit = {}) : KJDSocket{
            return KJDSocket.Build(SDK_HOST, SDK_PORT)
                    .setAppName(SDK_APP)
                    .setCoName(SDK_CO)
                    .setTimeOut(SDK_TIME_OUT)
                    .setSocketOut(SDK_SOCKET_OUT)
                    .setInit(action)
                    .setHandler(KJDHandler())
                    .build()  //连接服务端
        }
    }

    var mSocket : KJDSocket.Build = KJDSocket.Build(host, port)

    fun setDisCon(action: () -> Unit) : KJDSDKBuild {
        mSocket.setDisCon(action)
        return this
    }

    fun setInit(action : () -> Unit) : KJDSDKBuild{
        mSocket.setInit(action)
        return this
    }

    fun setTimeOut(time : Long) : KJDSDKBuild{
        mSocket.setTimeOut(time)
        return this
    }

    fun setAppName(app:String) : KJDSDKBuild{
        mSocket.setAppName(app)
        return this
    }

    fun setCoName(co:String) : KJDSDKBuild{
        mSocket.setCoName(co)
        return this
    }

    fun build() : KJDSocket{
        return mSocket.build()
    }

}