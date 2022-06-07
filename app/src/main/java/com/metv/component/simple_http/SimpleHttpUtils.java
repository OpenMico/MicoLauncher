package com.metv.component.simple_http;

import android.text.TextUtils;

/* loaded from: classes2.dex */
public class SimpleHttpUtils {
    public static boolean checkHost(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.startsWith("http") || str.startsWith("https");
    }
}
