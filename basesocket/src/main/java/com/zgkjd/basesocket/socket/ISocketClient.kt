package com.zgkjd.basesocket.socket

/**
 * Created by admin on 2019/1/10.
 */
interface ISocketClient {
    /**
     * 接收的数据结果
     */
    fun resultMsg(s:String)

    /**
     * socket连接成功
     */
    fun connectSuccess()

    /**
     * socket断开连接
     */
    fun disConnect()

    /**
     * 写入文件失败
     */
    fun writeFail(s:String)
}