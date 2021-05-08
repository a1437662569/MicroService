package com.xxl.config;

import lombok.Getter;

@Getter
public enum DBTypeEnum {

    MASTER("master"), READ("read");
    private String value;

    DBTypeEnum(String value) {
        this.value = value;
    }

}

