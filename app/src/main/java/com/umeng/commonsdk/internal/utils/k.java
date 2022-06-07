package com.umeng.commonsdk.internal.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import com.umeng.commonsdk.internal.crash.UMCrashManager;
import com.umeng.commonsdk.statistics.common.ULog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.JSONObject;

/* compiled from: UMProbe.java */
/* loaded from: classes2.dex */
public class k {
    public static final String a = "UM_PROBE_DATA";
    public static final String b = "_dsk_s";
    public static final String c = "_thm_z";
    public static final String d = "_gdf_r";
    private static Object e = new Object();

    public static String a(Context context) {
        try {
            SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(a, 0);
            if (sharedPreferences == null) {
                return null;
            }
            JSONObject jSONObject = new JSONObject();
            synchronized (e) {
                jSONObject.put(b, sharedPreferences.getString(b, ""));
                jSONObject.put(c, sharedPreferences.getString(c, ""));
                jSONObject.put(d, sharedPreferences.getString(d, ""));
            }
            return jSONObject.toString();
        } catch (Exception e2) {
            UMCrashManager.reportCrash(context, e2);
            return null;
        }
    }

    public static void b(final Context context) {
        if (!c(context)) {
            final String[] strArr = {"unknown", "unknown", "unknown"};
            new Thread() { // from class: com.umeng.commonsdk.internal.utils.k.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    super.run();
                    try {
                        strArr[0] = k.c();
                        strArr[1] = k.a();
                        strArr[2] = k.b();
                        ULog.i("diskType = " + strArr[0] + "; ThremalZone = " + strArr[1] + "; GoldFishRc = " + strArr[2]);
                        k.b(context, strArr);
                    } catch (Throwable th) {
                        UMCrashManager.reportCrash(context, th);
                    }
                }
            }.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Context context, String[] strArr) {
        SharedPreferences sharedPreferences;
        if (context != null && (sharedPreferences = context.getApplicationContext().getSharedPreferences(a, 0)) != null) {
            synchronized (e) {
                sharedPreferences.edit().putString(b, strArr[0]).putString(c, strArr[1]).putString(d, strArr[2]).commit();
            }
        }
    }

    public static boolean c(Context context) {
        SharedPreferences sharedPreferences;
        return (context == null || (sharedPreferences = context.getApplicationContext().getSharedPreferences(a, 0)) == null || TextUtils.isEmpty(sharedPreferences.getString(b, ""))) ? false : true;
    }

    public static int a(String str, String str2) throws IOException {
        int i;
        if (Build.VERSION.SDK_INT > 28) {
            return -1;
        }
        Process exec = Runtime.getRuntime().exec(str);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine != null) {
                if (readLine.contains(str2)) {
                    i = 1;
                    break;
                }
            } else {
                i = -1;
                break;
            }
        }
        try {
            if (exec.waitFor() != 0) {
                return -1;
            }
            return i;
        } catch (InterruptedException unused) {
            return -1;
        }
    }

    public static String a() {
        int i;
        try {
            i = a("ls /sys/class/thermal", "thermal_zone");
        } catch (Throwable unused) {
            i = -1;
        }
        return i > 0 ? "thermal_zone" : i < 0 ? "noper" : "unknown";
    }

    public static String b() {
        int i;
        try {
            i = a("ls /", "goldfish");
        } catch (Throwable unused) {
            i = -1;
        }
        return i > 0 ? "goldfish" : i < 0 ? "noper" : "unknown";
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x003c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String c() {
        /*
            java.lang.String r0 = "unknown"
            r1 = 0
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch: Throwable -> 0x0037
            java.io.FileReader r3 = new java.io.FileReader     // Catch: Throwable -> 0x0037
            java.lang.String r4 = "/proc/diskstats"
            r3.<init>(r4)     // Catch: Throwable -> 0x0037
            r2.<init>(r3)     // Catch: Throwable -> 0x0037
            java.lang.String r1 = "mmcblk"
            java.lang.String r3 = "sda"
            java.lang.String r4 = "mtd"
        L_0x0016:
            java.lang.String r5 = r2.readLine()     // Catch: Throwable -> 0x0038
            if (r5 == 0) goto L_0x003a
            boolean r6 = r5.contains(r1)     // Catch: Throwable -> 0x0038
            if (r6 == 0) goto L_0x0025
            java.lang.String r0 = "mmcblk"
            goto L_0x003a
        L_0x0025:
            boolean r6 = r5.contains(r3)     // Catch: Throwable -> 0x0038
            if (r6 == 0) goto L_0x002e
            java.lang.String r0 = "sda"
            goto L_0x003a
        L_0x002e:
            boolean r5 = r5.contains(r4)     // Catch: Throwable -> 0x0038
            if (r5 == 0) goto L_0x0016
            java.lang.String r0 = "mtd"
            goto L_0x003a
        L_0x0037:
            r2 = r1
        L_0x0038:
            java.lang.String r0 = "noper"
        L_0x003a:
            if (r2 == 0) goto L_0x003f
            r2.close()     // Catch: Throwable -> 0x003f
        L_0x003f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.internal.utils.k.c():java.lang.String");
    }
}
