package com.zgkjd.kjdsdk.cache

import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.Utils
import com.google.gson.Gson
import com.zgkjd.kjdsdk.bean.response.GateWayInfo
import com.zgkjd.kjdsdk.bean.response.LoginInfo
import com.zgkjd.kjdsdk.manager.Constract
import org.json.JSONObject

/**
 * @title com.zgkjd.kjdsdk.cache  SocketBase
 * @author xian_zhong  admin
 * @version 1.0
 * @Des 存储内存中的数据
 *
 *  * 主动推送：
 * {"data_ver":{"ver_dev_list":"1527057134","ver_area_list":"1508483887","ver_cam_list":"1525601738",
 * "ver_scene_list":"1526021650","ver_cron_list":"1510728991"}}  五个列表
 * {"is_online":"1"}  网关是否在线
 * 登录注册：
 * {"pwd":"12345678901234567890123456789012"}
 * {"sn_list":[{"sn":"e9d92162015a","level":"1","smart_name":"\u9ed1\u8272\u7f51\u5173\u6d4b\u8bd5","gw_version":"20180305"}],
 * "user_info":{"user_id":"1564","name":"\u7528\u6237_c587","imgurl":""}}
 */
object KJDLruCache {

    /**
     * 网关是否在线  1:在线
     */
    var mIsOnLine = -1

    var mRegisterPwd = ""  //注册成功服务端返回的密码,为null则未注册过
    set(value) {
        field = value
        mCache!!.put(Constract.REGISTER_PWD,value)
    }

    /**
     * 登录的用户信息
     */
    private var mUserBean : LoginInfo.UserInfoBean? = null

    private var mGateWaySn = ""   //上一次绑定的网关序列号

    private var mCache : KJDCache? = null
    get() {
        if (field == null){
            field = KJDCache.get(Utils.getApp(),Constract.CACHE_FILE_NAME)
        }
        return field
    }

    /**
     * 存储列表的version ，用于判断列表是否发生变化
     * k ：常量 V : version
     */
    private val mListMap = HashMap<String,String>()

    /**
     * 存储各个列表，k ：常量 V : List's json data
     */
    private val mListData = HashMap<String,JSONObject>()

    fun putListMap(key : String,value : String){
        mListMap[key] = value
        mCache!!.put(key,value)
    }

    fun getListMap(key: String) : String{
        if (mListMap[key] == null){
            val asString = mCache!!.getAsString(key) ?: return ""
            mListMap[key] = asString
        }
        return mListMap[key]!!
    }

    fun cacheListMap(bean : GateWayInfo.DataVerBean){
        putListMap(Constract.UPD_DEVICE_LIST,bean.ver_dev_list ?: "")
        putListMap(Constract.UPD_AREA_LIST,bean.ver_area_list ?: "")
        putListMap(Constract.UPD_CAMERA_LIST,bean.ver_cam_list ?: "")
        putListMap(Constract.UPD_CRON_LIST,bean.ver_cron_list ?: "")
        putListMap(Constract.UPD_SCENE_LIST,bean.ver_scene_list ?: "")
    }

    fun putListData(key: String,value: JSONObject){
        mListData[key] = value
    }

    private fun getListData(key: String) = mListData[key]

    fun setGateWaySn(sn:String){

    }

    fun getGateWaySn() : String = mGateWaySn

    /**
     * 是否注册过
     * register_pwd not is null
     */
    fun isRegisted() : Boolean{
        mRegisterPwd = mCache!!.getAsString(Constract.REGISTER_PWD) ?: ""
        return mRegisterPwd.isNotEmpty()
    }

    /**
     * 保存登录的用户信息
     */
    fun saveUserInfo(bean : LoginInfo.UserInfoBean){
        mUserBean = bean
        mCache!!.put(Constract.LOGIN_USER_INFO,Gson().toJson(bean))
    }

    /**
     * 得到登录用户信息
     */
    fun getUserInfo() : LoginInfo.UserInfoBean?{
        if (mUserBean == null){
            mUserBean = Gson().fromJson(mCache!!.getAsString(Constract.LOGIN_USER_INFO)  ?: "",LoginInfo.UserInfoBean::class.java)
        }
        return mUserBean
    }
}