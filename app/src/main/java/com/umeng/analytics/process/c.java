package com.umeng.analytics.process;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.umeng.commonsdk.service.UMGlobalContext;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: UMProcessDBManager.java */
/* loaded from: classes2.dex */
class c {
    private static c a;
    private ConcurrentHashMap<String, a> b = new ConcurrentHashMap<>();
    private Context c;

    private c() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static c a(Context context) {
        if (a == null) {
            synchronized (c.class) {
                if (a == null) {
                    a = new c();
                }
            }
        }
        c cVar = a;
        cVar.c = context;
        return cVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized SQLiteDatabase a(String str) {
        return c(str).a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void b(String str) {
        c(str).b();
    }

    private a c(String str) {
        if (this.b.get(str) != null) {
            return this.b.get(str);
        }
        a a2 = a.a(this.c, str);
        this.b.put(str, a2);
        return a2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: UMProcessDBManager.java */
    /* loaded from: classes2.dex */
    public static class a {
        private AtomicInteger a = new AtomicInteger();
        private SQLiteOpenHelper b;
        private SQLiteDatabase c;

        private a() {
        }

        static a a(Context context, String str) {
            Context appContext = UMGlobalContext.getAppContext(context);
            a aVar = new a();
            aVar.b = b.a(appContext, str);
            return aVar;
        }

        synchronized SQLiteDatabase a() {
            if (this.a.incrementAndGet() == 1) {
                this.c = this.b.getWritableDatabase();
            }
            return this.c;
        }

        synchronized void b() {
            try {
                if (this.a.decrementAndGet() == 0) {
                    this.c.close();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
