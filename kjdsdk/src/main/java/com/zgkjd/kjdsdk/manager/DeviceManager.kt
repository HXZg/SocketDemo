package com.zgkjd.kjdsdk.manager

import com.blankj.utilcode.util.LogUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zgkjd.basesocket.bean.response.BaseResponseStatusEntity
import com.zgkjd.kjdsdk.KJDSDKManager
import com.zgkjd.kjdsdk.api.jsonToMap
import com.zgkjd.kjdsdk.api.mapui
import com.zgkjd.kjdsdk.api.ui
import com.zgkjd.kjdsdk.bean.request.DeviceCmd
import com.zgkjd.kjdsdk.bean.response.AreaListInfo
import com.zgkjd.kjdsdk.bean.response.DevListInfo
import com.zgkjd.kjdsdk.bean.response.DevTypeStaInfo
import org.json.JSONObject

/**
 * @title com.zgkjd.kjdsdk.manager  SocketBase
 * @author xian_zhong  admin
 * @version 1.0
 * @Des DeviceManager  设备相关
 */
class DeviceManager {

    /**
     * device list 解析
     * {"dev_list":{"00158d0002257c02":[{"dev_id":"89215","dev_typ....
     */
    fun getAllDeviceList(action : (devices : Map<String,List<DevListInfo>>) -> Unit,fail : (msg : String) -> Unit){
        KJDSDKManager.reApi!!.getDevList("")
                .map {
                    it.jsonToMap("dev_list", object : TypeToken<List<DevListInfo>>() {},true)
                }
                .ui({
            /*val jsonObject = JSONObject(it.dataJson)
            val optJSONObject = jsonObject.optJSONObject("dev_list")
            val keys = optJSONObject.keys()
            val map = HashMap<String,List<DevListInfo>>()
            while (keys.hasNext()){
                val next = keys.next()
                val optJSONArray = optJSONObject.optJSONArray(next)
                val fromJson = Gson().fromJson<List<DevListInfo>>(optJSONArray.toString(), object : TypeToken<List<DevListInfo>>() {

                }.type)
                val list = map[next]
                if (list == null){
                    map[next] = arrayListOf()
                }
                (map[next] as ArrayList).addAll(fromJson)
            }*/
            action.invoke(it)
        },{type, msg ->
            fail.invoke(msg ?: "")
        })
    }

    fun getAllAreaList(action : (areas : List<AreaListInfo>) -> Unit,fail : (msg : String) -> Unit){
        KJDSDKManager.reApi!!.getAreaList("").mapui<List<AreaListInfo>>(object : TypeToken<List<AreaListInfo>>() {}.type,{
            LogUtils.i("${it.size}")
            action.invoke(it)
        },{type, msg ->
            fail.invoke(msg ?: "")
        })
    }

    fun getAllDevSta(action : (devices : Map<String,DevTypeStaInfo>) -> Unit,fail : (msg : String) -> Unit){
        KJDSDKManager.reApi!!.getAllDevSta("")
                .map {
                    it.jsonToMap("",object : TypeToken<DevTypeStaInfo>() {},false)
                }
                .ui({
                action.invoke(it)
        },{type, msg ->
            fail.invoke(msg ?: "")
        })
    }

    /**
     * 发送成功，服务器的推送
     * {"app":"smart","co":"kdj","api":"smart_set_dev_sta","data":{"dev_id":"1234","dev_type":"KG","sta":"00"}}
     */
    fun sendCmd(mac:String,port:String,sta:String){
        KJDSDKManager.reApi!!.sendCmd(DeviceCmd().createCOMMON(mac, port, sta))
    }
}