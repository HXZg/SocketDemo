package com.zgkjd.kjdsdk.received

/**
 * @title com.zgkjd.kjdsdk.received  SocketBase
 * @author xian_zhong  admin
 * @version 1.0
 * @Des ListStatusUpdate  列表状态有更新
 */
interface ListStatusUpdate {

    /**
     * 需要监听多个列表
     * 该状态下列表有更新，需要自己再重新获取列表
     * @param list 所有有改变的列表
     * value : {@link Constract#UPD_DEVICE_LIST}
     */
    fun updateList(list : List<String>)

    interface SingleStatusUpdate{
        fun updateSingle()
    }
}