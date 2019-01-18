package com.zgkjd.kjdsdk.bean.response;

/**
 * @author xian_zhong  admin
 * @version 1.0
 * @title com.zgkjd.kjdsdk.bean.response  SocketBase
 * @Des DevTypeStaInfo
 */
public class DevTypeStaInfo {

    /**
     * dev_type : KG
     * sta : 00
     */

    private String dev_id;
    private String dev_type;
    private String sta;

    public void setDev_id(String dev_id) {
        this.dev_id = dev_id;
    }

    public String getDev_id() {
        return dev_id;
    }

    public String getDev_type() {
        return dev_type;
    }

    public void setDev_type(String dev_type) {
        this.dev_type = dev_type;
    }

    public String getSta() {
        return sta;
    }

    public void setSta(String sta) {
        this.sta = sta;
    }
}
