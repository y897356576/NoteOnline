package com.stone.demo.ObjectMapTransform;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cglib.beans.BeanMap;

import java.io.IOException;
import java.util.Map;

/**
 * Created by admin on 2018/2/23.
 */
public class ObjectMapTransform {

    private static final Map objToMap(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(objectMapper.writeValueAsString(obj), Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static final Object mapToObj(Map<String, Object> map, Class clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(objectMapper.writeValueAsString(map), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static final Map objToMapBySpring(Object obj) {
        if (obj == null) {
            return null;
        }
        return BeanMap.create(obj);
    }


}
