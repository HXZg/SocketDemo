package com.xian_zhong.apt_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xian_zhong  admin
 * @version 1.0
 * @title com.xian_zhong.apt_annotation  SocketBase
 * @Des SocketTimeParam 请求接口的参数，超时回复时间
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.PARAMETER)
public @interface SocketTimeParam {
}
