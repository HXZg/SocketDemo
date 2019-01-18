package com.zgkjd.kjdsdk.api;

import com.xian_zhong.apt_annotation.SocketApi;
import com.xian_zhong.apt_annotation.SocketObjParam;
import com.zgkjd.basesocket.bean.response.BaseResponseStatusEntity;
import com.zgkjd.basesocket.client.Callable;

import io.reactivex.Observable;

/**
 * @author xian_zhong  admin
 * @version 1.0
 * @title com.zgkjd.kjdsdk.api  SocketBase
 * @Des Api
 */
public interface Api {

    @SocketApi("test")
    Callable test(@SocketObjParam("sn") String sn);

    @SocketApi("app_set_sn")
    Observable<BaseResponseStatusEntity> bindGateway(@SocketObjParam("sn") String sn);

    @SocketApi("app_get_area_list")
    Observable<BaseResponseStatusEntity> getAreaList(String s);

    @SocketApi("app_get_dev_list")
    Observable<BaseResponseStatusEntity> getDevList(String s);

    @SocketApi("app_get_all_dev_sta")
    Observable<BaseResponseStatusEntity> getAllDevSta(String s);

    @SocketApi("app_dev_cmd")
    Observable<BaseResponseStatusEntity> sendCmd(@SocketObjParam("cmd") String cmd);
}
