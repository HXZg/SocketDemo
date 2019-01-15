package com.zgkjd.kjdsdk.api

import com.zgkjd.basesocket.exception.ServiceReturnException
import com.zgkjd.basesocket.exception.SocketTimeOutException
import com.zgkjd.basesocket.exception.SocketWriteException
import io.reactivex.functions.Consumer

/**
 * Created by xian_zhong on 2019/1/11.
 * des : ${class}
 */
abstract class ExceptionEngine : Consumer<Throwable>{

    companion object {
        const val SERVICE_RETURN = 0X11
        const val CONNECT_TIME_OUT = 0X12
        const val WRITE_FAIL = 0X13
    }

    override fun accept(t: Throwable?) {
        when(t){
            is ServiceReturnException -> handlerMsg(SERVICE_RETURN,t.message)
            is SocketTimeOutException -> handlerMsg(CONNECT_TIME_OUT,t.message)
            is SocketWriteException -> handlerMsg(WRITE_FAIL,t.message)
            else -> handlerMsg(SERVICE_RETURN,"nothing error:::${t?.message}")
        }
    }

    abstract fun handlerMsg( type: Int,msg : String?)

}