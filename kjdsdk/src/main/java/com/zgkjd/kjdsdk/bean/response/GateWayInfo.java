package com.zgkjd.kjdsdk.bean.response;

/**
 * @author xian_zhong  admin
 * @version 1.0
 * @title com.zgkjd.kjdsdk.bean.response  SocketBase
 * @Des GateWayInfo
 */
public class GateWayInfo {


    /**
     * sn : 22718aab7156
     * is_online : 0
     * data_ver : {"ver_dev_list":"1547534617","ver_area_list":"1547446563","ver_scene_list":"1547537571"}
     */

    private String sn;
    private String is_online;
    private DataVerBean data_ver;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getIs_online() {
        return is_online;
    }

    public void setIs_online(String is_online) {
        this.is_online = is_online;
    }

    public DataVerBean getData_ver() {
        return data_ver;
    }

    public void setData_ver(DataVerBean data_ver) {
        this.data_ver = data_ver;
    }

    public static class DataVerBean {
        /**
         * ver_dev_list : 1547534617
         * ver_area_list : 1547446563
         * ver_scene_list : 1547537571
         */

        private String ver_dev_list;
        private String ver_area_list;
        private String ver_cam_list;
        private String ver_scene_list;
        private String ver_cron_list;

        public void setVer_cam_list(String ver_cam_list) {
            this.ver_cam_list = ver_cam_list;
        }

        public String getVer_cam_list() {
            return ver_cam_list;
        }

        public void setVer_cron_list(String ver_cron_list) {
            this.ver_cron_list = ver_cron_list;
        }

        public String getVer_cron_list() {
            return ver_cron_list;
        }

        public String getVer_dev_list() {
            return ver_dev_list;
        }

        public void setVer_dev_list(String ver_dev_list) {
            this.ver_dev_list = ver_dev_list;
        }

        public String getVer_area_list() {
            return ver_area_list;
        }

        public void setVer_area_list(String ver_area_list) {
            this.ver_area_list = ver_area_list;
        }

        public String getVer_scene_list() {
            return ver_scene_list;
        }

        public void setVer_scene_list(String ver_scene_list) {
            this.ver_scene_list = ver_scene_list;
        }
    }
}
