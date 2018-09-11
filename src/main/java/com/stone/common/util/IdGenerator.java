package com.stone.common.util;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * Created by 石头 on 2017/6/20.
 */
public class IdGenerator {

    private static SecureRandom secureRandom = new SecureRandom();

    private static Random random = new Random(47);

    public static String generateId_36() {
        return UUID.randomUUID().toString();
    }

    public static String generateId_16() {
        return Long.toHexString(secureRandom.nextLong());
    }

    public static String generateId_16_num() {
        int a = random.nextInt(89999999) + 10000000;
        int b = random.nextInt(99999999);
        long c = a * 100000000L + b;
        return String.valueOf(c);
    }

}
