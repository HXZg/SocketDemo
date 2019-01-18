package com.zgkjd.kjdsdk.api

import com.xian_zhong.apt_annotation.SocketApi
import com.xian_zhong.apt_annotation.SocketObjParam
import com.zgkjd.basesocket.bean.response.BaseResponseStatusEntity
import com.zgkjd.basesocket.client.Callable
import com.zgkjd.basesocket.client.KJDClient
import com.zgkjd.basesocket.client.RxKJDClient
import com.zgkjd.kjdsdk.bean.request.RegisterBean
import io.reactivex.Observable

/**
 * Created by xian_zhong on 2019/1/11.
 * des : ${class}
 */
class KJDApi {

    fun register(bean : RegisterBean) : Observable<BaseResponseStatusEntity> = RxKJDClient.requestApi("app_regist_user",bean)

    fun login(bean : RegisterBean) : Callable = KJDClient.requestApi("app_client_connect",bean)

}