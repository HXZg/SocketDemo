package com.zgkjd.kjdsdk.bean.request;

/**
 * Created by xian_zhong on 2019/1/11.
 * des : ${class}
 */

public class RegisterBean {


  /**
   * uuid : 123456789013
   * user_name : 小王
   * pwd : 12345678901234567890123456789012
   * timestamp : 1234567890
   */

  private String uuid;
  private String user_name;
  private String pwd;
  private String timestamp;

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getUser_name() {
    return user_name;
  }

  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }
}
