package com.jason;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * 扩容期间取出的值不准确
 * @Author Jason
 * @Date 2023-05-12
 */
public class HashMapNotSafe {

    public static void main(String[] args) {
        final Map<Integer, String> map = new HashMap<>();

        final Integer targetKey = 0b1111_1111_1111_1111; // 65 535
        final String targetValue = "v";
        map.put(targetKey, targetValue);

        new Thread(() -> {
            IntStream.range(0, targetKey).forEach(key -> map.put(key, "someValue"));
        }).start();

        while (true) {
            if (null == map.get(targetKey)) {
                throw new RuntimeException("HashMap is not thread safe.");
            }
        }
    }

}
