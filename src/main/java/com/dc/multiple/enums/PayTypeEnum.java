package com.dc.multiple.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public enum PayTypeEnum {
    MIANJIAN("MIANJIAN","满减"),
    DAZHE("DAZHE","打折"),
    FANLI("FANLI","返利"),
    JIFEN("JIFEN","积分"),
    ;


    private String code;

    private String desc;

    PayTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public PayTypeEnum of(String code) {
        return Arrays.stream(values()).filter(v -> Objects.equals(v.code, code)).findAny().orElse(null);
    }
}
