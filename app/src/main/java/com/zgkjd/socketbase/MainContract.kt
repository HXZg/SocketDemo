package com.zgkjd.socketbase

import com.blankj.utilcode.util.LogUtils
import com.zgkjd.baseui.mvp.BaseMvpPresent
import com.zgkjd.baseui.mvp.BaseMvpView
import com.zgkjd.kjdsdk.KJDSDKManager
import com.zgkjd.kjdsdk.bean.response.AreaListInfo
import com.zgkjd.kjdsdk.bean.response.DevListInfo
import com.zgkjd.kjdsdk.bean.response.DevTypeStaInfo
import com.zgkjd.kjdsdk.manager.Constract
import com.zgkjd.kjdsdk.received.CustomerListener

/**
 * @title com.zgkjd.socketbase  SocketBase
 * @author xian_zhong  admin
 * @version 1.0
 * @Des MainContract
 */
class MainContract {

    interface View : BaseMvpView{
        fun setRoomAdapterData(data : List<RoomHead>)
        fun updateDevStaUi(info : DevTypeStaInfo)
    }

    class Present : BaseMvpPresent<View>(){
        val listenerApi = arrayOf(Constract.UPDATE_DEV_STA,Constract.BIND_GATEWAY_SUC)
        var getList = false

        fun getAreaDevInfo(){
            showDialog()
            if (KJDSDKManager.mGWStatus == "ok" && !getList){
                getList = true
                KJDSDKManager.mDeviceManager!!.getAllAreaList({ it ->
                    getDevList(it)
                },{
                    dismissDialog()
                    getView()?.showToast("area is null")
                })
            }
        }

        fun getDevList(data : List<AreaListInfo>){
            KJDSDKManager.mDeviceManager!!.getAllDeviceList({
                val devlist = arrayListOf<RoomHead.DevInfo>()
                for (i in it){
                    for (j in it[i.key]!!){
                        val devInfo = RoomHead.DevInfo(j)
                        devInfo.mac = i.key
                        devlist.add(devInfo)
                    }
                }
                val list = arrayListOf<RoomHead>()
                for (i in data){
                    list.add(RoomHead(i))
                }
                getDevSta(list,devlist)
//                getView()?.setRoomAdapterData(list)
            },{
                dismissDialog()
                getView()?.showToast("dev list is null")
            })
        }

        fun getDevSta(data : List<RoomHead>,dev:List<RoomHead.DevInfo>){
            KJDSDKManager.mDeviceManager!!.getAllDevSta({
                for (i in it){
                    for (j in dev){
                        if (i.key == j.dev.dev_id){
                            j.typeSta = i.value
                        }
                    }
                }
                for (i in data){
                    for (j in dev){
                        if (i.info.area_id == j.dev.area_id){
                            i.addSubItem(j)
                        }
                    }
                }
                getView()?.setRoomAdapterData(data)
                dismissDialog()
            },{
                dismissDialog()
                getView()?.showToast("dev sta is null")
            })
        }

        fun sendCMD(info : RoomHead.DevInfo){
            showDialog()
            val s = if (info.typeSta?.sta == "00"){
                "01"
            }else{
                "00"
            }
            KJDSDKManager.mDeviceManager!!.sendCmd(info.mac,info.dev.dev_port,s)
        }

        override fun startListener() {
            super.startListener()
            KJDSDKManager.addCustomerListener(listenerApi, object : CustomerListener {
                override fun update(status: Any) {
                    if (status == "ok" && !getList){
                        getAreaDevInfo()
                    }else if (status is DevTypeStaInfo){
                        getView()?.updateDevStaUi(status)
                    }
                }
            })
        }

        override fun stopListener() {
            super.stopListener()
            KJDSDKManager.removeCustomerListener(listenerApi)
        }
    }
}