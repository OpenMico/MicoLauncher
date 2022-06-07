package com.miui.privacypolicy;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes2.dex */
public class SharePreferenceUtils {
    private static SharedPreferences mInstance;

    private SharePreferenceUtils() {
    }

    private static SharedPreferences getInstance(Context context) {
        if (mInstance == null) {
            mInstance = context.getSharedPreferences("privacy_sdk", 0);
        }
        return mInstance;
    }

    protected static void putString(Context context, String str, String str2) {
        SharedPreferences.Editor edit = getInstance(context).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    protected static String getString(Context context, String str) {
        return getInstance(context).getString(str, "");
    }

    protected static String getString(Context context, String str, String str2) {
        return getInstance(context).getString(str, str2);
    }

    protected static void putBoolean(Context context, String str, boolean z) {
        SharedPreferences.Editor edit = getInstance(context).edit();
        edit.putBoolean(str, z);
        edit.commit();
    }

    protected static boolean getBoolean(Context context, String str, boolean z) {
        return getInstance(context).getBoolean(str, z);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static void putLong(Context context, String str, long j) {
        SharedPreferences.Editor edit = getInstance(context).edit();
        edit.putLong(str, j);
        edit.commit();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static long getLong(Context context, String str, long j) {
        return getInstance(context).getLong(str, j);
    }

    protected static void putInt(Context context, String str, int i) {
        SharedPreferences.Editor edit = getInstance(context).edit();
        edit.putInt(str, i);
        edit.commit();
    }

    protected static int getInt(Context context, String str, int i) {
        return getInstance(context).getInt(str, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static void clear(Context context) {
        SharedPreferences.Editor edit = getInstance(context).edit();
        edit.clear();
        edit.commit();
    }
}
