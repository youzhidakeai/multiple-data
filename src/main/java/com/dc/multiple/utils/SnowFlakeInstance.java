package com.dc.multiple.utils;

import xyz.downgoon.snowflake.Snowflake;

import java.util.Random;

public class SnowFlakeInstance extends Snowflake {

    private static volatile SnowFlakeInstance instance;

    public SnowFlakeInstance(long datacenterId, long workerId) {
        super(datacenterId, workerId);
    }

    public static SnowFlakeInstance getInstance() {
        if (instance == null) {
            Class<SnowFlakeInstance> var0 = SnowFlakeInstance.class;
            synchronized(SnowFlakeInstance.class) {
                if (instance == null) {
                    instance = new SnowFlakeInstance((long)(new Random()).nextInt(31), (long)(new Random()).nextInt(31));
                }
            }
        }

        return instance;
    }
}
