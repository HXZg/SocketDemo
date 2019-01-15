package com.zgkjd.basesocket.exception

import com.zgkjd.basesocket.bean.response.BaseResponseStatusEntity

/**
 * Created by xian_zhong on 2019/1/11.
 * des : ${class}
 */
class ServiceReturnException(val bean : BaseResponseStatusEntity) : IllegalStateException(bean.retmsg) {

}