package com.umeng.analytics.pro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: UMDBManager.java */
/* loaded from: classes2.dex */
public class f {
    private static SQLiteOpenHelper b;
    private static Context d;
    private AtomicInteger a;
    private SQLiteDatabase c;

    private f() {
        this.a = new AtomicInteger();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: UMDBManager.java */
    /* loaded from: classes2.dex */
    public static class a {
        private static final f a = new f();

        private a() {
        }
    }

    public static f a(Context context) {
        if (d == null && context != null) {
            d = context.getApplicationContext();
            b = e.a(d);
        }
        return a.a;
    }

    public synchronized SQLiteDatabase a() {
        if (this.a.incrementAndGet() == 1) {
            this.c = b.getWritableDatabase();
        }
        return this.c;
    }

    public synchronized void b() {
        try {
            if (this.a.decrementAndGet() == 0) {
                this.c.close();
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
