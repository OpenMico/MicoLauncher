package com.xiaomi.smarthome.library.http.util;

import java.util.Map;
import okhttp3.Headers;

/* loaded from: classes4.dex */
public class HeaderUtil {
    public static Headers getHeaders(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        return Headers.of(map);
    }

    public static void checkName(String str) throws Exception {
        if (str == null) {
            throw new IllegalArgumentException("name == null");
        } else if (!str.isEmpty()) {
            int length = str.length();
            for (int i = 0; i < length; i++) {
                char charAt = str.charAt(i);
                if (charAt <= 31 || charAt >= 127) {
                    throw new IllegalArgumentException(String.format("Unexpected char %#04x at %d in header name: %s", Integer.valueOf(charAt), Integer.valueOf(i), str));
                }
            }
        } else {
            throw new IllegalArgumentException("name is empty");
        }
    }

    public static void checkValue(String str) throws Exception {
        if (str != null) {
            int length = str.length();
            for (int i = 0; i < length; i++) {
                char charAt = str.charAt(i);
                if (charAt <= 31 || charAt >= 127) {
                    throw new IllegalArgumentException(String.format("Unexpected char %#04x at %d in header value: %s", Integer.valueOf(charAt), Integer.valueOf(i), str));
                }
            }
            return;
        }
        throw new IllegalArgumentException("value == null");
    }
}
