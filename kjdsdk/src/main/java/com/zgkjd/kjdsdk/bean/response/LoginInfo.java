package com.zgkjd.kjdsdk.bean.response;

import java.util.List;

/**
 * Created by xian_zhong on 2019/1/11.
 * des : ${class}
 */

public class LoginInfo {

  /**
   * sn_list : [{"sn":"","level":"","smart_name":""},{"sn":"","level":"","smart_name":""}]
   * user_info : {"user_id":"1","name":"aaaa","imgurl":""}
   */

  private UserInfoBean user_info;
  private List<SnListBean> sn_list;

  public UserInfoBean getUser_info() {
    return user_info;
  }

  public void setUser_info(UserInfoBean user_info) {
    this.user_info = user_info;
  }

  public List<SnListBean> getSn_list() {
    return sn_list;
  }

  public void setSn_list(List<SnListBean> sn_list) {
    this.sn_list = sn_list;
  }

  public static class UserInfoBean {
    /**
     * user_id : 1
     * name : aaaa
     * imgurl :
     */

    private String user_id;
    private String name;
    private String imgurl;

    public String getUser_id() {
      return user_id;
    }

    public void setUser_id(String user_id) {
      this.user_id = user_id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getImgurl() {
      return imgurl;
    }

    public void setImgurl(String imgurl) {
      this.imgurl = imgurl;
    }
  }

  public static class SnListBean {
    /**
     * sn :
     * level :
     * smart_name :
     */

    private String sn;
    private String level;
    private String smart_name = "未设置";

    public String getSn() {
      return sn;
    }

    public void setSn(String sn) {
      this.sn = sn;
    }

    public String getLevel() {
      return level;
    }

    public void setLevel(String level) {
      this.level = level;
    }

    public String getSmart_name() {
      return smart_name;
    }

    public void setSmart_name(String smart_name) {
      this.smart_name = smart_name;
    }
  }
}
