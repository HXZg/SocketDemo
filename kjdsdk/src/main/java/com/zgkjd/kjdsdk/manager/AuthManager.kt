package com.zgkjd.kjdsdk.manager

import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.LogUtils
import com.google.gson.Gson
import com.zgkjd.kjdsdk.KJDSDKManager
import com.zgkjd.kjdsdk.api.mapui
import com.zgkjd.kjdsdk.api.ui
import com.zgkjd.kjdsdk.bean.request.RegisterBean
import com.zgkjd.kjdsdk.bean.response.GateWayInfo
import com.zgkjd.kjdsdk.bean.response.LoginInfo
import com.zgkjd.kjdsdk.bean.response.RegisterInfo
import com.zgkjd.kjdsdk.cache.KJDLruCache
import java.util.*

/**
 *
 * manager ： 管理调用的接口
 * Created by xian_zhong on 2019/1/11.
 * des : 权限管理  ： api : 注册，登录
 */
class AuthManager {

    val MD5_SALT_REGISTER = "kdkdkdk8394y5fnwoejfs93u49tjf,.3049jrfjslfjsdfs"
    val MD5_SALT_LOGIN = "ilsdkjfsldfjlwfioejfwkfsdjvihsoverklnrf34u392485u9rjsoidfnj3948uthgrf"

    fun register(phoneId:String){
        if (KJDLruCache.isRegisted()){
            login(phoneId,KJDLruCache.mRegisterPwd)
            return
        }
        val registerBean = RegisterBean()
        val contains = Locale.getDefault().country.contains("CN")
        registerBean.user_name = "${if (contains) "用户" else "User"}_${phoneId.substring(phoneId.length - 4)}"
        registerBean.timestamp = System.currentTimeMillis().toString()
        registerBean.uuid = phoneId
        registerBean.pwd = EncryptUtils.encryptMD5ToString(MD5_SALT_REGISTER + phoneId + registerBean.timestamp).toLowerCase()
        KJDSDKManager.api!!.register(registerBean).ui({
            val bean = Gson().fromJson<RegisterInfo>(it.dataJson, RegisterInfo::class.java)
            KJDLruCache.mRegisterPwd = bean.pwd
            login(phoneId,bean.pwd)
        },{ type, msg -> LogUtils.i("socket_test_text",msg) })
    }

    fun login(phoneId: String,pwd:String){
        val registerBean = RegisterBean()
        registerBean.timestamp = System.currentTimeMillis().toString()
        registerBean.uuid = phoneId
        registerBean.pwd = EncryptUtils.encryptMD5ToString(MD5_SALT_LOGIN + pwd + registerBean.timestamp).toLowerCase()
        KJDSDKManager.api!!.login(registerBean).addCall({
            val bean = Gson().fromJson<LoginInfo>(it.dataJson, LoginInfo::class.java)
            KJDLruCache.saveUserInfo(bean.user_info)
            if (bean.sn_list.isEmpty()){
                KJDSDKManager.mGWStatus = "no"
                KJDSDKManager.getCustomerListener(Constract.BIND_GATEWAY_SUC)?.update("no_gateway")
            }
            KJDSDKManager.getHandler().postDelayed({
                bindGateWay(bean.sn_list[2].sn)
            },100)
        },{
            LogUtils.i("auth_manager",it.message)
        })
    }

    fun bindGateWay(sn:String){
        KJDSDKManager.reApi!!.bindGateway(sn).mapui(GateWayInfo::class.java,{
            KJDLruCache.cacheListMap(it.data_ver)
            KJDLruCache.mIsOnLine = it.is_online.toInt()
            KJDSDKManager.mGWStatus = "ok"
            LogUtils.i("bind_status",KJDSDKManager.mGWStatus)
            KJDSDKManager.getCustomerListener(Constract.BIND_GATEWAY_SUC)?.update("ok")
        },{type, msg ->  })
    }

}