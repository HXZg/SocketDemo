package com.xian_zhong.apt_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xian_zhong  admin
 * @version 1.0
 * @title com.xian_zhong.apt_annotation  SocketBase
 * @Des SocketApi  请求接口的方法，用于自动生成方法
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface SocketApi {
    String value();
}
