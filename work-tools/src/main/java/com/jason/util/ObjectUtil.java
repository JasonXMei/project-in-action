package com.jason.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author Jason
 * @Date 2022/06/21
 */
@Slf4j
public class ObjectUtil {

    public static <T> Map<String, String> obj2Map(T obj) {
        if (Objects.isNull(obj)) {
            return Collections.emptyMap();
        }

        try {
            Class clazz = obj.getClass();

            Field[] fields = clazz.getDeclaredFields();
            Map<String, String> paramMap = new HashMap<>(fields.length);
            for (Field field : fields) {
                field.setAccessible(true);

                Object value = field.get(obj);
                if (Objects.isNull(value)) {
                    continue;
                }

                String name = field.getName();
                paramMap.put(name, value.toString());
            }

            return paramMap;
        } catch (IllegalAccessException e) {
            log.error("obj to map failed", e);
            return Collections.emptyMap();
        }
    }

}
