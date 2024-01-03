package com.jason.util;

import cn.hutool.core.collection.CollectionUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionUtils {

    /**
     * 集合转 map
     * @param from 集合
     * @param keyFunc map key
     * @return
     * @param <K>
     * @param <V>
     */
    public static <K, V> Map<K, V> convertMap(Collection<V> from, Function<V, K> keyFunc) {
        if (CollectionUtil.isEmpty(from)) {
            return Collections.emptyMap();
        }

        return from.stream().collect(Collectors.toMap(keyFunc, Function.identity()));
    }

}
