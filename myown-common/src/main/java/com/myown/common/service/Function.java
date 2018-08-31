package com.myown.common.service;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/29 0029 17:58
 * @Description:
 */
public interface Function<T,E> {

    public T callback(E e);
}
