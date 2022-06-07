package com.xiaomi.smarthome.library.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class SharePrefsManager {
    private static HashMap<String, SharedPreferences> a;

    private static String a(String str) {
        return str != null ? str : "";
    }

    public static SharedPreferences getSharedPrefs(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (a == null) {
            a = new HashMap<>();
        }
        if (a.containsKey(str)) {
            return a.get(str);
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(str, 0);
        a.put(str, sharedPreferences);
        return sharedPreferences;
    }

    public static void setSettingBoolean(SharedPreferences sharedPreferences, String str, boolean z) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putBoolean(str, z).commit();
        }
    }

    public static void setSettingBoolean(Context context, String str, String str2, boolean z) {
        SharedPreferences sharedPrefs = getSharedPrefs(context, str);
        if (sharedPrefs != null) {
            sharedPrefs.edit().putBoolean(str2, z).commit();
        }
    }

    public static boolean getSettingBoolean(SharedPreferences sharedPreferences, String str, boolean z) {
        return sharedPreferences != null ? sharedPreferences.getBoolean(str, z) : z;
    }

    public static boolean getSettingBoolean(Context context, String str, String str2, boolean z) {
        SharedPreferences sharedPrefs = getSharedPrefs(context, str);
        return sharedPrefs != null ? sharedPrefs.getBoolean(str2, z) : z;
    }

    public static void setSettingString(SharedPreferences sharedPreferences, String str, String str2) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString(str, a(str2)).commit();
        }
    }

    public static void setSettingString(Context context, String str, String str2, String str3) {
        SharedPreferences sharedPrefs = getSharedPrefs(context, str);
        if (sharedPrefs != null) {
            sharedPrefs.edit().putString(str2, str3).commit();
        }
    }

    public static void setSettingStringAsync(Context context, String str, String str2, String str3) {
        SharedPreferences sharedPrefs = getSharedPrefs(context, str);
        if (sharedPrefs != null) {
            sharedPrefs.edit().putString(str2, str3).apply();
        }
    }

    public static String getSettingString(SharedPreferences sharedPreferences, String str, String str2) {
        return sharedPreferences != null ? sharedPreferences.getString(str, str2) : str2;
    }

    public static String getSettingString(Context context, String str, String str2, String str3) {
        SharedPreferences sharedPrefs = getSharedPrefs(context, str);
        return (sharedPrefs == null || TextUtils.isEmpty(str2)) ? str3 : sharedPrefs.getString(str2, str3);
    }

    public static int getSettingInt(SharedPreferences sharedPreferences, String str, int i) {
        return sharedPreferences != null ? sharedPreferences.getInt(str, i) : i;
    }

    public static void setSettingInt(SharedPreferences sharedPreferences, String str, int i) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putInt(str, i).commit();
        }
    }

    public static long getSettingLong(Context context, String str, String str2, long j) {
        SharedPreferences sharedPrefs = getSharedPrefs(context, str);
        return sharedPrefs != null ? sharedPrefs.getLong(str2, j) : j;
    }

    public static long getSettingLong(SharedPreferences sharedPreferences, String str, long j) {
        return sharedPreferences != null ? sharedPreferences.getLong(str, j) : j;
    }

    public static void setSettingLong(Context context, String str, String str2, long j) {
        SharedPreferences sharedPrefs = getSharedPrefs(context, str);
        if (sharedPrefs != null) {
            sharedPrefs.edit().putLong(str2, j).commit();
        }
    }

    public static void setSettingLong(SharedPreferences sharedPreferences, String str, long j) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putLong(str, j).commit();
        }
    }

    public static void setSettingFloat(SharedPreferences sharedPreferences, String str, float f) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putFloat(str, f).commit();
        }
    }

    public static void removePreference(SharedPreferences sharedPreferences, String str) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().remove(str).commit();
        }
    }

    public static void clearPreference(SharedPreferences sharedPreferences) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.clear();
            edit.commit();
        }
    }
}
