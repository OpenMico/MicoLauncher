package com.xiaomi.smarthome.library.common.util;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ListUtils {
    public static boolean isEmpty(List<?> list) {
        return list == null || list.size() <= 0;
    }

    public static <E> List<E> getEmptyList() {
        return new ArrayList();
    }
}
