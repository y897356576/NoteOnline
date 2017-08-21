package com.stone.common.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
 * Created by 石头 on 2017/6/25.
 */
public class EhcacheUtil {

    private static CacheManager cacheManager;

    public static Cache getCache(String cacheName) {
        if (cacheManager == null) {
            cacheManager = CacheManager.create();
        }
        return cacheManager.getCache(cacheName);
    }

    public static void shutdown() {
        if (cacheManager != null) {
            cacheManager.shutdown();
        }
    }

}
