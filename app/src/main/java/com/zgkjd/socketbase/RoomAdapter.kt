package com.zgkjd.socketbase

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zgkjd.kjdsdk.bean.response.DevListInfo

/**
 * @title com.zgkjd.socketbase  SocketBase
 * @author xian_zhong  admin
 * @version 1.0
 * @Des RoomAdapter
 */
class RoomAdapter(data : List<MultiItemEntity>) : BaseMultiItemQuickAdapter<MultiItemEntity,BaseViewHolder>(data) {

    val mDevIdMap = HashMap<String,Int>()  //存储dev_id对应的position

    init {
        addItemType(0,R.layout.view_room_group)
        addItemType(1,R.layout.view_room_item)
    }

    override fun convert(helper: BaseViewHolder?, item: MultiItemEntity?) {

        when (helper?.itemViewType){
            0 -> {
                helper.setText(R.id.tv_group,(item as RoomHead).info.area_name)
                helper.itemView.setOnClickListener {
                    val adapterPosition = helper.adapterPosition
                    if (item.isExpanded){
                        collapse(adapterPosition)
                    }else{
                        expand(adapterPosition)
                    }
                }
            }
            1 -> {
                mDevIdMap[(item as RoomHead.DevInfo).dev.dev_id] = helper.adapterPosition
                helper.setText(R.id.tv_item, item.dev.dev_name)
                        .setText(R.id.tv_sta,item.typeSta?.sta)
            }
        }
    }

}