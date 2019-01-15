package com.zgkjd.basesocket.bean.request

/**
 * Created by xian_zhong on 2019/1/10.
 * des : 基本请求体
 */
data class BaseRequestEntity<T>(val app : String,val co : String ,val api : String,val data : T) {

}