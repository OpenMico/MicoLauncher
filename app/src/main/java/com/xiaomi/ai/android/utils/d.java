package com.xiaomi.ai.android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Map;

/* loaded from: classes2.dex */
public final class d {
    public static String a(Context context, String str, String str2) {
        return context.getSharedPreferences(str, 0).getString(str2, null);
    }

    public static void a(Context context, String str) {
        SharedPreferences.Editor edit = context.getSharedPreferences(str, 0).edit();
        edit.clear();
        edit.apply();
    }

    public static void a(Context context, String str, String str2, String str3) {
        SharedPreferences.Editor edit = context.getSharedPreferences(str, 0).edit();
        edit.putString(str2, str3);
        edit.apply();
    }

    public static Map<String, ?> b(Context context, String str) {
        return context.getSharedPreferences(str, 0).getAll();
    }

    public static void b(Context context, String str, String str2) {
        SharedPreferences.Editor edit = context.getSharedPreferences(str, 0).edit();
        edit.remove(str2);
        edit.apply();
    }
}
