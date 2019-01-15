package com.zgkjd.basesocket.exception

import java.util.concurrent.TimeoutException

/**
 * Created by xian_zhong on 2019/1/11.
 * des : ${class}
 */
class SocketTimeOutException(msg:String) : TimeoutException(msg) {
}