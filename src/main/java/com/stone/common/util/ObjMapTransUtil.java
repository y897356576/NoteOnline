package com.stone.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * Created by admin on 2018/2/23.
 */
public class ObjMapTransUtil {

    public static final Map objToMap(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(objectMapper.writeValueAsString(obj), Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static final Map objToStringMap(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> map = objectMapper.readValue(objectMapper.writeValueAsString(obj), Map.class);
            if (map != null) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    map.put(entry.getKey(), entry.getValue() == null ? "":entry.getValue().toString());
                }
            }
            return map;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static final Object mapToObj(Map<String, Object> map, Class clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (map == null || map.size() == 0) {
                return clazz.newInstance();
            }
            for (Map.Entry entry : map.entrySet()) {
                if (entry.getValue() == null) {
                    map.remove(entry.getKey());
                }
            }

            return objectMapper.readValue(objectMapper.writeValueAsString(map), clazz);
        } catch (IOException | IllegalAccessException | InstantiationException  e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
