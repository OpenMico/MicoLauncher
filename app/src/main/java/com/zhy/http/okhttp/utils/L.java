package com.zhy.http.okhttp.utils;

import android.util.Log;

/* loaded from: classes4.dex */
public class L {
    private static boolean a = false;

    public static void e(String str) {
        if (a) {
            Log.e("OkHttp", str);
        }
    }
}
