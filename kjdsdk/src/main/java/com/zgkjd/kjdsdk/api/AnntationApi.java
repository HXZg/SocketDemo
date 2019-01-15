package com.zgkjd.kjdsdk.api;

import com.zgkjd.basesocket.bean.response.BaseResponseStatusEntity;
import com.zgkjd.basesocket.client.Callable;
import com.zgkjd.kjdsdk.bean.request.RegisterBean;
import com.xian_zhong.apt_annotation.SocketApi;
import com.xian_zhong.apt_annotation.SocketTimeParam;
import com.xian_zhong.apt_annotation.SocketObjParam;

import io.reactivex.Observable;

/**
 * @author xian_zhong  admin
 * @version 1.0
 * @title com.zgkjd.kjdsdk.api  SocketBase
 * @Des AnntationApi
 */
public interface AnntationApi {

    @SocketApi("register")
    Callable register(RegisterBean bean,@SocketTimeParam Long time);

    @SocketApi("login")
    Observable<BaseResponseStatusEntity> login(RegisterBean bean);


    @SocketApi("test")
    Observable<BaseResponseStatusEntity> test(@SocketTimeParam Long time,@SocketObjParam("sn") String sn,
                                              @SocketObjParam("test") String test);
}
