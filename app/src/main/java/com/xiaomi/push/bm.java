package com.xiaomi.push;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.bx;

/* loaded from: classes4.dex */
class bm implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ bx.a b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public bm(bx.a aVar, Context context) {
        this.b = aVar;
        this.a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        SQLiteDatabase sQLiteDatabase;
        Exception e;
        try {
            sQLiteDatabase = null;
            try {
                sQLiteDatabase = this.b.a();
                if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                    sQLiteDatabase.beginTransaction();
                    this.b.a(this.a, sQLiteDatabase);
                    sQLiteDatabase.setTransactionSuccessful();
                }
                if (sQLiteDatabase != null) {
                    try {
                        sQLiteDatabase.endTransaction();
                    } catch (Exception e2) {
                        e = e2;
                        b.a(e);
                        this.b.a(this.a);
                    }
                }
                if (this.b.a != null) {
                    this.b.a.close();
                }
            } catch (Exception e3) {
                b.a(e3);
                if (sQLiteDatabase != null) {
                    try {
                        sQLiteDatabase.endTransaction();
                    } catch (Exception e4) {
                        e = e4;
                        b.a(e);
                        this.b.a(this.a);
                    }
                }
                if (this.b.a != null) {
                    this.b.a.close();
                }
            }
            this.b.a(this.a);
        } catch (Throwable th) {
            if (sQLiteDatabase != null) {
                try {
                    sQLiteDatabase.endTransaction();
                } catch (Exception e5) {
                    b.a(e5);
                    this.b.a(this.a);
                    throw th;
                }
            }
            if (this.b.a != null) {
                this.b.a.close();
            }
            this.b.a(this.a);
            throw th;
        }
    }
}
