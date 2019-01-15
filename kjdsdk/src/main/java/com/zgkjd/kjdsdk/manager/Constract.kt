package com.zgkjd.kjdsdk.manager

/**
 * @title com.zgkjd.kjdsdk.manager  SocketBase
 * @author xian_zhong  admin
 * @version 1.0
 * @Des Constract  socket api 常量管理
 */
object Constract {

    const val CACHE_FILE_NAME = "kjd_cache"  //缓存文件名

    const val UPD_DEVICE_LIST = "ver_dev_list"  //设备列表
    const val UPD_CAMERA_LIST = "ver_cam_list"  //摄像头列表
    const val UPD_SCENE_LIST = "ver_scene_list" //情景列表
    const val UPD_CRON_LIST = "ver_cron_list"  //定时列表
    const val UPD_AREA_LIST = "ver_area_list"  //区域列表
    const val GATEWAY_INLINE = "is_online"     //网关是否在线
    const val REGISTER_PWD = "register_pwd"    //注册成功后的密码
    const val LOGIN_USER_INFO = "login_info"    //登录后的用户信息

    const val CHANGE_RECEIVE_LIST = "app_push_data_ver"
    const val CHANGE_RECEIVE_ONLINE = "app_push_gw_is_online"

}