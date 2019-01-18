package com.zgkjd.kjdsdk.api

import com.xian_zhong.apt_annotation.SocketApi

/**
 * @title com.zgkjd.kjdsdk.api  SocketBase
 * @author xian_zhong  admin
 * @version 1.0
 * @Des KotlinApi
 */
interface KotlinApi {

    @SocketApi("register")
    fun register(any : String)
}