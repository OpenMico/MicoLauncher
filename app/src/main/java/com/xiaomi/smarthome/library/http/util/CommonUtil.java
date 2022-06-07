package com.xiaomi.smarthome.library.http.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes4.dex */
public class CommonUtil {
    public static void asserts(boolean z, String str) {
        if (!z) {
            throw new AssertionError(str);
        }
    }

    public static <T> T notNull(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException(str + " should not be null!");
    }

    public static void silentCloseInputStream(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void silentCloseOutputStream(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException unused) {
            }
        }
    }
}
