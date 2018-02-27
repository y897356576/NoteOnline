package com.stone.common.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 石头 on 2018/2/27.
 */
public enum DataStatus {
    启用("1"),
    禁用("0");

    private String code;
    public String getCode() {
        return code;
    }

    DataStatus(String code) {
        this.code = code;
    }

    private static Map<String, DataStatus> enumMap = new HashMap<>();
    static {
        for (DataStatus status : DataStatus.values()) {
            enumMap.put(status.getCode(), status);
        }
    }

    public static DataStatus getEnum(String code) {
        return enumMap.get(code);
    }
}
