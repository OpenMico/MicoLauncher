package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import com.blankj.utilcode.constant.CacheConstants;
import com.xiaomi.push.aj;
import com.xiaomi.push.hm;
import com.xiaomi.push.service.ag;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class b {
    public static StackTraceElement[] a;

    public static void a() {
        try {
            a = Thread.getAllStackTraces().get(Thread.currentThread());
        } catch (Throwable unused) {
        }
    }

    public static void a(Context context) {
        aj.a(context).a(new am(context), 20);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(int i) {
        StackTraceElement[] stackTraceElementArr = a;
        if (stackTraceElementArr == null || stackTraceElementArr.length <= 4) {
            return "";
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 4; i2 < a.length && i2 < i + 4; i2++) {
            try {
                arrayList.add(a[i2].toString());
            } catch (Exception unused) {
                return "";
            }
        }
        return arrayList.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean d(Context context) {
        ag a2 = ag.a(context);
        if (!a2.a(hm.AggregationSdkMonitorSwitch.a(), false)) {
            return false;
        }
        return Math.abs(System.currentTimeMillis() - context.getSharedPreferences("mipush_extra", 0).getLong("last_upload_call_stack_timestamp", 0L)) >= ((long) Math.max(60, a2.a(hm.AggregationSdkMonitorFrequency.a(), CacheConstants.DAY)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void e(Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("mipush_extra", 0).edit();
        edit.putLong("last_upload_call_stack_timestamp", System.currentTimeMillis());
        edit.commit();
    }
}
