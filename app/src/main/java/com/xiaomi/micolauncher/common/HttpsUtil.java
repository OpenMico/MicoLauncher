package com.xiaomi.micolauncher.common;

import android.net.Uri;

/* loaded from: classes3.dex */
public class HttpsUtil {
    public static boolean isHttpOrHttpsSchema(String str) {
        return isHttpOrHttpsSchema(Uri.parse(str));
    }

    public static boolean isHttpOrHttpsSchema(Uri uri) {
        String scheme = uri.getScheme();
        return "http".equals(scheme) || "https".equals(scheme);
    }
}
