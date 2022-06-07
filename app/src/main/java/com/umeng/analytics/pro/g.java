package com.umeng.analytics.pro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/* compiled from: UMDBUtils.java */
/* loaded from: classes2.dex */
public class g {
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0032, code lost:
        if (r1 != null) goto L_0x0034;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0034, code lost:
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x003f, code lost:
        if (r1 != null) goto L_0x0034;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0042, code lost:
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean a(java.lang.String r6, android.database.sqlite.SQLiteDatabase r7) {
        /*
            r0 = 0
            if (r6 != 0) goto L_0x0004
            return r0
        L_0x0004:
            r1 = 0
            r2 = 2
            java.lang.String[] r3 = new java.lang.String[r2]     // Catch: Exception -> 0x003f, all -> 0x0038
            java.lang.String r4 = "table"
            r3[r0] = r4     // Catch: Exception -> 0x003f, all -> 0x0038
            java.lang.String r4 = r6.trim()     // Catch: Exception -> 0x003f, all -> 0x0038
            r5 = 1
            r3[r5] = r4     // Catch: Exception -> 0x003f, all -> 0x0038
            java.lang.String r3 = "select count(*) as c from sqlite_master where type=? and name=?"
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch: Exception -> 0x003f, all -> 0x0038
            java.lang.String r4 = "table"
            r2[r0] = r4     // Catch: Exception -> 0x003f, all -> 0x0038
            java.lang.String r6 = r6.trim()     // Catch: Exception -> 0x003f, all -> 0x0038
            r2[r5] = r6     // Catch: Exception -> 0x003f, all -> 0x0038
            android.database.Cursor r1 = r7.rawQuery(r3, r2)     // Catch: Exception -> 0x003f, all -> 0x0038
            boolean r6 = r1.moveToNext()     // Catch: Exception -> 0x003f, all -> 0x0038
            if (r6 == 0) goto L_0x0032
            int r6 = r1.getInt(r0)     // Catch: Exception -> 0x003f, all -> 0x0038
            if (r6 <= 0) goto L_0x0032
            r0 = r5
        L_0x0032:
            if (r1 == 0) goto L_0x0042
        L_0x0034:
            r1.close()
            goto L_0x0042
        L_0x0038:
            r6 = move-exception
            if (r1 == 0) goto L_0x003e
            r1.close()
        L_0x003e:
            throw r6
        L_0x003f:
            if (r1 == 0) goto L_0x0042
            goto L_0x0034
        L_0x0042:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.g.a(java.lang.String, android.database.sqlite.SQLiteDatabase):boolean");
    }

    public static void a(Context context) {
        if (context != null) {
            try {
                File databasePath = context.getDatabasePath(d.b);
                if (databasePath != null && databasePath.exists()) {
                    databasePath.delete();
                }
                e.a(context).a();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static String b(Context context) {
        File databasePath = context.getDatabasePath(d.b);
        return databasePath.getParent() + File.separator;
    }

    public static String c(Context context) {
        return b(context) + "subprocess/";
    }

    public static String a(List<String> list) {
        return TextUtils.join("!", list);
    }

    public static List<String> a(String str) {
        return new ArrayList(Arrays.asList(str.split("!")));
    }

    public static List<String> b(List<String> list) {
        ArrayList arrayList = new ArrayList();
        try {
            for (String str : list) {
                if (Collections.frequency(arrayList, str) < 1) {
                    arrayList.add(str);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x002d, code lost:
        if (r0.isClosed() == false) goto L_0x002f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x002f, code lost:
        r0.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0046, code lost:
        if (r0.isClosed() == false) goto L_0x002f;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean a(android.database.sqlite.SQLiteDatabase r4, java.lang.String r5, java.lang.String r6) {
        /*
            r0 = 0
            r1 = 0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: Exception -> 0x0040, all -> 0x0033
            r2.<init>()     // Catch: Exception -> 0x0040, all -> 0x0033
            java.lang.String r3 = "SELECT * FROM "
            r2.append(r3)     // Catch: Exception -> 0x0040, all -> 0x0033
            r2.append(r5)     // Catch: Exception -> 0x0040, all -> 0x0033
            java.lang.String r5 = " LIMIT 0"
            r2.append(r5)     // Catch: Exception -> 0x0040, all -> 0x0033
            java.lang.String r5 = r2.toString()     // Catch: Exception -> 0x0040, all -> 0x0033
            android.database.Cursor r0 = r4.rawQuery(r5, r0)     // Catch: Exception -> 0x0040, all -> 0x0033
            if (r0 == 0) goto L_0x0027
            int r4 = r0.getColumnIndex(r6)     // Catch: Exception -> 0x0040, all -> 0x0033
            r5 = -1
            if (r4 == r5) goto L_0x0027
            r4 = 1
            r1 = r4
        L_0x0027:
            if (r0 == 0) goto L_0x0049
            boolean r4 = r0.isClosed()
            if (r4 != 0) goto L_0x0049
        L_0x002f:
            r0.close()
            goto L_0x0049
        L_0x0033:
            r4 = move-exception
            if (r0 == 0) goto L_0x003f
            boolean r5 = r0.isClosed()
            if (r5 != 0) goto L_0x003f
            r0.close()
        L_0x003f:
            throw r4
        L_0x0040:
            if (r0 == 0) goto L_0x0049
            boolean r4 = r0.isClosed()
            if (r4 != 0) goto L_0x0049
            goto L_0x002f
        L_0x0049:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.g.a(android.database.sqlite.SQLiteDatabase, java.lang.String, java.lang.String):boolean");
    }

    public static void a(SQLiteDatabase sQLiteDatabase, String str, String str2, String str3) {
        sQLiteDatabase.execSQL("alter table " + str + " add " + str2 + StringUtils.SPACE + str3);
    }
}
