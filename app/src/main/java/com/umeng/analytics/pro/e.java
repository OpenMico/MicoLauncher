package com.umeng.analytics.pro;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import com.umeng.analytics.pro.d;

/* compiled from: UMDBCreater.java */
/* loaded from: classes2.dex */
public class e extends SQLiteOpenHelper {
    private static Context b;
    private String a;

    /* compiled from: UMDBCreater.java */
    /* loaded from: classes2.dex */
    public static class a {
        private static final e a = new e(e.b, g.b(e.b), d.b, null, 2);

        private a() {
        }
    }

    public static e a(Context context) {
        if (b == null) {
            b = context.getApplicationContext();
        }
        return a.a;
    }

    private e(Context context, String str, String str2, SQLiteDatabase.CursorFactory cursorFactory, int i) {
        this(new b(context, str), str2, cursorFactory, i);
    }

    private e(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i) {
        super(context, TextUtils.isEmpty(str) ? d.b : str, cursorFactory, i);
        this.a = null;
        a();
    }

    public void a() {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (!g.a(d.C0138d.a, writableDatabase)) {
                c(writableDatabase);
            }
            if (!g.a(d.c.a, writableDatabase)) {
                d(writableDatabase);
            }
            if (!g.a(d.b.a, writableDatabase)) {
                b(writableDatabase);
            }
            if (!g.a(d.a.a, writableDatabase)) {
                a(writableDatabase);
            }
        } catch (Exception unused) {
        }
    }

    /*  JADX ERROR: NullPointerException in pass: BlockProcessor
        java.lang.NullPointerException
        */
    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(android.database.sqlite.SQLiteDatabase r2) {
        /*
            r1 = this;
            r2.beginTransaction()     // Catch: SQLiteDatabaseCorruptException -> 0x001d, Throwable -> 0x001a, all -> 0x0018
            r1.c(r2)     // Catch: SQLiteDatabaseCorruptException -> 0x001d, Throwable -> 0x001a, all -> 0x0018
            r1.d(r2)     // Catch: SQLiteDatabaseCorruptException -> 0x001d, Throwable -> 0x001a, all -> 0x0018
            r1.b(r2)     // Catch: SQLiteDatabaseCorruptException -> 0x001d, Throwable -> 0x001a, all -> 0x0018
            r1.a(r2)     // Catch: SQLiteDatabaseCorruptException -> 0x001d, Throwable -> 0x001a, all -> 0x0018
            r2.setTransactionSuccessful()     // Catch: SQLiteDatabaseCorruptException -> 0x001d, Throwable -> 0x001a, all -> 0x0018
            if (r2 == 0) goto L_0x0025
        L_0x0014:
            r2.endTransaction()     // Catch: Throwable -> 0x0025
            goto L_0x0025
        L_0x0018:
            r0 = move-exception
            goto L_0x0026
        L_0x001a:
            if (r2 == 0) goto L_0x0025
            goto L_0x0014
        L_0x001d:
            android.content.Context r0 = com.umeng.analytics.pro.e.b     // Catch: all -> 0x0018
            com.umeng.analytics.pro.g.a(r0)     // Catch: all -> 0x0018
            if (r2 == 0) goto L_0x0025
            goto L_0x0014
        L_0x0025:
            return
        L_0x0026:
            if (r2 == 0) goto L_0x002b
            r2.endTransaction()     // Catch: Throwable -> 0x002b
        L_0x002b:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.e.onCreate(android.database.sqlite.SQLiteDatabase):void");
    }

    private void a(SQLiteDatabase sQLiteDatabase) {
        try {
            this.a = "create table if not exists __er(id INTEGER primary key autoincrement, __i TEXT, __a TEXT, __t INTEGER, __av TEXT, __vc TEXT)";
            sQLiteDatabase.execSQL(this.a);
        } catch (SQLException unused) {
        }
    }

    private void b(SQLiteDatabase sQLiteDatabase) {
        try {
            this.a = "create table if not exists __et(id INTEGER primary key autoincrement, __i TEXT, __e TEXT, __s TEXT, __t INTEGER, __av TEXT, __vc TEXT)";
            sQLiteDatabase.execSQL(this.a);
        } catch (SQLException unused) {
        }
    }

    private void c(SQLiteDatabase sQLiteDatabase) {
        try {
            this.a = "create table if not exists __sd(id INTEGER primary key autoincrement, __ii TEXT unique, __a TEXT, __b TEXT, __c TEXT, __d TEXT, __e TEXT, __f TEXT, __g TEXT, __sp TEXT, __pp TEXT, __av TEXT, __vc TEXT)";
            sQLiteDatabase.execSQL(this.a);
        } catch (SQLException unused) {
        }
    }

    private void d(SQLiteDatabase sQLiteDatabase) {
        try {
            this.a = "create table if not exists __is(id INTEGER primary key autoincrement, __ii TEXT unique, __e TEXT, __sp TEXT, __pp TEXT, __av TEXT, __vc TEXT)";
            sQLiteDatabase.execSQL(this.a);
        } catch (SQLException unused) {
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i2 > i && i == 1) {
            try {
                try {
                    e(sQLiteDatabase);
                } catch (Exception unused) {
                    f(sQLiteDatabase);
                }
            } catch (Exception unused2) {
                e(sQLiteDatabase);
            }
        }
    }

    private void e(SQLiteDatabase sQLiteDatabase) {
        if (!g.a(sQLiteDatabase, d.C0138d.a, "__av")) {
            g.a(sQLiteDatabase, d.C0138d.a, "__sp", "TEXT");
            g.a(sQLiteDatabase, d.C0138d.a, "__pp", "TEXT");
            g.a(sQLiteDatabase, d.C0138d.a, "__av", "TEXT");
            g.a(sQLiteDatabase, d.C0138d.a, "__vc", "TEXT");
        }
        if (!g.a(sQLiteDatabase, d.b.a, "__av")) {
            g.a(sQLiteDatabase, d.b.a, "__av", "TEXT");
            g.a(sQLiteDatabase, d.b.a, "__vc", "TEXT");
        }
        if (!g.a(sQLiteDatabase, d.a.a, "__av")) {
            g.a(sQLiteDatabase, d.a.a, "__av", "TEXT");
            g.a(sQLiteDatabase, d.a.a, "__vc", "TEXT");
        }
    }

    private void f(SQLiteDatabase sQLiteDatabase) {
        a(sQLiteDatabase, d.C0138d.a);
        a(sQLiteDatabase, d.b.a);
        a(sQLiteDatabase, d.a.a);
        a();
    }

    private void a(SQLiteDatabase sQLiteDatabase, String str) {
        try {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + str);
        } catch (SQLException unused) {
        }
    }
}
