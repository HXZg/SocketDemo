package com.zgkjd.kjdsdk

import android.annotation.SuppressLint
import android.telecom.GatewayInfo
import com.blankj.utilcode.util.*
import com.google.gson.Gson
import com.zgkjd.basesocket.client.KJDClient
import com.zgkjd.kjdsdk.api.Api
import com.zgkjd.kjdsdk.api.KJDApi
import com.zgkjd.kjdsdk.bean.response.DevTypeStaInfo
import com.zgkjd.kjdsdk.bean.response.GateWayInfo
import com.zgkjd.kjdsdk.cache.KJDLruCache
import com.zgkjd.kjdsdk.manager.AuthManager
import com.zgkjd.kjdsdk.manager.Constract
import com.zgkjd.kjdsdk.manager.DeviceManager
import com.zgkjd.kjdsdk.received.CustomerListener
import com.zgkjd.kjdsdk.received.ListChangReceived
import com.zgkjd.kjdsdk.received.ListStatusUpdate
import org.json.JSONObject

/**
 * Created by xian_zhong on 2019/1/11.
 * des : ${class}
 */
object KJDSDKManager {

    var mAuthManager : AuthManager? = null
    private set
    get() {
        if (field == null){
            field = AuthManager()
        }
        return field
    }

    var mDeviceManager : DeviceManager? = null
        private set
        get() {
            if (field == null){
                field = DeviceManager()
            }
            return field
        }

    var api : KJDApi? = null
    private set
    get() {
        if (field == null){
            field = KJDApi()
        }
        return field
    }

    var reApi : Api? = null
    private set
    get() {
        if (field == null){
            field = setApi(Api::class.java)
        }
        return field
    }

    var mGWStatus = "no"

    private val mCustomerMap = HashMap<String,CustomerListener>()

    private val mChangeReceive = ListChangReceived()

    fun init(builder: KJDSDKBuild){
        KJDClient.mKS = builder.build()
        setServiceListener()
    }

    fun init(){
        KJDClient.mKS = KJDSDKBuild.getKJDSocket{
            initSuc()
        }
        setServiceListener()  //监听状态更新
    }

    fun<T> setApi(clz : Class<T>) : T?{
        val forName = Class.forName(clz.name + "Impl")
        val newInstance = forName.newInstance()
        return newInstance as T
    }

    private fun initSuc(){
        if (PermissionUtils.isGranted(android.Manifest.permission.READ_PHONE_STATE)){
            mAuthManager?.register(getUUID())
        }else{
            KJDClient.mKS!!.getHandler().postDelayed({
                initSuc()
            },1000)
        }
    }

    fun getHandler() = KJDClient.mKS!!.getHandler()

    fun addCustomerListener(api:Array<String>,listener:CustomerListener){
        if (api.isNotEmpty()){
            for (i in api){
                mCustomerMap[i] = listener
            }
        }
    }

    fun removeCustomerListener(api: Array<String>){
        if (api.isNotEmpty()){
            for (i in api){
                mCustomerMap.remove(i)
            }
        }
    }

    fun getCustomerListener(api: String) = mCustomerMap[api]

    /**
     * 增加单个列表状态的更新回调
     */
    fun addStatusListener(status : String,listener : ListStatusUpdate.SingleStatusUpdate){
        mChangeReceive.addStatusListener(status,listener)
    }

    /**
     * 取消某个列表状态的回调
     * 不传，则全部取消
     */
    fun removeStatusListener(status: String = ""){
        mChangeReceive.removeStatusListener(status)
    }

    /**
     * 增加所有列表状态的更新回调
     * 设置null ，取消回调
     */
    fun setAllListListener(listener : ListStatusUpdate?){
        mChangeReceive.mAllListener = listener
    }

    /**
     * 服务器主动推送过来的消息
     */
    private fun setServiceListener(){
        KJDClient.listener = {
            when(it.api){
                Constract.CHANGE_RECEIVE_LIST -> {
                    mChangeReceive.handlerUpdateList(Gson().fromJson(it.dataJson,GateWayInfo::class.java).data_ver)
                }  //列表有更新
                Constract.CHANGE_RECEIVE_ONLINE -> {
                    setGWOnline(it.dataJson)
                }  //网关是否在线
                Constract.UPDATE_DEV_STA -> {
                    val fromJson = GsonUtils.fromJson(it.dataJson, DevTypeStaInfo::class.java)
                    mCustomerMap[Constract.UPDATE_DEV_STA]?.update(fromJson)
                }
            }
        }
    }

    /**
     * 设置网关是否在线
     */
    private fun setGWOnline(s : String){
        val jsonObject = JSONObject(s)
        val online = jsonObject.optString("is_online")
        KJDLruCache.mIsOnLine = online.toInt()
    }

    @SuppressLint("MissingPermission")
    private fun getUUID() : String{
        /*val tmDevice = PhoneUtils.getDeviceId()
        val tmSerial = PhoneUtils.getSerial()
        val androidId = DeviceUtils.getAndroidID()
        return UUID(androidId.hashCode().toLong(), tmDevice.hashCode().toLong() shl 32 or tmSerial.hashCode().toLong()).toString()*/
        return "ffffffff-c966-6202-ec3b-31310033c587"
    }
}
