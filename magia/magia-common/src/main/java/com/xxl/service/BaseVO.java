package com.xxl.service;


import java.io.Serializable;


public interface BaseVO extends Serializable {
    /**
     * 获取Key
     *
     * @return 返回key
     * @see
     */
    public abstract String getKey();
}
