package com.xiaomi.push;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import com.umeng.analytics.pro.ai;
import com.umeng.analytics.pro.c;
import com.xiaomi.channel.commonutils.logger.b;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class gs {
    private static am a = new am(true);
    private static volatile int b = -1;
    private static long c = System.currentTimeMillis();
    private static final Object d = new Object();
    private static List<a> e = Collections.synchronizedList(new ArrayList());
    private static String f = "";
    private static com.xiaomi.push.providers.a g = null;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static class a {
        public String a;
        public long b;
        public int c;
        public int d;
        public String e;
        public long f;

        public a(String str, long j, int i, int i2, String str2, long j2) {
            this.a = "";
            this.b = 0L;
            this.c = -1;
            this.d = -1;
            this.e = "";
            this.f = 0L;
            this.a = str;
            this.b = j;
            this.c = i;
            this.d = i2;
            this.e = str2;
            this.f = j2;
        }

        public boolean a(a aVar) {
            return TextUtils.equals(aVar.a, this.a) && TextUtils.equals(aVar.e, this.e) && aVar.c == this.c && aVar.d == this.d && Math.abs(aVar.b - this.b) <= 5000;
        }
    }

    public static int a(Context context) {
        if (b == -1) {
            b = b(context);
        }
        return b;
    }

    public static int a(String str) {
        try {
            return str.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException unused) {
            return str.getBytes().length;
        }
    }

    private static long a(int i, long j, boolean z, long j2, boolean z2) {
        if (z && z2) {
            long j3 = c;
            c = j2;
            if (j2 - j3 > 30000 && j > PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
                return j * 2;
            }
        }
        return (j * (i == 0 ? 13 : 11)) / 10;
    }

    /* renamed from: a  reason: collision with other method in class */
    public static void m949a(Context context) {
        b = b(context);
    }

    private static void a(Context context, String str, long j, boolean z, long j2) {
        int a2;
        boolean isEmpty;
        if (context != null && !TextUtils.isEmpty(str) && "com.xiaomi.xmsf".equals(context.getPackageName()) && !"com.xiaomi.xmsf".equals(str) && -1 != (a2 = a(context))) {
            synchronized (d) {
                isEmpty = e.isEmpty();
                a(new a(str, j2, a2, z ? 1 : 0, a2 == 0 ? c(context) : "", j));
            }
            if (isEmpty) {
                a.a(new fx(context), 5000L);
            }
        }
    }

    public static void a(Context context, String str, long j, boolean z, boolean z2, long j2) {
        a(context, str, a(a(context), j, z, j2, z2), z, j2);
    }

    private static void a(a aVar) {
        for (a aVar2 : e) {
            if (aVar2.a(aVar)) {
                aVar2.f += aVar.f;
                return;
            }
        }
        e.add(aVar);
    }

    /* renamed from: a  reason: collision with other method in class */
    public static synchronized void m950a(String str) {
        synchronized (gs.class) {
            if (!l.d() && !TextUtils.isEmpty(str)) {
                f = str;
            }
        }
    }

    private static int b(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return -1;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return -1;
            }
            return activeNetworkInfo.getType();
        } catch (Exception unused) {
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Context context, List<a> list) {
        try {
            synchronized (com.xiaomi.push.providers.a.a) {
                SQLiteDatabase writableDatabase = d(context).getWritableDatabase();
                writableDatabase.beginTransaction();
                for (a aVar : list) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("package_name", aVar.a);
                    contentValues.put("message_ts", Long.valueOf(aVar.b));
                    contentValues.put(ai.T, Integer.valueOf(aVar.c));
                    contentValues.put("bytes", Long.valueOf(aVar.f));
                    contentValues.put("rcv", Integer.valueOf(aVar.d));
                    contentValues.put("imsi", aVar.e);
                    writableDatabase.insert(c.F, null, contentValues);
                }
                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();
            }
        } catch (SQLiteException e2) {
            b.a(e2);
        }
    }

    private static synchronized String c(Context context) {
        synchronized (gs.class) {
            if (TextUtils.isEmpty(f)) {
                return "";
            }
            return f;
        }
    }

    private static com.xiaomi.push.providers.a d(Context context) {
        com.xiaomi.push.providers.a aVar = g;
        if (aVar != null) {
            return aVar;
        }
        g = new com.xiaomi.push.providers.a(context);
        return g;
    }
}
