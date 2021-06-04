package com.mlk.web.home.manager.utils;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 *
 * @author malikai
 * @date 2021-6-4 14:15
 */
public final class EmptyUtils {
    private EmptyUtils() {
    }

    public static <T> boolean isEmpty(Collection<T> coll) {
        return coll == null || coll.isEmpty();
    }

    public static <T> boolean isNotEmpty(Collection<T> coll) {
        return !isEmpty(coll);
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    public static <K, V> boolean isNotEmpty(Map<K, V> map) {
        return !isEmpty(map);
    }

    public static <T> boolean isEmpty(T t) {
        return t == null || !StringUtils.hasText(t.toString());
    }

    public static <T> boolean isNotEmpty(T[] datas) {
        return !isEmpty(datas);
    }

    public static <T> boolean isEmpty(T[] datas) {
        return ObjectUtils.isEmpty(datas);
    }

    public static <T> boolean isNotEmpty(T t) {
        return !isEmpty(t);
    }


    public static <K, V> boolean hasEmpty(Map... datas) {
        Map[] var1 = datas;
        int var2 = datas.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Map<K, V> data = var1[var3];
            if (isEmpty(data)) {
                return true;
            }
        }

        return false;
    }

    public static <T> boolean hasEmpty(Collection... datas) {
        Collection[] var1 = datas;
        int var2 = datas.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Collection<T> data = var1[var3];
            if (isEmpty(data)) {
                return true;
            }
        }

        return false;
    }
}
