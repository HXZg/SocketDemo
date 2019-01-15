package com.zgkjd.kjdsdk.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zgkjd.basesocket.bean.response.BaseResponseStatusEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.lang.reflect.Type

/**
 * Created by xian_zhong on 2019/1/11.
 * des : rxjava 线程切换 map 转换解析实体类
 */
fun Observable<BaseResponseStatusEntity>.ui(action: (t : BaseResponseStatusEntity) -> Unit, error: (type:Int,msg: String?) -> Unit = { i: Int, s: String? -> }) =
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
            Gson().fromJson(it.dataJson,clz)
        }
        .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer {
                    action.invoke(it)
                }, object : ExceptionEngine() {
                    override fun handlerMsg(type: Int, msg: String?) {
                        error.invoke(type,msg)
                    }
                })

fun<T> Observable<BaseResponseStatusEntity>.mapui(type : Type,action: (t : T) -> Unit, error: (type:Int,msg: String?) -> Unit = { i: Int, s: String? -> }) =
        map {
            Gson().fromJson<T>(it.dataJson,type)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer {
                    action.invoke(it)
                }, object : ExceptionEngine() {
                    override fun handlerMsg(type: Int, msg: String?) {
                        error.invoke(type,msg)
                    }
                })