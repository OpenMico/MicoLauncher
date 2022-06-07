package com.umeng.umcrash;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import com.uc.crashsdk.export.CrashApi;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes2.dex */
public class UMCrashUtils {
    public static final String UNKNOW = "";

    public static String[] getActiveUser(Context context) {
        Class<?> cls;
        Method method;
        if (context == null) {
            return null;
        }
        try {
            cls = Class.forName("com.umeng.commonsdk.utils.UMUtils");
        } catch (ClassNotFoundException unused) {
            cls = null;
        }
        if (cls == null) {
            return null;
        }
        try {
            method = cls.getMethod("getActiveUser", Context.class);
        } catch (NoSuchMethodException unused2) {
            method = null;
        }
        if (method == null) {
            return null;
        }
        try {
            Object invoke = method.invoke(null, context);
            if (invoke != null) {
                return (String[]) invoke;
            }
            return null;
        } catch (IllegalAccessException | InvocationTargetException unused3) {
            return null;
        }
    }

    public static void setPuidAndProvider(String str, String str2) {
        if (CrashApi.getInstance() != null) {
            CrashApi.getInstance().addHeaderInfo(UMCrash.KEY_HEADER_PUID, str);
            CrashApi.getInstance().addHeaderInfo(UMCrash.KEY_HEADER_PROVIDER, str2);
        }
    }

    public static String getNetworkOperatorName(Context context) {
        if (context == null) {
            return "";
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            return (!checkPermission(context, "android.permission.READ_PHONE_STATE") || telephonyManager == null) ? "" : telephonyManager.getNetworkOperatorName();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static boolean checkPermission(Context context, String str) {
        if (context == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 23) {
            return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
        }
        try {
            return ((Integer) Class.forName("android.content.Context").getMethod("checkSelfPermission", String.class).invoke(context, str)).intValue() == 0;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static String splitByByte(String str, int i) {
        StringBuffer stringBuffer = new StringBuffer(i);
        try {
            int length = str.length();
            int i2 = 0;
            for (int i3 = 0; i3 < length; i3++) {
                String valueOf = String.valueOf(str.charAt(i3));
                i2 += valueOf.getBytes().length;
                if (i < i2) {
                    break;
                }
                stringBuffer.append(valueOf);
            }
        } catch (Throwable unused) {
        }
        return stringBuffer.toString();
    }
}
