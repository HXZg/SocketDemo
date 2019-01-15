package com.zgkjd.basesocket.client

import android.util.Log
import com.zgkjd.basesocket.bean.response.BaseResponseStatusEntity
import io.reactivex.Observable

/**
 * Created by xian_zhong on 2019/1/11.
 * des : ${class}
 */
object RxKJDClient {

    fun requestApi(api:String,data:Any,timeout:Long = 0) : Observable<BaseResponseStatusEntity>{
        val create = Observable.create<BaseResponseStatusEntity> { it ->
            val a = it
            KJDClient.requestApi(api,data,timeout).addCall({
                a.onNext(it)
                a.onComplete()
            },{
                a.onError(it)
            })
        }
        return create
    }

}