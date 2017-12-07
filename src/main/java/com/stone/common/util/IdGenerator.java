package com.stone.common.util;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * Created by 石头 on 2017/6/20.
 */
public class IdGenerator {

    private static SecureRandom secureRandom = new SecureRandom();

    public static String generateId_36(){
        return UUID.randomUUID().toString();
    }

    public static String generateId_16(){
        return Long.toHexString(secureRandom.nextLong());
    }

}
