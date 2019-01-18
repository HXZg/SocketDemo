package com.zgkjd.kjdsdk.bean.request

import com.blankj.utilcode.util.StringUtils
import com.zgkjd.kjdsdk.bean.response.DevListInfo

/**
 * @title com.zgkjd.kjdsdk.bean.request  SocketBase
 * @author xian_zhong  admin
 * @version 1.0
 * @Des DeviceCmd
 */
class DeviceCmd {

    private val TEMPLET_COMMON = "011af10102[dev_mac]0320[dev_port]0400[len][dev_sta]"
    private val TEMPLET_IR_REMOTE = "011af10102[dev_mac]0320[dev_port]040014[sta][dev_id][IRid][key]"
    private val TEMPLET_LOCK = "011af10102[mac]032001040012[dev_sta][len][timestamp][password]"
    private val TEMPLET_AIRCONDITIONER = "011af10102[dev_mac]0320[dev_port]040011[ac_type][dev_id]3001[IRid][dev_sta]"
    private val TEMPLET_THERMOSTAT = "011af10102[dev_mac]0320[dev_port]040004[dev_sta]0808"

    fun createCOMMON(mac : String,port : String,sta : String) : String{
        return TEMPLET_COMMON.replace("[dev_mac]",mac)
                .replace("[dev_port]",port)
                .replace("[dev_sta]",sta)
                .replace("[len]",getStateLength(sta))
    }

    fun getStateLength(state: String): String {
        val length = state.length / 2
        val hex = Integer.toHexString(length)
        return com.zgkjd.kjdsdk.cache.StringUtils.leftPad(hex, 2, '0')
    }

}