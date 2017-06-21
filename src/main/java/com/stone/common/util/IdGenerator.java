package com.stone.common.util;

import java.util.UUID;

/**
 * Created by 石头 on 2017/6/20.
 */
public class IdGenerator {

    public static String generateId(){
        return UUID.randomUUID().toString();
    }

}
