package com.xiaomi.mico.base.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class CollectionUtils {

    /* loaded from: classes3.dex */
    public interface BelongTo<T> {
        boolean belongTo(T t);
    }

    public static <T> List<T> filter(List<T> list, BelongTo<T> belongTo) {
        ArrayList arrayList = new ArrayList();
        for (T t : list) {
            if (belongTo.belongTo(t)) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }

    public static <T1, T2> List<T2> convert(List<T1> list, List<T2> list2) {
        for (T1 t1 : list) {
            list2.add(t1);
        }
        return list2;
    }

    public static <K, V> K findKeyByValue(Map<K, V> map, V v) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(v)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
