package com.xiaomi.push;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class q {
    private static volatile q a;
    private Context b;
    private Handler c = new Handler(Looper.getMainLooper());
    private Map<String, Map<String, String>> d = new HashMap();

    private q(Context context) {
        this.b = context;
    }

    public static q a(Context context) {
        if (a == null) {
            synchronized (q.class) {
                if (a == null) {
                    a = new q(context);
                }
            }
        }
        return a;
    }

    private synchronized String a(String str, String str2) {
        if (this.d == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return "";
        }
        try {
            Map<String, String> map = this.d.get(str);
            if (map == null) {
                return "";
            }
            return map.get(str2);
        } catch (Throwable unused) {
            return "";
        }
    }

    private synchronized void b(String str, String str2, String str3) {
        if (this.d == null) {
            this.d = new HashMap();
        }
        Map<String, String> map = this.d.get(str);
        if (map == null) {
            map = new HashMap<>();
        }
        map.put(str2, str3);
        this.d.put(str, map);
    }

    public synchronized String a(String str, String str2, String str3) {
        String a2 = a(str, str2);
        if (!TextUtils.isEmpty(a2)) {
            return a2;
        }
        return this.b.getSharedPreferences(str, 4).getString(str2, str3);
    }

    /* renamed from: a  reason: collision with other method in class */
    public synchronized void m1116a(String str, String str2, String str3) {
        b(str, str2, str3);
        this.c.post(new ha(this, str, str2, str3));
    }
}
