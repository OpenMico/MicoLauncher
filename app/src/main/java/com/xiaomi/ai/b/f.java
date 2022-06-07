package com.xiaomi.ai.b;

import java.util.Map;

/* loaded from: classes3.dex */
public final class f {
    public static String a(Map<String, String> map) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : map.keySet()) {
            stringBuffer.append(str);
            stringBuffer.append(": ");
            stringBuffer.append(map.get(str));
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }

    public static boolean a(String str) {
        return str == null || str.length() == 0;
    }
}
