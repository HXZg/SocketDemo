package com.zgkjd.kjdsdk.api

import android.util.Log
import com.blankj.utilcode.util.LogUtils
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.zgkjd.basesocket.bean.response.BaseResponseStatusEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.lang.reflect.Type

/**
 * Created by xian_zhong on 2019/1/11.
 * des : rxjava 线程切换 map 转换解析实体类
 */
fun<T> Observable<T>.ui(action: (t : T) -> Unit, error: (type:Int,msg: String?) -> Unit = { i: Int, s: String? -> }) =
        subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(Consumer {
            action.invoke(it)
        }, object : ExceptionEngine() {
            override fun handlerMsg(type: Int, msg: String?) {
                error.invoke(type,msg)
            }
        })

fun<T> Observable<BaseResponseStatusEntity>.mapui(clz : Class<T>,action: (t : T) -> Unit, error: (type:Int,msg: String?) -> Unit = { i: Int, s: String? -> }) =
        map {
            LogUtils.i(it.dataJson)
            Gson().fromJson(it.dataJson,clz)
        }
        .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer {
                    LogUtils.i(it)
                    action.invoke(it)
                }, object : ExceptionEngine() {
                    override fun handlerMsg(type: Int, msg: String?) {
                        LogUtils.i(msg)
                        error.invoke(type,msg)
                    }
                })

fun<T> Observable<BaseResponseStatusEntity>.mapui(type : Type,action: (t : T) -> Unit, error: (type:Int,msg: String?) -> Unit = { i: Int, s: String? -> }) =
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                        .map {
                            val jsonObject = JSONObject(it.rawJsonData)
                            val optJSONArray = jsonObject.optJSONArray("data")
    Gson().fromJson<T>(optJSONArray.toString(),type)
}
                .subscribe(Consumer {
                    action.invoke(it)
                }, object : ExceptionEngine() {
                    override fun handlerMsg(type: Int, msg: String?) {
                        error.invoke(type,msg)
                    }
                })

inline fun<reified T> BaseResponseStatusEntity.jsonToMap(data : String = "",type:TypeToken<T>,array:Boolean) : Map<String,T>{
    val map = HashMap<String,T>()
    val jsonObject = JSONObject(rawJsonData)
    val optJSONObject = jsonObject.optJSONObject("data")
    val souceJsonObject : JSONObject
    if (optJSONObject != null){
        souceJsonObject = if (data.isNotEmpty()) optJSONObject.optJSONObject(data) else optJSONObject
        val keys = souceJsonObject.keys()
        while (keys.hasNext()){
            val next = keys.next()
            val s = if (array) souceJsonObject.optJSONArray(next).toString() else souceJsonObject.optJSONObject(next).toString()
            val fromJson = Gson().fromJson<T>(s, type.type)
            map[next] = fromJson
        }
    }
    return map
}