package com.umeng.analytics.pro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteOpenHelper;
import com.umeng.commonsdk.debug.UMRTLog;

/* compiled from: CacheDBHelper.java */
/* loaded from: classes2.dex */
public class ak extends SQLiteOpenHelper {
    private static final Object b = new Object();
    private static ak c = null;
    private static final String d = "CREATE TABLE IF NOT EXISTS stf(_id INTEGER PRIMARY KEY AUTOINCREMENT, _tp TEXT, _hd TEXT, _bd TEXT, _ts TEXT, _uuid TEXT, _re1 TEXT, _re2 TEXT)";
    private static final String e = "DROP TABLE IF EXISTS stf";
    private static final String f = "DELETE FROM stf WHERE _id IN( SELECT _id FROM stf ORDER BY _id LIMIT 1)";
    private final Context a;

    public static final int a() {
        return 1;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public static ak a(Context context) {
        ak akVar;
        synchronized (b) {
            if (c == null) {
                c = new ak(context, am.b, null, 1);
            }
            akVar = c;
        }
        return akVar;
    }

    private ak(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i) {
        super(context, str, cursorFactory, i);
        this.a = context;
    }

    private void a(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL(e);
            sQLiteDatabase.execSQL(d);
        } catch (SQLException unused) {
        }
    }

    private void b(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL(d);
        } catch (SQLiteDatabaseCorruptException unused) {
            a(sQLiteDatabase);
        } catch (Throwable th) {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> [有状态]创建二级缓存数据库失败: " + th.getMessage());
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        b(sQLiteDatabase);
    }

    public void b() {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase != null && writableDatabase.isOpen()) {
                writableDatabase.close();
            }
        } catch (Throwable unused) {
        }
    }

    public void a(String str, ContentValues contentValues) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase != null && writableDatabase.isOpen()) {
                try {
                    writableDatabase.beginTransaction();
                    writableDatabase.insert(str, null, contentValues);
                    writableDatabase.setTransactionSuccessful();
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> [有状态]插入二级缓存数据记录 成功。");
                    if (writableDatabase == null) {
                        return;
                    }
                } catch (Throwable unused) {
                    if (writableDatabase == null) {
                        return;
                    }
                }
                writableDatabase.endTransaction();
                writableDatabase.close();
            }
        } catch (Throwable unused2) {
        }
    }

    public void a(String str, String str2, String[] strArr) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase != null && writableDatabase.isOpen()) {
                try {
                    writableDatabase.beginTransaction();
                    writableDatabase.delete(str, str2, strArr);
                    writableDatabase.setTransactionSuccessful();
                    if (writableDatabase == null) {
                        return;
                    }
                } catch (Throwable unused) {
                    if (writableDatabase == null) {
                        return;
                    }
                }
                writableDatabase.endTransaction();
                writableDatabase.close();
            }
        } catch (Throwable unused2) {
        }
    }

    private void d() {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase != null && writableDatabase.isOpen()) {
                try {
                    writableDatabase.execSQL(f);
                    if (writableDatabase == null) {
                        return;
                    }
                } catch (Throwable th) {
                    if (writableDatabase != null) {
                        writableDatabase.close();
                    }
                    throw th;
                }
                writableDatabase.close();
            }
        } catch (Throwable unused) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0072  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.umeng.analytics.pro.al a(java.lang.String r12) {
        /*
            r11 = this;
            r10 = 0
            java.lang.String r1 = "_uuid"
            java.lang.String r2 = "_tp"
            java.lang.String r3 = "_hd"
            java.lang.String r4 = "_bd"
            java.lang.String r5 = "_re1"
            java.lang.String r6 = "_re2"
            java.lang.String[] r3 = new java.lang.String[]{r1, r2, r3, r4, r5, r6}     // Catch: Throwable -> 0x0079, all -> 0x0076
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            java.lang.String r9 = "1"
            r1 = r11
            r2 = r12
            android.database.Cursor r1 = r1.a(r2, r3, r4, r5, r6, r7, r8, r9)     // Catch: Throwable -> 0x0079, all -> 0x0076
            if (r1 == 0) goto L_0x006f
            boolean r0 = r1.moveToFirst()     // Catch: Throwable -> 0x006c, all -> 0x006a
            if (r0 == 0) goto L_0x006f
            com.umeng.analytics.pro.al r0 = new com.umeng.analytics.pro.al     // Catch: Throwable -> 0x006c, all -> 0x006a
            r0.<init>()     // Catch: Throwable -> 0x006c, all -> 0x006a
            r2 = 0
            java.lang.String r2 = r1.getString(r2)     // Catch: Throwable -> 0x006d, all -> 0x006a
            r0.a = r2     // Catch: Throwable -> 0x006d, all -> 0x006a
            r2 = 1
            java.lang.String r2 = r1.getString(r2)     // Catch: Throwable -> 0x006d, all -> 0x006a
            r0.b = r2     // Catch: Throwable -> 0x006d, all -> 0x006a
            r2 = 2
            java.lang.String r2 = r1.getString(r2)     // Catch: Throwable -> 0x006d, all -> 0x006a
            r3 = 3
            java.lang.String r3 = r1.getString(r3)     // Catch: Throwable -> 0x006d, all -> 0x006a
            android.content.Context r4 = r11.a     // Catch: Throwable -> 0x006d, all -> 0x006a
            com.umeng.analytics.pro.h r4 = com.umeng.analytics.pro.h.a(r4)     // Catch: Throwable -> 0x006d, all -> 0x006a
            java.lang.String r2 = r4.d(r2)     // Catch: Throwable -> 0x006d, all -> 0x006a
            r0.c = r2     // Catch: Throwable -> 0x006d, all -> 0x006a
            android.content.Context r2 = r11.a     // Catch: Throwable -> 0x006d, all -> 0x006a
            com.umeng.analytics.pro.h r2 = com.umeng.analytics.pro.h.a(r2)     // Catch: Throwable -> 0x006d, all -> 0x006a
            java.lang.String r2 = r2.d(r3)     // Catch: Throwable -> 0x006d, all -> 0x006a
            r0.d = r2     // Catch: Throwable -> 0x006d, all -> 0x006a
            r2 = 4
            java.lang.String r2 = r1.getString(r2)     // Catch: Throwable -> 0x006d, all -> 0x006a
            r0.e = r2     // Catch: Throwable -> 0x006d, all -> 0x006a
            r2 = 5
            java.lang.String r2 = r1.getString(r2)     // Catch: Throwable -> 0x006d, all -> 0x006a
            r0.f = r2     // Catch: Throwable -> 0x006d, all -> 0x006a
            goto L_0x0070
        L_0x006a:
            r0 = move-exception
            goto L_0x0083
        L_0x006c:
            r0 = r10
        L_0x006d:
            r10 = r1
            goto L_0x007a
        L_0x006f:
            r0 = r10
        L_0x0070:
            if (r1 == 0) goto L_0x0082
            r1.close()
            goto L_0x0082
        L_0x0076:
            r0 = move-exception
            r1 = r10
            goto L_0x0083
        L_0x0079:
            r0 = r10
        L_0x007a:
            r11.d()     // Catch: all -> 0x0076
            if (r10 == 0) goto L_0x0082
            r10.close()
        L_0x0082:
            return r0
        L_0x0083:
            if (r1 == 0) goto L_0x0088
            r1.close()
        L_0x0088:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.ak.a(java.lang.String):com.umeng.analytics.pro.al");
    }

    public void a(String str, String str2) {
        a(str, "_uuid=?", new String[]{str2});
    }

    public Cursor a(String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase == null || !writableDatabase.isOpen()) {
                return null;
            }
            return writableDatabase.query(str, strArr, str2, strArr2, str3, str4, str5, str6);
        } catch (Throwable unused) {
            return null;
        }
    }

    public boolean b(String str) {
        SQLiteDatabase sQLiteDatabase;
        Throwable th;
        Cursor cursor = null;
        try {
            sQLiteDatabase = getWritableDatabase();
            if (sQLiteDatabase != null) {
                try {
                    if (sQLiteDatabase.isOpen()) {
                        cursor = sQLiteDatabase.query(str, null, null, null, null, null, null, null);
                    }
                } catch (Throwable unused) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (sQLiteDatabase == null) {
                        return false;
                    }
                    sQLiteDatabase.close();
                    return false;
                }
            }
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (sQLiteDatabase != null) {
                        sQLiteDatabase.close();
                    }
                    return true;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            if (sQLiteDatabase == null) {
                return false;
            }
        } catch (Throwable unused2) {
            sQLiteDatabase = null;
        }
        sQLiteDatabase.close();
        return false;
    }

    public boolean c() {
        return !b(am.c);
    }
}
