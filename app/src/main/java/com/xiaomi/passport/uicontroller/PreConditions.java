package com.xiaomi.passport.uicontroller;

import android.text.TextUtils;

/* loaded from: classes4.dex */
public class PreConditions {
    public static void checkArgumentNotEmpty(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException(str2);
        }
    }

    public static <T> void checkArgumentNotNull(T t, String str) {
        if (t == null) {
            throw new NullPointerException(str);
        }
    }
}
