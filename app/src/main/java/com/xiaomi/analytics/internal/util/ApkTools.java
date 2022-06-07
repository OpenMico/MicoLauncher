package com.xiaomi.analytics.internal.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.Constants;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class ApkTools {
    /* JADX WARN: Removed duplicated region for block: B:102:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x014c A[Catch: Exception -> 0x0150, TRY_ENTER, TRY_LEAVE, TryCatch #0 {Exception -> 0x0150, blocks: (B:56:0x0137, B:62:0x014c), top: B:72:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void extractSo(android.content.Context r11, java.lang.String r12, java.lang.String r13) {
        /*
            Method dump skipped, instructions count: 368
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.analytics.internal.util.ApkTools.extractSo(android.content.Context, java.lang.String, java.lang.String):void");
    }

    private static List<String> a(Context context) {
        ArrayList arrayList = new ArrayList();
        String b = b(context);
        if (!TextUtils.isEmpty(b)) {
            arrayList.add(b);
        }
        String str = SystemProperties.get("ro.product.cpu.abi", "");
        if (!TextUtils.isEmpty(str)) {
            arrayList.add(str);
        }
        String str2 = SystemProperties.get("ro.product.cpu.abi2", "");
        if (!TextUtils.isEmpty(str2)) {
            arrayList.add(str2);
        }
        String str3 = SystemProperties.get("ro.product.cpu.abilist", "");
        if (!TextUtils.isEmpty(str3)) {
            String[] split = str3.split(Constants.ACCEPT_TIME_SEPARATOR_SP);
            for (int i = 0; split != null && i < split.length; i++) {
                if (!TextUtils.isEmpty(split[i])) {
                    arrayList.add(split[i]);
                }
            }
        }
        arrayList.add("armeabi");
        return arrayList;
    }

    private static String b(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            Field declaredField = Class.forName("android.content.pm.ApplicationInfo").getDeclaredField("primaryCpuAbi");
            declaredField.setAccessible(true);
            return (String) declaredField.get(applicationInfo);
        } catch (Throwable unused) {
            return null;
        }
    }

    private static int a(List<String> list, String str) {
        for (int i = 0; list != null && i < list.size(); i++) {
            if (!TextUtils.isEmpty(str) && str.equalsIgnoreCase(list.get(i))) {
                return i;
            }
        }
        return -1;
    }

    private static String a(String str) {
        String[] split;
        return (str == null || (split = str.split("/")) == null || split.length <= 0) ? str : split[split.length - 1];
    }

    private static String b(String str) {
        String[] split;
        return (str == null || (split = str.split("/")) == null || split.length <= 1) ? "armeabi" : split[split.length - 2];
    }
}
