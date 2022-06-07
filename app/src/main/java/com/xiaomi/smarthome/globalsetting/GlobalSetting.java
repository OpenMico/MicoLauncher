package com.xiaomi.smarthome.globalsetting;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.xiaomi.smarthome.setting.ServerSetting;
import java.util.Locale;

/* loaded from: classes4.dex */
public class GlobalSetting {
    private static boolean a;
    private static String b;
    private static String c;
    private static final Object d = new Object();
    private static Context e;
    private static Locale f;

    public static Context getAppContext() {
        Context context;
        synchronized (d) {
            context = e;
        }
        return context;
    }

    public static void setAppContext(Context context) {
        synchronized (d) {
            e = context;
        }
    }

    public static boolean isEurope() {
        return a(ServerSetting.SERVER_DE);
    }

    private static boolean a(String str) {
        return !TextUtils.isEmpty(b) && TextUtils.equals(b, str);
    }

    public static boolean isNetActiveAndAvailable() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) e.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                return activeNetworkInfo.isAvailable();
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    public static synchronized String getServerEnv() {
        String str;
        synchronized (GlobalSetting.class) {
            str = c;
        }
        return str;
    }

    public static void setServer(String str) {
        synchronized (d) {
            b = str;
        }
    }

    public static void setServerEnv(String str) {
        synchronized (d) {
            c = str;
        }
    }

    public static void setIsDebug(boolean z) {
        synchronized (d) {
            a = z;
        }
    }

    public static boolean getIsDebug() {
        return a;
    }

    public static synchronized String getServer() {
        String server;
        synchronized (GlobalSetting.class) {
            server = getServer(true);
        }
        return server;
    }

    public static synchronized String getServer(boolean z) {
        synchronized (GlobalSetting.class) {
            if (z) {
                return mapServer(b);
            }
            return b;
        }
    }

    public static String mapServer(String str) {
        return (!ServerSetting.SERVER_HK.equals(str) && !ServerSetting.SERVER_US.equals(str) && !ServerSetting.SERVER_KR.equals(str)) ? ServerSetting.SERVER_DE.equals(str) ? ServerSetting.SERVER_DE : ServerSetting.SERVER_TK.equals(str) ? ServerSetting.SERVER_SG : ServerSetting.SERVER_US_TRUE.equals(str) ? ServerSetting.SERVER_US : ServerSetting.SERVER_IN_TRUE.equals(str) ? ServerSetting.SERVER_IN_TRUE : (!ServerSetting.SERVER_TW.equals(str) && !ServerSetting.SERVER_IN.equals(str)) ? str : ServerSetting.SERVER_SG : ServerSetting.SERVER_SG;
    }

    public static void setLocale(Locale locale) {
        synchronized (d) {
            f = locale;
        }
    }

    public static Locale getLocale() {
        return f;
    }
}
