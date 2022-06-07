package com.xiaomi.miplay.mylibrary.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import com.xiaomi.mipush.sdk.Constants;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes4.dex */
public class CpuInfo {
    public static final int CPU_MTK_HSeries = 6;
    public static final int CPU_MTK_LSeries = 8;
    public static final int CPU_MTK_MSeries = 7;
    public static final int CPU_Qualcomm_Series_4 = 5;
    public static final int CPU_Qualcomm_Series_5 = 4;
    public static final int CPU_Qualcomm_Series_6 = 3;
    public static final int CPU_Qualcomm_Series_7 = 2;
    public static final int CPU_Qualcomm_Series_8 = 1;
    public static final int CPU_Unknow = 0;
    static String[] a = {"SM8350", "SM8250", "SM8150", "SDM845", "MSM8998", "MSM8996pro", "MSM8996"};
    static String[] b = {"SM7350", "SM7250", "SM7150", "SDM712", "SDM710", "LAGOON", "SM7225"};
    static String[] c = new String[0];
    static String[] d = {"TRINKET", "SM6150", "SDM660", "SDM632", "SDM636", "MSM8953", "SM6125", "BENGAL", "SM6350"};
    static String[] e = {"SDM439"};
    static String[] f = new String[0];
    static String[] g = {"MT6889"};
    static String[] h = new String[0];
    static String[] i = new String[0];

    public static String getCpuName() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/cpuinfo"));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    bufferedReader.close();
                    return null;
                } else if (readLine.contains("Hardware")) {
                    String str = readLine.split(Constants.COLON_SEPARATOR)[1];
                    if (str.contains("Qualcomm Technologies, Inc ")) {
                        String str2 = str.split("Inc ")[1];
                        Log.i("MiPlayQuickCpuInfo", "cpu is " + str2);
                        return str2;
                    } else if (str.contains("Qualcomm Technologies, Inc. ")) {
                        String str3 = str.split("Inc. ")[1];
                        Log.i("MiPlayQuickCpuInfo", "... cpu is " + str3);
                        return str3;
                    } else if (str.contains("MT")) {
                        String str4 = str.split(StringUtils.SPACE)[1];
                        Log.i("MiPlayQuickCpuInfo", "mtk cpu is " + str4);
                        return str4;
                    }
                }
            }
        } catch (IOException unused) {
            return null;
        }
    }

    public static boolean getCpuIsSupport(String[] strArr, String str) {
        if (strArr.length <= 0 || str == null) {
            return false;
        }
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if (TextUtils.equals(strArr[i2], str)) {
                return true;
            }
            if (strArr[i2].length() >= str.length()) {
                if (TextUtils.regionMatches(strArr[i2], 0, str, 0, str.length())) {
                    return true;
                }
            } else if (TextUtils.regionMatches(strArr[i2], 0, str, 0, strArr[i2].length())) {
                return true;
            }
        }
        return false;
    }

    public static boolean IsSupportCpu(Context context) {
        String cpuName = getCpuName();
        Log.i("MiPlayQuickCpuInfo", "cpuinfo " + cpuName);
        if (cpuName != null && (getCpuIsSupport(a, cpuName) || getCpuIsSupport(b, cpuName) || getCpuIsSupport(d, cpuName) || getCpuIsSupport(g, cpuName))) {
            return true;
        }
        String str = null;
        if (context != null) {
            str = a(context, "ro.build.product");
        }
        return str != null && str.equals("venus");
    }

    private static String a(Context context, String str) throws IllegalArgumentException {
        try {
            Class<?> loadClass = context.getClassLoader().loadClass("android.os.SystemProperties");
            return (String) loadClass.getMethod(BluetoothConstants.GET, String.class).invoke(loadClass, new String(str));
        } catch (IllegalArgumentException e2) {
            throw e2;
        } catch (Exception unused) {
            return "";
        }
    }

    public static int GetCpuSeriesFlag(Context context) {
        String cpuName = getCpuName();
        Log.i("MiPlayQuickCpuInfo", "cpuinfo " + cpuName);
        if (cpuName != null) {
            if (getCpuIsSupport(a, cpuName)) {
                return 1;
            }
            if (getCpuIsSupport(b, cpuName)) {
                return 2;
            }
            if (getCpuIsSupport(d, cpuName)) {
                return 3;
            }
            if (getCpuIsSupport(c, cpuName)) {
                return 4;
            }
            if (getCpuIsSupport(e, cpuName)) {
                return 5;
            }
            if (getCpuIsSupport(g, cpuName)) {
                return 6;
            }
            if (getCpuIsSupport(h, cpuName)) {
                return 7;
            }
            if (getCpuIsSupport(i, cpuName)) {
                return 8;
            }
        }
        String str = null;
        if (context != null) {
            str = a(context, "ro.build.product");
        }
        return (str == null || !str.equals("venus")) ? 0 : 1;
    }

    public static boolean GetProductNameIsAssign(Context context, String str) {
        String a2 = context != null ? a(context, "ro.build.product") : null;
        return (a2 == null || str == null || !a2.equals(str)) ? false : true;
    }
}
