package com.dc.multiple.utils;

public class IdUtil {

    public IdUtil() {
    }

    public static Long generateId() {
        return SnowFlakeInstance.getInstance().nextId();
    }
}
