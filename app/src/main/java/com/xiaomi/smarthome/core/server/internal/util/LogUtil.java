package com.xiaomi.smarthome.core.server.internal.util;

import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.smarthome.globalsetting.GlobalSetting;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes4.dex */
public class LogUtil {
    public static void d(String str, String str2) {
        if (!TextUtils.isEmpty(str2) && GlobalSetting.getIsDebug()) {
            int i = 0;
            while (i <= str2.length() / 2000) {
                int i2 = i * 2000;
                i++;
                int i3 = i * 2000;
                if (i3 > str2.length()) {
                    i3 = str2.length();
                }
                Log.d(str, str2.substring(i2, i3));
            }
        }
    }

    public static void e(String str, String str2) {
        if (!TextUtils.isEmpty(str2) && GlobalSetting.getIsDebug()) {
            int i = 0;
            while (i <= str2.length() / 2000) {
                int i2 = i * 2000;
                i++;
                int i3 = i * 2000;
                if (i3 > str2.length()) {
                    i3 = str2.length();
                }
                Log.e(str, str2.substring(i2, i3));
            }
        }
    }

    public static void i(String str, String str2) {
        if (!TextUtils.isEmpty(str2) && GlobalSetting.getIsDebug()) {
            int i = 0;
            while (i <= str2.length() / 2000) {
                int i2 = i * 2000;
                i++;
                int i3 = i * 2000;
                if (i3 > str2.length()) {
                    i3 = str2.length();
                }
                Log.i(str, str2.substring(i2, i3));
            }
        }
    }

    public static void v(String str, String str2) {
        if (!TextUtils.isEmpty(str2) && GlobalSetting.getIsDebug()) {
            int i = 0;
            while (i <= str2.length() / 2000) {
                int i2 = i * 2000;
                i++;
                int i3 = i * 2000;
                if (i3 > str2.length()) {
                    i3 = str2.length();
                }
                Log.v(str, str2.substring(i2, i3));
            }
        }
    }

    public static void w(String str, String str2) {
        if (!TextUtils.isEmpty(str2) && GlobalSetting.getIsDebug()) {
            int i = 0;
            while (i <= str2.length() / 2000) {
                int i2 = i * 2000;
                i++;
                int i3 = i * 2000;
                if (i3 > str2.length()) {
                    i3 = str2.length();
                }
                Log.w(str, str2.substring(i2, i3));
            }
        }
    }

    public static void printStackTraceString(String str, String str2) {
        if (GlobalSetting.getIsDebug()) {
            String str3 = str2 + StringUtils.SPACE + Log.getStackTraceString(new Throwable());
            int i = 0;
            while (i <= str3.length() / 2000) {
                int i2 = i * 2000;
                i++;
                int i3 = i * 2000;
                if (i3 > str3.length()) {
                    i3 = str3.length();
                }
                Log.d(str, str3.substring(i2, i3));
            }
        }
    }
}
