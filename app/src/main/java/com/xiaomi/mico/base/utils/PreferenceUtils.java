package com.xiaomi.mico.base.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.xiaomi.mico.common.ProcessChecker;
import com.xiaomi.mipush.sdk.Constants;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public abstract class PreferenceUtils {
    private static volatile AtomicBoolean a;

    /* loaded from: classes3.dex */
    public interface PrefObserver {
        void notifyPrefChange(String str, Object obj);
    }

    public static String getSettingString(Context context, String str, String str2) {
        return a(context).getString(str, str2);
    }

    public static void setSettingString(Context context, String str, String str2) {
        a(context).edit().putString(str, str2).apply();
    }

    public static boolean getSettingBoolean(Context context, String str, boolean z) {
        return a(context).getBoolean(str, z);
    }

    public static void setSettingBoolean(Context context, String str, boolean z) {
        Log.i("PreferenceUtils", "setSettingBoolean " + str + " to " + z);
        a(context).edit().putBoolean(str, z).apply();
    }

    public static void setSettingInt(Context context, String str, int i) {
        a(context).edit().putInt(str, i).apply();
    }

    private static SharedPreferences a(Context context) {
        if (a == null) {
            a = new AtomicBoolean(ProcessChecker.isMainProcess(context));
        }
        if (a.get()) {
            return PreferenceManager.getDefaultSharedPreferences(context);
        }
        return context.getSharedPreferences("mico_sub_process_" + ProcessChecker.getCurrentProcessName(context) + "_shared_pref", 0);
    }

    public static void setSettingInt(SharedPreferences sharedPreferences, String str, int i) {
        sharedPreferences.edit().putInt(str, i).apply();
    }

    public static void increaseSettingInt(Context context, String str) {
        increaseSettingInt(a(context), str);
    }

    public static void increaseSettingInt(SharedPreferences sharedPreferences, String str) {
        sharedPreferences.edit().putInt(str, sharedPreferences.getInt(str, 0) + 1).apply();
    }

    public static void increaseSettingInt(SharedPreferences sharedPreferences, String str, int i) {
        sharedPreferences.edit().putInt(str, sharedPreferences.getInt(str, 0) + i).apply();
    }

    public static int getSettingInt(Context context, String str, int i) {
        return a(context).getInt(str, i);
    }

    public static void setSettingFloat(Context context, String str, float f) {
        a(context).edit().putFloat(str, f).apply();
    }

    public static float getSettingFloat(Context context, String str, float f) {
        return a(context).getFloat(str, f);
    }

    public static void setSettingLong(Context context, String str, long j) {
        a(context).edit().putLong(str, j).apply();
    }

    public static long getSettingLong(Context context, String str, long j) {
        return a(context).getLong(str, j);
    }

    public static void setSettingStringSet(Context context, String str, Set<String> set) {
        a(context).edit().putStringSet(str, set).apply();
    }

    public static Set<String> getSettingStringSet(Context context, String str, Set<String> set) {
        return a(context).getStringSet(str, set);
    }

    public static void dumpDefaultPreference(Context context) {
        a(a(context), "default preference:");
    }

    public static void dumpDefaultPreference(Context context, String str) {
        a(context.getSharedPreferences(str, 0), str);
    }

    private static void a(SharedPreferences sharedPreferences, String str) {
        Log.i("PreferenceUtils", "begin dump pref " + str);
        Map<String, ?> all = sharedPreferences.getAll();
        for (String str2 : all.keySet()) {
            Log.i("PreferenceUtils", "dump pref KV " + str2 + Constants.COLON_SEPARATOR + all.get(str2));
        }
        Log.i("PreferenceUtils", "end dump pref " + str);
    }
}
