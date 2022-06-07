package com.umeng.commonsdk.config;

import android.content.Context;
import android.util.Pair;
import com.umeng.commonsdk.config.d;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class FieldManager {
    private static final String a = "cfgfd";
    private static b b = b.b();
    private static boolean c = false;
    private static Object d = new Object();

    private FieldManager() {
    }

    /* loaded from: classes2.dex */
    public static class a {
        private static final FieldManager a = new FieldManager();

        private a() {
        }
    }

    public static FieldManager a() {
        return a.a;
    }

    public static boolean allow(String str) {
        synchronized (d) {
            if (!c) {
                return false;
            }
            b bVar = b;
            return b.a(str);
        }
    }

    public static boolean b() {
        boolean z;
        synchronized (d) {
            z = c;
        }
        return z;
    }

    public void a(Context context) {
        String str;
        String[] strArr = {d.a.class.getName(), d.b.class.getName(), d.c.class.getName(), d.EnumC0140d.class.getName()};
        String imprintProperty = UMEnvelopeBuild.imprintProperty(context, "cfgfd", "1001@3758096383,2147483647,262143,2047");
        synchronized (d) {
            Pair<Long, String> a2 = a(imprintProperty);
            if (((Long) a2.first).longValue() <= 1000 || (str = (String) a2.second) == null || str.length() <= 0) {
                str = "1001@3758096383,2147483647,262143,2047";
            }
            String[] split = str.split(Constants.ACCEPT_TIME_SEPARATOR_SP);
            int length = split.length;
            if (length > 0) {
                ArrayList arrayList = new ArrayList();
                g gVar = new g();
                for (int i = 0; i < length; i++) {
                    arrayList.add(gVar);
                    ((e) arrayList.get(i)).a(split[i], b, d.b(strArr[i]));
                }
            }
            c = true;
        }
    }

    public void a(Context context, String str) {
        String str2;
        String str3 = "1001@3758096383,2147483647,262143,2047";
        String[] strArr = {d.a.class.getName(), d.b.class.getName(), d.c.class.getName(), d.EnumC0140d.class.getName()};
        synchronized (d) {
            b.a();
            if (str != null) {
                Pair<Long, String> a2 = a(str);
                if (((Long) a2.first).longValue() > 1000 && (str2 = (String) a2.second) != null && str2.length() > 0) {
                    str3 = str2;
                }
            }
            String[] split = str3.split(Constants.ACCEPT_TIME_SEPARATOR_SP);
            int length = split.length;
            if (length > 0) {
                ArrayList arrayList = new ArrayList();
                g gVar = new g();
                for (int i = 0; i < length; i++) {
                    arrayList.add(gVar);
                    ((e) arrayList.get(i)).a(split[i], b, d.b(strArr[i]));
                }
            }
            c = true;
        }
    }

    private static Pair<Long, String> a(String str) {
        Pair<Long, String> pair = new Pair<>(-1L, null);
        if (str == null || str.length() < 2) {
            return pair;
        }
        String[] split = str.split("@");
        if (split.length < 2) {
            return pair;
        }
        try {
            long parseLong = Long.parseLong(split[0]);
            return new Pair<>(Long.valueOf(parseLong), split[1]);
        } catch (Throwable unused) {
            return pair;
        }
    }
}
