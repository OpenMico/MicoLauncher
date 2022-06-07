package org.apache.commons.lang.enums;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public class EnumUtils {
    public static Enum getEnum(Class cls, String str) {
        return Enum.getEnum(cls, str);
    }

    public static ValuedEnum getEnum(Class cls, int i) {
        return (ValuedEnum) ValuedEnum.getEnum(cls, i);
    }

    public static Map getEnumMap(Class cls) {
        return Enum.getEnumMap(cls);
    }

    public static List getEnumList(Class cls) {
        return Enum.getEnumList(cls);
    }

    public static Iterator iterator(Class cls) {
        return Enum.getEnumList(cls).iterator();
    }
}
