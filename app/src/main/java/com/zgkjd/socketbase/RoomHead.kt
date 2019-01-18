package com.zgkjd.socketbase

import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zgkjd.kjdsdk.bean.response.AreaListInfo
import com.zgkjd.kjdsdk.bean.response.DevListInfo
import com.zgkjd.kjdsdk.bean.response.DevTypeStaInfo

/**
 * @title com.zgkjd.socketbase  SocketBase
 * @author xian_zhong  admin
 * @version 1.0
 * @Des RoomHead
 */

class RoomHead(val info : AreaListInfo) : AbstractExpandableItem<RoomHead.DevInfo>(),MultiItemEntity{

    override fun getLevel(): Int {
        return 0
    }

    override fun getItemType(): Int {
        return 0
    }

    class DevInfo(val dev : DevListInfo) : MultiItemEntity{

        var mac : String = ""
        var typeSta : DevTypeStaInfo? = null

        override fun getItemType(): Int {
            return 1
        }
    }

}