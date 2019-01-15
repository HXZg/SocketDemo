package com.xian_zhong.apt_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xian_zhong  admin
 * @version 1.0
 * @title com.xian_zhong.apt_annotation  SocketBase
 * @Des SocketObjParam 请求接口的参数，根据参数生成对象
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.PARAMETER)
public @interface SocketObjParam {
    String value();
}
