package com.xiaomi.passport.ui.settings.utils;

import android.util.Base64;
import java.nio.charset.Charset;

/* loaded from: classes4.dex */
public class Base64Utils {
    private static final int BASE64_FLAGS = 10;
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public static String encodeToString(String str) {
        return Base64.encodeToString(str.getBytes(UTF8), 10);
    }

    public static String encodeToString(byte[] bArr) {
        return Base64.encodeToString(bArr, 10);
    }

    public static String decodeToString(String str) {
        return new String(Base64.decode(str, 10), UTF8);
    }
}
