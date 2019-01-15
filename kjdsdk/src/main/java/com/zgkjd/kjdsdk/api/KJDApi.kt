package com.zgkjd.kjdsdk.api

import com.zgkjd.basesocket.client.KJDClient
import com.zgkjd.basesocket.client.RxKJDClient
import com.zgkjd.kjdsdk.bean.request.RegisterBean

/**
 * Created by xian_zhong on 2019/1/11.
 * des : ${class}
 */
internal object KJDApi {

    fun register(bean : RegisterBean) = RxKJDClient.requestApi("app_regist_user",bean)

    fun login(bean : RegisterBean) = KJDClient.requestApi("app_client_connect",bean)

}