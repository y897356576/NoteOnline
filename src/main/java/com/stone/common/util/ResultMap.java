package com.stone.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 石头 on 2017/6/25.
 */
public class ResultMap {

    public static Map<String, Object> generateMap(final Boolean result){
        return new HashMap<String, Object>(){{
            put("result", result);
            put("message", "");
        }};
    }

    public static Map<String, Object> generateMap(final Boolean result, final String message){
        return new HashMap<String, Object>(){{
            put("result", result);
            put("message", message);
        }};
    }

}
