package com.xiaomi.push.service;

import android.content.Context;
import android.content.SharedPreferences;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.push.ag;
import com.xiaomi.push.aj;
import com.xiaomi.push.at;
import com.xiaomi.push.s;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public final class bc implements ae {
    private static volatile bc f;
    Context a;
    private SharedPreferences b;
    private long c;
    private volatile boolean d = false;
    private ConcurrentHashMap<String, a> e = new ConcurrentHashMap<>();

    /* loaded from: classes4.dex */
    public static abstract class a implements Runnable {
        String a;
        long b;

        /* JADX INFO: Access modifiers changed from: package-private */
        public a(String str, long j) {
            this.a = str;
            this.b = j;
        }

        abstract void a(bc bcVar);

        @Override // java.lang.Runnable
        public void run() {
            if (bc.f != null) {
                Context context = bc.f.a;
                if (at.c(context)) {
                    long currentTimeMillis = System.currentTimeMillis();
                    SharedPreferences sharedPreferences = bc.f.b;
                    if (currentTimeMillis - sharedPreferences.getLong(":ts-" + this.a, 0L) > this.b || ag.a(context)) {
                        SharedPreferences.Editor edit = bc.f.b.edit();
                        s.a(edit.putLong(":ts-" + this.a, System.currentTimeMillis()));
                        a(bc.f);
                    }
                }
            }
        }
    }

    private bc(Context context) {
        this.a = context.getApplicationContext();
        this.b = context.getSharedPreferences("sync", 0);
    }

    public static bc a(Context context) {
        if (f == null) {
            synchronized (bc.class) {
                if (f == null) {
                    f = new bc(context);
                }
            }
        }
        return f;
    }

    public String a(String str, String str2) {
        SharedPreferences sharedPreferences = this.b;
        return sharedPreferences.getString(str + Constants.COLON_SEPARATOR + str2, "");
    }

    @Override // com.xiaomi.push.service.ae
    public void a() {
        if (!this.d) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - this.c >= 3600000) {
                this.c = currentTimeMillis;
                this.d = true;
                aj.a(this.a).a(new y(this), (int) (Math.random() * 10.0d));
            }
        }
    }

    public void a(a aVar) {
        if (this.e.putIfAbsent(aVar.a, aVar) == null) {
            aj.a(this.a).a(aVar, ((int) (Math.random() * 30.0d)) + 10);
        }
    }

    public void a(String str, String str2, String str3) {
        SharedPreferences.Editor edit = f.b.edit();
        s.a(edit.putString(str + Constants.COLON_SEPARATOR + str2, str3));
    }
}
