package com.umeng.analytics.pro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import com.umeng.analytics.pro.d;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.service.UMGlobalContext;
import com.umeng.commonsdk.statistics.common.DataHelper;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import com.umeng.commonsdk.utils.UMUtils;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: UMStoreManager.java */
/* loaded from: classes2.dex */
public class h {
    public static final int a = 2049;
    public static final int b = 2050;
    private static final int c = 1000;
    private static Context d = null;
    private static String e = null;
    private static final String f = "umeng+";
    private static final String g = "ek__id";
    private static final String h = "ek_key";
    private List<String> i;
    private List<Integer> j;
    private String k;
    private List<String> l;

    /* compiled from: UMStoreManager.java */
    /* loaded from: classes2.dex */
    public enum a {
        AUTOPAGE,
        PAGE,
        BEGIN,
        END,
        NEWSESSION,
        INSTANTSESSIONBEGIN
    }

    /* compiled from: UMStoreManager.java */
    /* loaded from: classes2.dex */
    public static class b {
        private static final h a = new h();

        private b() {
        }
    }

    private h() {
        this.i = new ArrayList();
        this.j = new ArrayList();
        this.k = null;
        this.l = new ArrayList();
    }

    public static h a(Context context) {
        h hVar = b.a;
        if (d == null && context != null) {
            d = context.getApplicationContext();
            hVar.k();
        }
        return hVar;
    }

    private void k() {
        synchronized (this) {
            l();
            this.i.clear();
            this.l.clear();
            this.j.clear();
        }
    }

    public void a() {
        this.i.clear();
    }

    public void b() {
        this.l.clear();
    }

    public boolean c() {
        return this.l.isEmpty();
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x009a, code lost:
        if (r1 != null) goto L_0x00a7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00a5, code lost:
        if (r1 == null) goto L_0x00aa;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00a7, code lost:
        r1.endTransaction();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(org.json.JSONArray r8) {
        /*
            r7 = this;
            r0 = 0
            android.content.Context r1 = com.umeng.analytics.pro.h.d     // Catch: SQLiteDatabaseCorruptException -> 0x00b4, Throwable -> 0x00a4, all -> 0x00a1
            com.umeng.analytics.pro.f r1 = com.umeng.analytics.pro.f.a(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x00b4, Throwable -> 0x00a4, all -> 0x00a1
            android.database.sqlite.SQLiteDatabase r1 = r1.a()     // Catch: SQLiteDatabaseCorruptException -> 0x00b4, Throwable -> 0x00a4, all -> 0x00a1
            r1.beginTransaction()     // Catch: SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            r2 = 0
        L_0x000f:
            int r3 = r8.length()     // Catch: SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            if (r2 >= r3) goto L_0x0097
            org.json.JSONObject r3 = r8.getJSONObject(r2)     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            android.content.ContentValues r4 = new android.content.ContentValues     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            r4.<init>()     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            java.lang.String r5 = "__i"
            java.lang.String r5 = r3.optString(r5)     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            if (r6 != 0) goto L_0x0032
            java.lang.String r6 = "-1"
            boolean r6 = r6.equals(r5)     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            if (r6 == 0) goto L_0x0042
        L_0x0032:
            com.umeng.analytics.pro.t r5 = com.umeng.analytics.pro.t.a()     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            java.lang.String r5 = r5.b()     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            if (r6 == 0) goto L_0x0042
            java.lang.String r5 = "-1"
        L_0x0042:
            java.lang.String r6 = "__i"
            r4.put(r6, r5)     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            java.lang.String r5 = "__e"
            java.lang.String r6 = "id"
            java.lang.String r6 = r3.optString(r6)     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            r4.put(r5, r6)     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            java.lang.String r5 = "__t"
            java.lang.String r6 = "__t"
            int r6 = r3.optInt(r6)     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            r4.put(r5, r6)     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            java.lang.String r5 = "__av"
            android.content.Context r6 = com.umeng.analytics.pro.h.d     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            java.lang.String r6 = com.umeng.commonsdk.utils.UMUtils.getAppVersionName(r6)     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            r4.put(r5, r6)     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            java.lang.String r5 = "__vc"
            android.content.Context r6 = com.umeng.analytics.pro.h.d     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            java.lang.String r6 = com.umeng.commonsdk.utils.UMUtils.getAppVersionCode(r6)     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            r4.put(r5, r6)     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            java.lang.String r5 = "__i"
            r3.remove(r5)     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            java.lang.String r5 = "__t"
            r3.remove(r5)     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            java.lang.String r5 = "__s"
            java.lang.String r3 = r3.toString()     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            java.lang.String r3 = r7.c(r3)     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            r4.put(r5, r3)     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            java.lang.String r3 = "__et"
            r1.insert(r3, r0, r4)     // Catch: Exception -> 0x0093, SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
        L_0x0093:
            int r2 = r2 + 1
            goto L_0x000f
        L_0x0097:
            r1.setTransactionSuccessful()     // Catch: SQLiteDatabaseCorruptException -> 0x009f, Throwable -> 0x00a5, all -> 0x009d
            if (r1 == 0) goto L_0x00aa
            goto L_0x00a7
        L_0x009d:
            r8 = move-exception
            goto L_0x00c0
        L_0x009f:
            r0 = r1
            goto L_0x00b4
        L_0x00a1:
            r8 = move-exception
            r1 = r0
            goto L_0x00c0
        L_0x00a4:
            r1 = r0
        L_0x00a5:
            if (r1 == 0) goto L_0x00aa
        L_0x00a7:
            r1.endTransaction()     // Catch: Throwable -> 0x00aa
        L_0x00aa:
            android.content.Context r8 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r8 = com.umeng.analytics.pro.f.a(r8)
            r8.b()
            goto L_0x00bf
        L_0x00b4:
            android.content.Context r8 = com.umeng.analytics.pro.h.d     // Catch: all -> 0x00a1
            com.umeng.analytics.pro.g.a(r8)     // Catch: all -> 0x00a1
            if (r0 == 0) goto L_0x00aa
            r0.endTransaction()     // Catch: Throwable -> 0x00aa
            goto L_0x00aa
        L_0x00bf:
            return
        L_0x00c0:
            if (r1 == 0) goto L_0x00c5
            r1.endTransaction()     // Catch: Throwable -> 0x00c5
        L_0x00c5:
            android.content.Context r0 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r0 = com.umeng.analytics.pro.f.a(r0)
            r0.b()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.h.a(org.json.JSONArray):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0059, code lost:
        if (r1 == null) goto L_0x005e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x005b, code lost:
        r1.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x004e, code lost:
        if (r1 != null) goto L_0x005b;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean a(java.lang.String r5, java.lang.String r6, int r7) {
        /*
            r4 = this;
            r0 = 0
            android.content.Context r1 = com.umeng.analytics.pro.h.d     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0055
            com.umeng.analytics.pro.f r1 = com.umeng.analytics.pro.f.a(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0055
            android.database.sqlite.SQLiteDatabase r1 = r1.a()     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0055
            r1.beginTransaction()     // Catch: SQLiteDatabaseCorruptException -> 0x0053, Throwable -> 0x0059, all -> 0x0051
            android.content.ContentValues r2 = new android.content.ContentValues     // Catch: SQLiteDatabaseCorruptException -> 0x0053, Throwable -> 0x0059, all -> 0x0051
            r2.<init>()     // Catch: SQLiteDatabaseCorruptException -> 0x0053, Throwable -> 0x0059, all -> 0x0051
            java.lang.String r3 = "__i"
            r2.put(r3, r5)     // Catch: SQLiteDatabaseCorruptException -> 0x0053, Throwable -> 0x0059, all -> 0x0051
            java.lang.String r5 = r4.c(r6)     // Catch: SQLiteDatabaseCorruptException -> 0x0053, Throwable -> 0x0059, all -> 0x0051
            boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch: SQLiteDatabaseCorruptException -> 0x0053, Throwable -> 0x0059, all -> 0x0051
            if (r6 != 0) goto L_0x004b
            java.lang.String r6 = "__a"
            r2.put(r6, r5)     // Catch: SQLiteDatabaseCorruptException -> 0x0053, Throwable -> 0x0059, all -> 0x0051
            java.lang.String r5 = "__t"
            java.lang.Integer r6 = java.lang.Integer.valueOf(r7)     // Catch: SQLiteDatabaseCorruptException -> 0x0053, Throwable -> 0x0059, all -> 0x0051
            r2.put(r5, r6)     // Catch: SQLiteDatabaseCorruptException -> 0x0053, Throwable -> 0x0059, all -> 0x0051
            java.lang.String r5 = "__av"
            android.content.Context r6 = com.umeng.analytics.pro.h.d     // Catch: SQLiteDatabaseCorruptException -> 0x0053, Throwable -> 0x0059, all -> 0x0051
            java.lang.String r6 = com.umeng.commonsdk.utils.UMUtils.getAppVersionName(r6)     // Catch: SQLiteDatabaseCorruptException -> 0x0053, Throwable -> 0x0059, all -> 0x0051
            r2.put(r5, r6)     // Catch: SQLiteDatabaseCorruptException -> 0x0053, Throwable -> 0x0059, all -> 0x0051
            java.lang.String r5 = "__vc"
            android.content.Context r6 = com.umeng.analytics.pro.h.d     // Catch: SQLiteDatabaseCorruptException -> 0x0053, Throwable -> 0x0059, all -> 0x0051
            java.lang.String r6 = com.umeng.commonsdk.utils.UMUtils.getAppVersionCode(r6)     // Catch: SQLiteDatabaseCorruptException -> 0x0053, Throwable -> 0x0059, all -> 0x0051
            r2.put(r5, r6)     // Catch: SQLiteDatabaseCorruptException -> 0x0053, Throwable -> 0x0059, all -> 0x0051
            java.lang.String r5 = "__er"
            r1.insert(r5, r0, r2)     // Catch: SQLiteDatabaseCorruptException -> 0x0053, Throwable -> 0x0059, all -> 0x0051
        L_0x004b:
            r1.setTransactionSuccessful()     // Catch: SQLiteDatabaseCorruptException -> 0x0053, Throwable -> 0x0059, all -> 0x0051
            if (r1 == 0) goto L_0x005e
            goto L_0x005b
        L_0x0051:
            r5 = move-exception
            goto L_0x0075
        L_0x0053:
            r0 = r1
            goto L_0x0068
        L_0x0055:
            r5 = move-exception
            r1 = r0
            goto L_0x0075
        L_0x0058:
            r1 = r0
        L_0x0059:
            if (r1 == 0) goto L_0x005e
        L_0x005b:
            r1.endTransaction()     // Catch: Throwable -> 0x005e
        L_0x005e:
            android.content.Context r5 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r5 = com.umeng.analytics.pro.f.a(r5)
            r5.b()
            goto L_0x0073
        L_0x0068:
            android.content.Context r5 = com.umeng.analytics.pro.h.d     // Catch: all -> 0x0055
            com.umeng.analytics.pro.g.a(r5)     // Catch: all -> 0x0055
            if (r0 == 0) goto L_0x005e
            r0.endTransaction()     // Catch: Throwable -> 0x005e
            goto L_0x005e
        L_0x0073:
            r5 = 0
            return r5
        L_0x0075:
            if (r1 == 0) goto L_0x007a
            r1.endTransaction()     // Catch: Throwable -> 0x007a
        L_0x007a:
            android.content.Context r6 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r6 = com.umeng.analytics.pro.f.a(r6)
            r6.b()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.h.a(java.lang.String, java.lang.String, int):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x006b, code lost:
        if (r0 != null) goto L_0x0072;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0070, code lost:
        if (r0 != null) goto L_0x0072;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0072, code lost:
        r0.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0084, code lost:
        if (r0 == null) goto L_0x0075;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void d() {
        /*
            r6 = this;
            r0 = 0
            android.content.Context r1 = com.umeng.analytics.pro.h.d     // Catch: SQLiteDatabaseCorruptException -> 0x007f, Throwable -> 0x0070, all -> 0x006e
            com.umeng.analytics.pro.f r1 = com.umeng.analytics.pro.f.a(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x007f, Throwable -> 0x0070, all -> 0x006e
            android.database.sqlite.SQLiteDatabase r0 = r1.a()     // Catch: SQLiteDatabaseCorruptException -> 0x007f, Throwable -> 0x0070, all -> 0x006e
            r0.beginTransaction()     // Catch: SQLiteDatabaseCorruptException -> 0x007f, Throwable -> 0x0070, all -> 0x006e
            com.umeng.analytics.pro.t r1 = com.umeng.analytics.pro.t.a()     // Catch: SQLiteDatabaseCorruptException -> 0x007f, Throwable -> 0x0070, all -> 0x006e
            java.lang.String r1 = r1.c()     // Catch: SQLiteDatabaseCorruptException -> 0x007f, Throwable -> 0x0070, all -> 0x006e
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x007f, Throwable -> 0x0070, all -> 0x006e
            if (r2 == 0) goto L_0x002b
            if (r0 == 0) goto L_0x0021
            r0.endTransaction()     // Catch: Throwable -> 0x0021
        L_0x0021:
            android.content.Context r0 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r0 = com.umeng.analytics.pro.f.a(r0)
            r0.b()
            return
        L_0x002b:
            java.lang.String r2 = ""
            java.lang.String r3 = "-1"
            java.lang.String[] r2 = new java.lang.String[]{r2, r3}     // Catch: SQLiteDatabaseCorruptException -> 0x007f, Throwable -> 0x0070, all -> 0x006e
            r3 = 0
        L_0x0034:
            int r4 = r2.length     // Catch: SQLiteDatabaseCorruptException -> 0x007f, Throwable -> 0x0070, all -> 0x006e
            if (r3 >= r4) goto L_0x0068
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: SQLiteDatabaseCorruptException -> 0x007f, Throwable -> 0x0070, all -> 0x006e
            r4.<init>()     // Catch: SQLiteDatabaseCorruptException -> 0x007f, Throwable -> 0x0070, all -> 0x006e
            java.lang.String r5 = "update __et set __i=\""
            r4.append(r5)     // Catch: SQLiteDatabaseCorruptException -> 0x007f, Throwable -> 0x0070, all -> 0x006e
            r4.append(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x007f, Throwable -> 0x0070, all -> 0x006e
            java.lang.String r5 = "\" where "
            r4.append(r5)     // Catch: SQLiteDatabaseCorruptException -> 0x007f, Throwable -> 0x0070, all -> 0x006e
            java.lang.String r5 = "__i"
            r4.append(r5)     // Catch: SQLiteDatabaseCorruptException -> 0x007f, Throwable -> 0x0070, all -> 0x006e
            java.lang.String r5 = "=\""
            r4.append(r5)     // Catch: SQLiteDatabaseCorruptException -> 0x007f, Throwable -> 0x0070, all -> 0x006e
            r5 = r2[r3]     // Catch: SQLiteDatabaseCorruptException -> 0x007f, Throwable -> 0x0070, all -> 0x006e
            r4.append(r5)     // Catch: SQLiteDatabaseCorruptException -> 0x007f, Throwable -> 0x0070, all -> 0x006e
            java.lang.String r5 = "\""
            r4.append(r5)     // Catch: SQLiteDatabaseCorruptException -> 0x007f, Throwable -> 0x0070, all -> 0x006e
            java.lang.String r4 = r4.toString()     // Catch: SQLiteDatabaseCorruptException -> 0x007f, Throwable -> 0x0070, all -> 0x006e
            r0.execSQL(r4)     // Catch: SQLiteDatabaseCorruptException -> 0x007f, Throwable -> 0x0070, all -> 0x006e
            int r3 = r3 + 1
            goto L_0x0034
        L_0x0068:
            r0.setTransactionSuccessful()     // Catch: SQLiteDatabaseCorruptException -> 0x007f, Throwable -> 0x0070, all -> 0x006e
            if (r0 == 0) goto L_0x0075
            goto L_0x0072
        L_0x006e:
            r1 = move-exception
            goto L_0x0088
        L_0x0070:
            if (r0 == 0) goto L_0x0075
        L_0x0072:
            r0.endTransaction()     // Catch: Throwable -> 0x0075
        L_0x0075:
            android.content.Context r0 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r0 = com.umeng.analytics.pro.f.a(r0)
            r0.b()
            goto L_0x0087
        L_0x007f:
            android.content.Context r1 = com.umeng.analytics.pro.h.d     // Catch: all -> 0x006e
            com.umeng.analytics.pro.g.a(r1)     // Catch: all -> 0x006e
            if (r0 == 0) goto L_0x0075
            goto L_0x0072
        L_0x0087:
            return
        L_0x0088:
            if (r0 == 0) goto L_0x008d
            r0.endTransaction()     // Catch: Throwable -> 0x008d
        L_0x008d:
            android.content.Context r0 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r0 = com.umeng.analytics.pro.f.a(r0)
            r0.b()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.h.d():void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x007f, code lost:
        if (r2 != null) goto L_0x008c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x008a, code lost:
        if (r2 == null) goto L_0x008f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x008c, code lost:
        r2.endTransaction();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean a(java.lang.String r6, org.json.JSONObject r7, com.umeng.analytics.pro.h.a r8) {
        /*
            r5 = this;
            r0 = 0
            if (r7 != 0) goto L_0x0004
            return r0
        L_0x0004:
            r1 = 0
            android.content.Context r2 = com.umeng.analytics.pro.h.d     // Catch: SQLiteDatabaseCorruptException -> 0x0099, Throwable -> 0x0089, all -> 0x0086
            com.umeng.analytics.pro.f r2 = com.umeng.analytics.pro.f.a(r2)     // Catch: SQLiteDatabaseCorruptException -> 0x0099, Throwable -> 0x0089, all -> 0x0086
            android.database.sqlite.SQLiteDatabase r2 = r2.a()     // Catch: SQLiteDatabaseCorruptException -> 0x0099, Throwable -> 0x0089, all -> 0x0086
            r2.beginTransaction()     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            com.umeng.analytics.pro.h$a r3 = com.umeng.analytics.pro.h.a.BEGIN     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            if (r8 != r3) goto L_0x0051
            java.lang.String r8 = "__e"
            java.lang.Object r7 = r7.opt(r8)     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            java.lang.Long r7 = (java.lang.Long) r7     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            long r7 = r7.longValue()     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            android.content.ContentValues r3 = new android.content.ContentValues     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            r3.<init>()     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            java.lang.String r4 = "__ii"
            r3.put(r4, r6)     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            java.lang.String r6 = "__e"
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            r3.put(r6, r7)     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            java.lang.String r6 = "__av"
            android.content.Context r7 = com.umeng.analytics.pro.h.d     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            java.lang.String r7 = com.umeng.commonsdk.utils.UMUtils.getAppVersionName(r7)     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            r3.put(r6, r7)     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            java.lang.String r6 = "__vc"
            android.content.Context r7 = com.umeng.analytics.pro.h.d     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            java.lang.String r7 = com.umeng.commonsdk.utils.UMUtils.getAppVersionCode(r7)     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            r3.put(r6, r7)     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            java.lang.String r6 = "__sd"
            r2.insert(r6, r1, r3)     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            goto L_0x007c
        L_0x0051:
            com.umeng.analytics.pro.h$a r1 = com.umeng.analytics.pro.h.a.INSTANTSESSIONBEGIN     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            if (r8 != r1) goto L_0x0059
            r5.b(r6, r7, r2)     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            goto L_0x007c
        L_0x0059:
            com.umeng.analytics.pro.h$a r1 = com.umeng.analytics.pro.h.a.END     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            if (r8 != r1) goto L_0x0061
            r5.a(r6, r7, r2)     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            goto L_0x007c
        L_0x0061:
            com.umeng.analytics.pro.h$a r1 = com.umeng.analytics.pro.h.a.PAGE     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            if (r8 != r1) goto L_0x006b
            java.lang.String r8 = "__a"
            r5.a(r6, r7, r2, r8)     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            goto L_0x007c
        L_0x006b:
            com.umeng.analytics.pro.h$a r1 = com.umeng.analytics.pro.h.a.AUTOPAGE     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            if (r8 != r1) goto L_0x0075
            java.lang.String r8 = "__b"
            r5.a(r6, r7, r2, r8)     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            goto L_0x007c
        L_0x0075:
            com.umeng.analytics.pro.h$a r1 = com.umeng.analytics.pro.h.a.NEWSESSION     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            if (r8 != r1) goto L_0x007c
            r5.c(r6, r7, r2)     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
        L_0x007c:
            r2.setTransactionSuccessful()     // Catch: SQLiteDatabaseCorruptException -> 0x0084, Throwable -> 0x008a, all -> 0x0082
            if (r2 == 0) goto L_0x008f
            goto L_0x008c
        L_0x0082:
            r6 = move-exception
            goto L_0x00a5
        L_0x0084:
            r1 = r2
            goto L_0x0099
        L_0x0086:
            r6 = move-exception
            r2 = r1
            goto L_0x00a5
        L_0x0089:
            r2 = r1
        L_0x008a:
            if (r2 == 0) goto L_0x008f
        L_0x008c:
            r2.endTransaction()     // Catch: Throwable -> 0x008f
        L_0x008f:
            android.content.Context r6 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r6 = com.umeng.analytics.pro.f.a(r6)
            r6.b()
            goto L_0x00a4
        L_0x0099:
            android.content.Context r6 = com.umeng.analytics.pro.h.d     // Catch: all -> 0x0086
            com.umeng.analytics.pro.g.a(r6)     // Catch: all -> 0x0086
            if (r1 == 0) goto L_0x008f
            r1.endTransaction()     // Catch: Throwable -> 0x008f
            goto L_0x008f
        L_0x00a4:
            return r0
        L_0x00a5:
            if (r2 == 0) goto L_0x00aa
            r2.endTransaction()     // Catch: Throwable -> 0x00aa
        L_0x00aa:
            android.content.Context r7 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r7 = com.umeng.analytics.pro.f.a(r7)
            r7.b()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.h.a(java.lang.String, org.json.JSONObject, com.umeng.analytics.pro.h$a):boolean");
    }

    private void a(String str, JSONObject jSONObject, SQLiteDatabase sQLiteDatabase) {
        try {
            long longValue = ((Long) jSONObject.opt(d.C0138d.a.g)).longValue();
            long j = 0;
            Object opt = jSONObject.opt(d.C0138d.a.h);
            if (opt != null && (opt instanceof Long)) {
                j = ((Long) opt).longValue();
            }
            JSONObject optJSONObject = jSONObject.optJSONObject("__sp");
            JSONObject optJSONObject2 = jSONObject.optJSONObject("__pp");
            String str2 = "";
            String str3 = "";
            if (optJSONObject != null && optJSONObject.length() > 0) {
                str2 = c(optJSONObject.toString());
            }
            if (optJSONObject2 != null && optJSONObject2.length() > 0) {
                str3 = c(optJSONObject2.toString());
            }
            sQLiteDatabase.execSQL("update __sd set __f=\"" + longValue + "\", " + d.C0138d.a.h + "=\"" + j + "\", __sp=\"" + str2 + "\", __pp=\"" + str3 + "\" where __ii=\"" + str + "\"");
        } catch (Throwable unused) {
        }
    }

    private void b(String str, JSONObject jSONObject, SQLiteDatabase sQLiteDatabase) {
        try {
            long longValue = ((Long) jSONObject.get("__e")).longValue();
            JSONObject optJSONObject = jSONObject.optJSONObject("__sp");
            JSONObject optJSONObject2 = jSONObject.optJSONObject("__pp");
            String str2 = "";
            String str3 = "";
            if (optJSONObject != null && optJSONObject.length() > 0) {
                str2 = c(optJSONObject.toString());
            }
            if (optJSONObject2 != null && optJSONObject2.length() > 0) {
                str3 = c(optJSONObject2.toString());
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("__ii", str);
            contentValues.put("__e", String.valueOf(longValue));
            contentValues.put("__sp", str2);
            contentValues.put("__pp", str3);
            contentValues.put("__av", UMGlobalContext.getInstance(d).getAppVersion());
            contentValues.put("__vc", UMUtils.getAppVersionCode(d));
            sQLiteDatabase.insert(d.c.a, null, contentValues);
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0051, code lost:
        if (r4 != null) goto L_0x0053;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0053, code lost:
        r4.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0030, code lost:
        if (r4 != null) goto L_0x0053;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public long a(java.lang.String r8) {
        /*
            r7 = this;
            java.lang.String r0 = "select __f from __sd where __ii=?"
            r1 = 0
            r2 = 0
            android.content.Context r4 = com.umeng.analytics.pro.h.d     // Catch: Exception -> 0x004b, all -> 0x0035
            com.umeng.analytics.pro.f r4 = com.umeng.analytics.pro.f.a(r4)     // Catch: Exception -> 0x004b, all -> 0x0035
            android.database.sqlite.SQLiteDatabase r4 = r4.a()     // Catch: Exception -> 0x004b, all -> 0x0035
            r4.beginTransaction()     // Catch: Exception -> 0x004c, all -> 0x0033
            r5 = 1
            java.lang.String[] r5 = new java.lang.String[r5]     // Catch: Exception -> 0x004c, all -> 0x0033
            r6 = 0
            r5[r6] = r8     // Catch: Exception -> 0x004c, all -> 0x0033
            android.database.Cursor r1 = r4.rawQuery(r0, r5)     // Catch: Exception -> 0x004c, all -> 0x0033
            if (r1 == 0) goto L_0x002b
            r1.moveToFirst()     // Catch: Exception -> 0x004c, all -> 0x0033
            java.lang.String r8 = "__f"
            int r8 = r1.getColumnIndex(r8)     // Catch: Exception -> 0x004c, all -> 0x0033
            long r2 = r1.getLong(r8)     // Catch: Exception -> 0x004c, all -> 0x0033
        L_0x002b:
            if (r1 == 0) goto L_0x0030
            r1.close()     // Catch: Exception -> 0x0056
        L_0x0030:
            if (r4 == 0) goto L_0x0056
            goto L_0x0053
        L_0x0033:
            r8 = move-exception
            goto L_0x0037
        L_0x0035:
            r8 = move-exception
            r4 = r1
        L_0x0037:
            if (r1 == 0) goto L_0x003c
            r1.close()     // Catch: Exception -> 0x0041
        L_0x003c:
            if (r4 == 0) goto L_0x0041
            r4.endTransaction()     // Catch: Exception -> 0x0041
        L_0x0041:
            android.content.Context r0 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r0 = com.umeng.analytics.pro.f.a(r0)
            r0.b()
            throw r8
        L_0x004b:
            r4 = r1
        L_0x004c:
            if (r1 == 0) goto L_0x0051
            r1.close()     // Catch: Exception -> 0x0056
        L_0x0051:
            if (r4 == 0) goto L_0x0056
        L_0x0053:
            r4.endTransaction()     // Catch: Exception -> 0x0056
        L_0x0056:
            android.content.Context r8 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r8 = com.umeng.analytics.pro.f.a(r8)
            r8.b()
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.h.a(java.lang.String):long");
    }

    private void c(String str, JSONObject jSONObject, SQLiteDatabase sQLiteDatabase) {
        Cursor cursor;
        Throwable th;
        String str2 = null;
        try {
            JSONObject optJSONObject = jSONObject.optJSONObject(d.C0138d.a.e);
            if (optJSONObject != null) {
                cursor = sQLiteDatabase.rawQuery("select __d from __sd where __ii=?", new String[]{str});
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        try {
                            str2 = d(cursor.getString(cursor.getColumnIndex(d.C0138d.a.e)));
                        } catch (Throwable unused) {
                            if (cursor == null) {
                                return;
                            }
                            cursor.close();
                        }
                    }
                }
            } else {
                cursor = null;
            }
            if (optJSONObject != null) {
                JSONArray jSONArray = new JSONArray();
                if (!TextUtils.isEmpty(str2)) {
                    jSONArray = new JSONArray(str2);
                }
                jSONArray.put(optJSONObject);
                String c2 = c(jSONArray.toString());
                if (!TextUtils.isEmpty(c2)) {
                    sQLiteDatabase.execSQL("update  __sd set __d=\"" + c2 + "\" where __ii=\"" + str + "\"");
                }
            }
            JSONObject optJSONObject2 = jSONObject.optJSONObject(d.C0138d.a.d);
            if (optJSONObject2 != null) {
                String c3 = c(optJSONObject2.toString());
                if (!TextUtils.isEmpty(c3)) {
                    sQLiteDatabase.execSQL("update  __sd set __c=\"" + c3 + "\" where __ii=\"" + str + "\"");
                }
            }
            long optLong = jSONObject.optLong(d.C0138d.a.g);
            sQLiteDatabase.execSQL("update  __sd set __f=\"" + String.valueOf(optLong) + "\" where __ii=\"" + str + "\"");
            if (cursor == null) {
                return;
            }
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
        }
        cursor.close();
    }

    private void a(String str, JSONObject jSONObject, SQLiteDatabase sQLiteDatabase, String str2) throws JSONException {
        Cursor cursor;
        Throwable th;
        JSONArray jSONArray;
        int i;
        JSONArray jSONArray2;
        String str3 = null;
        try {
            if ("__a".equals(str2)) {
                jSONArray = jSONObject.optJSONArray("__a");
                if (jSONArray == null || jSONArray.length() <= 0) {
                    return;
                }
            } else if (d.C0138d.a.c.equals(str2)) {
                jSONArray = jSONObject.optJSONArray(d.C0138d.a.c);
                if (jSONArray == null || jSONArray.length() <= 0) {
                    return;
                }
            } else {
                jSONArray = null;
            }
            cursor = sQLiteDatabase.rawQuery("select " + str2 + " from " + d.C0138d.a + " where __ii=?", new String[]{str});
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    try {
                        str3 = d(cursor.getString(cursor.getColumnIndex(str2)));
                    } catch (Throwable th2) {
                        th = th2;
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw th;
                    }
                }
            }
            jSONArray2 = new JSONArray();
            if (!TextUtils.isEmpty(str3)) {
                jSONArray2 = new JSONArray(str3);
            }
        } catch (Throwable unused) {
            cursor = null;
        }
        if (jSONArray2.length() <= 1000) {
            for (i = 0; i < jSONArray.length(); i++) {
                try {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                    if (jSONObject2 != null) {
                        jSONArray2.put(jSONObject2);
                    }
                } catch (JSONException unused2) {
                }
            }
            String c2 = c(jSONArray2.toString());
            if (!TextUtils.isEmpty(c2)) {
                sQLiteDatabase.execSQL("update __sd set " + str2 + "=\"" + c2 + "\" where __ii=\"" + str + "\"");
            }
            if (cursor == null) {
                return;
            }
            cursor.close();
        } else if (cursor != null) {
            cursor.close();
        }
    }

    public boolean e() {
        return this.i.isEmpty();
    }

    public JSONObject a(boolean z) {
        a();
        this.j.clear();
        JSONObject jSONObject = new JSONObject();
        if (!z) {
            a(jSONObject, z);
            b(jSONObject, (String) null);
            a(jSONObject, (String) null);
        } else {
            String a2 = a(jSONObject, z);
            if (!TextUtils.isEmpty(a2)) {
                b(jSONObject, a2);
                a(jSONObject, a2);
            }
        }
        return jSONObject;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0062, code lost:
        if (r0 != 0) goto L_0x007c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x007a, code lost:
        if (r0 != 0) goto L_0x007c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x007c, code lost:
        r0.endTransaction();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v18, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r0v2, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r0v5, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v19 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v6, types: [org.json.JSONObject] */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.json.JSONObject f() {
        /*
            r7 = this;
            java.util.List<java.lang.String> r0 = r7.l
            boolean r0 = r0.isEmpty()
            r1 = 0
            if (r0 == 0) goto L_0x000a
            return r1
        L_0x000a:
            android.content.Context r0 = com.umeng.analytics.pro.h.d     // Catch: SQLiteDatabaseCorruptException -> 0x0089, Throwable -> 0x0073, all -> 0x006e
            com.umeng.analytics.pro.f r0 = com.umeng.analytics.pro.f.a(r0)     // Catch: SQLiteDatabaseCorruptException -> 0x0089, Throwable -> 0x0073, all -> 0x006e
            android.database.sqlite.SQLiteDatabase r0 = r0.a()     // Catch: SQLiteDatabaseCorruptException -> 0x0089, Throwable -> 0x0073, all -> 0x006e
            r0.beginTransaction()     // Catch: SQLiteDatabaseCorruptException -> 0x006c, Throwable -> 0x006a, all -> 0x00a5
            java.util.List<java.lang.String> r2 = r7.l     // Catch: SQLiteDatabaseCorruptException -> 0x006c, Throwable -> 0x006a, all -> 0x00a5
            r3 = 0
            java.lang.Object r2 = r2.get(r3)     // Catch: SQLiteDatabaseCorruptException -> 0x006c, Throwable -> 0x006a, all -> 0x00a5
            java.lang.String r2 = (java.lang.String) r2     // Catch: SQLiteDatabaseCorruptException -> 0x006c, Throwable -> 0x006a, all -> 0x00a5
            java.lang.String r4 = "select *  from __is where __ii=?"
            r5 = 1
            java.lang.String[] r5 = new java.lang.String[r5]     // Catch: SQLiteDatabaseCorruptException -> 0x006c, Throwable -> 0x006a, all -> 0x00a5
            r5[r3] = r2     // Catch: SQLiteDatabaseCorruptException -> 0x006c, Throwable -> 0x006a, all -> 0x00a5
            android.database.Cursor r2 = r0.rawQuery(r4, r5)     // Catch: SQLiteDatabaseCorruptException -> 0x006c, Throwable -> 0x006a, all -> 0x00a5
            if (r2 == 0) goto L_0x005a
            boolean r3 = r2.moveToNext()     // Catch: SQLiteDatabaseCorruptException -> 0x0067, Throwable -> 0x0075, all -> 0x0065
            if (r3 == 0) goto L_0x005a
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch: SQLiteDatabaseCorruptException -> 0x0067, Throwable -> 0x0075, all -> 0x0065
            r3.<init>()     // Catch: SQLiteDatabaseCorruptException -> 0x0067, Throwable -> 0x0075, all -> 0x0065
            java.lang.String r1 = "__av"
            int r1 = r2.getColumnIndex(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0065
            java.lang.String r1 = r2.getString(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0065
            java.lang.String r4 = "__vc"
            int r4 = r2.getColumnIndex(r4)     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0065
            java.lang.String r4 = r2.getString(r4)     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0065
            java.lang.String r5 = "__av"
            r3.put(r5, r1)     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0065
            java.lang.String r1 = "__vc"
            r3.put(r1, r4)     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0065
            r1 = r3
            goto L_0x005a
        L_0x0058:
            r1 = r3
            goto L_0x0075
        L_0x005a:
            r0.setTransactionSuccessful()     // Catch: SQLiteDatabaseCorruptException -> 0x0067, Throwable -> 0x0075, all -> 0x0065
            if (r2 == 0) goto L_0x0062
            r2.close()
        L_0x0062:
            if (r0 == 0) goto L_0x007f
            goto L_0x007c
        L_0x0065:
            r1 = move-exception
            goto L_0x00a9
        L_0x0067:
            r3 = r1
        L_0x0068:
            r1 = r2
            goto L_0x008b
        L_0x006a:
            r2 = r1
            goto L_0x0075
        L_0x006c:
            r3 = r1
            goto L_0x008b
        L_0x006e:
            r0 = move-exception
            r2 = r1
            r1 = r0
            r0 = r2
            goto L_0x00a9
        L_0x0073:
            r0 = r1
            r2 = r0
        L_0x0075:
            if (r2 == 0) goto L_0x007a
            r2.close()
        L_0x007a:
            if (r0 == 0) goto L_0x007f
        L_0x007c:
            r0.endTransaction()     // Catch: Throwable -> 0x007f
        L_0x007f:
            android.content.Context r0 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r0 = com.umeng.analytics.pro.f.a(r0)
            r0.b()
            goto L_0x00a4
        L_0x0089:
            r0 = r1
            r3 = r0
        L_0x008b:
            android.content.Context r2 = com.umeng.analytics.pro.h.d     // Catch: all -> 0x00a5
            com.umeng.analytics.pro.g.a(r2)     // Catch: all -> 0x00a5
            if (r1 == 0) goto L_0x0095
            r1.close()
        L_0x0095:
            if (r0 == 0) goto L_0x009a
            r0.endTransaction()     // Catch: Throwable -> 0x009a
        L_0x009a:
            android.content.Context r0 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r0 = com.umeng.analytics.pro.f.a(r0)
            r0.b()
            r1 = r3
        L_0x00a4:
            return r1
        L_0x00a5:
            r2 = move-exception
            r6 = r2
            r2 = r1
            r1 = r6
        L_0x00a9:
            if (r2 == 0) goto L_0x00ae
            r2.close()
        L_0x00ae:
            if (r0 == 0) goto L_0x00b3
            r0.endTransaction()     // Catch: Throwable -> 0x00b3
        L_0x00b3:
            android.content.Context r0 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r0 = com.umeng.analytics.pro.f.a(r0)
            r0.b()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.h.f():org.json.JSONObject");
    }

    public JSONObject b(boolean z) {
        JSONObject jSONObject = new JSONObject();
        b(jSONObject, z);
        return jSONObject;
    }

    /* JADX WARN: Code restructure failed: missing block: B:61:0x015e, code lost:
        if (r1 != null) goto L_0x017f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x016f, code lost:
        if (r1 != null) goto L_0x017f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x017d, code lost:
        if (r1 != null) goto L_0x017f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x017f, code lost:
        r1.endTransaction();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [android.database.sqlite.SQLiteDatabase, android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v2, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r0v3, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r11v0, types: [org.json.JSONObject] */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(org.json.JSONObject r11, java.lang.String r12) {
        /*
            Method dump skipped, instructions count: 426
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.h.a(org.json.JSONObject, java.lang.String):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0066, code lost:
        if (r1 != null) goto L_0x0087;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0077, code lost:
        if (r1 != null) goto L_0x0087;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0085, code lost:
        if (r1 != null) goto L_0x0087;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0087, code lost:
        r1.endTransaction();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [android.database.sqlite.SQLiteDatabase, android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v2, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r0v3, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r6v0, types: [org.json.JSONObject] */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void b(org.json.JSONObject r6, java.lang.String r7) {
        /*
            r5 = this;
            r0 = 0
            android.content.Context r1 = com.umeng.analytics.pro.h.d     // Catch: SQLiteDatabaseCorruptException -> 0x007a, Throwable -> 0x006c, all -> 0x0069
            com.umeng.analytics.pro.f r1 = com.umeng.analytics.pro.f.a(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x007a, Throwable -> 0x006c, all -> 0x0069
            android.database.sqlite.SQLiteDatabase r1 = r1.a()     // Catch: SQLiteDatabaseCorruptException -> 0x007a, Throwable -> 0x006c, all -> 0x0069
            r1.beginTransaction()     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006d, all -> 0x0094
            java.lang.String r2 = "select *  from __er"
            boolean r3 = android.text.TextUtils.isEmpty(r7)     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006d, all -> 0x0094
            if (r3 != 0) goto L_0x0024
            java.lang.String r2 = "select *  from __er where __i=?"
            r3 = 1
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006d, all -> 0x0094
            r4 = 0
            r3[r4] = r7     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006d, all -> 0x0094
            android.database.Cursor r7 = r1.rawQuery(r2, r3)     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006d, all -> 0x0094
            r0 = r7
            goto L_0x0029
        L_0x0024:
            android.database.Cursor r7 = r1.rawQuery(r2, r0)     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006d, all -> 0x0094
            r0 = r7
        L_0x0029:
            if (r0 == 0) goto L_0x005e
            org.json.JSONArray r7 = new org.json.JSONArray     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006d, all -> 0x0094
            r7.<init>()     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006d, all -> 0x0094
        L_0x0030:
            boolean r2 = r0.moveToNext()     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006d, all -> 0x0094
            if (r2 == 0) goto L_0x0053
            java.lang.String r2 = "__a"
            int r2 = r0.getColumnIndex(r2)     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006d, all -> 0x0094
            java.lang.String r2 = r0.getString(r2)     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006d, all -> 0x0094
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006d, all -> 0x0094
            if (r3 != 0) goto L_0x0030
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006d, all -> 0x0094
            java.lang.String r2 = r5.d(r2)     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006d, all -> 0x0094
            r3.<init>(r2)     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006d, all -> 0x0094
            r7.put(r3)     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006d, all -> 0x0094
            goto L_0x0030
        L_0x0053:
            int r2 = r7.length()     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006d, all -> 0x0094
            if (r2 <= 0) goto L_0x005e
            java.lang.String r2 = "error"
            r6.put(r2, r7)     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006d, all -> 0x0094
        L_0x005e:
            r1.setTransactionSuccessful()     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006d, all -> 0x0094
            if (r0 == 0) goto L_0x0066
            r0.close()
        L_0x0066:
            if (r1 == 0) goto L_0x008a
            goto L_0x0087
        L_0x0069:
            r6 = move-exception
            r1 = r0
            goto L_0x0095
        L_0x006c:
            r1 = r0
        L_0x006d:
            android.content.Context r6 = com.umeng.analytics.pro.h.d     // Catch: all -> 0x0094
            com.umeng.analytics.pro.g.a(r6)     // Catch: all -> 0x0094
            if (r0 == 0) goto L_0x0077
            r0.close()
        L_0x0077:
            if (r1 == 0) goto L_0x008a
            goto L_0x0087
        L_0x007a:
            r1 = r0
        L_0x007b:
            android.content.Context r6 = com.umeng.analytics.pro.h.d     // Catch: all -> 0x0094
            com.umeng.analytics.pro.g.a(r6)     // Catch: all -> 0x0094
            if (r0 == 0) goto L_0x0085
            r0.close()
        L_0x0085:
            if (r1 == 0) goto L_0x008a
        L_0x0087:
            r1.endTransaction()     // Catch: Throwable -> 0x008a
        L_0x008a:
            android.content.Context r6 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r6 = com.umeng.analytics.pro.f.a(r6)
            r6.b()
            return
        L_0x0094:
            r6 = move-exception
        L_0x0095:
            if (r0 == 0) goto L_0x009a
            r0.close()
        L_0x009a:
            if (r1 == 0) goto L_0x009f
            r1.endTransaction()     // Catch: Throwable -> 0x009f
        L_0x009f:
            android.content.Context r7 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r7 = com.umeng.analytics.pro.f.a(r7)
            r7.b()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.h.b(org.json.JSONObject, java.lang.String):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0062, code lost:
        if (r0 != 0) goto L_0x007c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x007a, code lost:
        if (r0 != 0) goto L_0x007c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x007c, code lost:
        r0.endTransaction();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v18, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r0v2, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r0v5, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v19 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v6, types: [org.json.JSONObject] */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.json.JSONObject g() {
        /*
            r7 = this;
            java.util.List<java.lang.String> r0 = r7.i
            boolean r0 = r0.isEmpty()
            r1 = 0
            if (r0 == 0) goto L_0x000a
            return r1
        L_0x000a:
            android.content.Context r0 = com.umeng.analytics.pro.h.d     // Catch: SQLiteDatabaseCorruptException -> 0x0089, Throwable -> 0x0073, all -> 0x006e
            com.umeng.analytics.pro.f r0 = com.umeng.analytics.pro.f.a(r0)     // Catch: SQLiteDatabaseCorruptException -> 0x0089, Throwable -> 0x0073, all -> 0x006e
            android.database.sqlite.SQLiteDatabase r0 = r0.a()     // Catch: SQLiteDatabaseCorruptException -> 0x0089, Throwable -> 0x0073, all -> 0x006e
            r0.beginTransaction()     // Catch: SQLiteDatabaseCorruptException -> 0x006c, Throwable -> 0x006a, all -> 0x00a5
            java.util.List<java.lang.String> r2 = r7.i     // Catch: SQLiteDatabaseCorruptException -> 0x006c, Throwable -> 0x006a, all -> 0x00a5
            r3 = 0
            java.lang.Object r2 = r2.get(r3)     // Catch: SQLiteDatabaseCorruptException -> 0x006c, Throwable -> 0x006a, all -> 0x00a5
            java.lang.String r2 = (java.lang.String) r2     // Catch: SQLiteDatabaseCorruptException -> 0x006c, Throwable -> 0x006a, all -> 0x00a5
            java.lang.String r4 = "select *  from __sd where __ii=?"
            r5 = 1
            java.lang.String[] r5 = new java.lang.String[r5]     // Catch: SQLiteDatabaseCorruptException -> 0x006c, Throwable -> 0x006a, all -> 0x00a5
            r5[r3] = r2     // Catch: SQLiteDatabaseCorruptException -> 0x006c, Throwable -> 0x006a, all -> 0x00a5
            android.database.Cursor r2 = r0.rawQuery(r4, r5)     // Catch: SQLiteDatabaseCorruptException -> 0x006c, Throwable -> 0x006a, all -> 0x00a5
            if (r2 == 0) goto L_0x005a
            boolean r3 = r2.moveToNext()     // Catch: SQLiteDatabaseCorruptException -> 0x0067, Throwable -> 0x0075, all -> 0x0065
            if (r3 == 0) goto L_0x005a
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch: SQLiteDatabaseCorruptException -> 0x0067, Throwable -> 0x0075, all -> 0x0065
            r3.<init>()     // Catch: SQLiteDatabaseCorruptException -> 0x0067, Throwable -> 0x0075, all -> 0x0065
            java.lang.String r1 = "__av"
            int r1 = r2.getColumnIndex(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0065
            java.lang.String r1 = r2.getString(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0065
            java.lang.String r4 = "__vc"
            int r4 = r2.getColumnIndex(r4)     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0065
            java.lang.String r4 = r2.getString(r4)     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0065
            java.lang.String r5 = "__av"
            r3.put(r5, r1)     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0065
            java.lang.String r1 = "__vc"
            r3.put(r1, r4)     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0065
            r1 = r3
            goto L_0x005a
        L_0x0058:
            r1 = r3
            goto L_0x0075
        L_0x005a:
            r0.setTransactionSuccessful()     // Catch: SQLiteDatabaseCorruptException -> 0x0067, Throwable -> 0x0075, all -> 0x0065
            if (r2 == 0) goto L_0x0062
            r2.close()
        L_0x0062:
            if (r0 == 0) goto L_0x007f
            goto L_0x007c
        L_0x0065:
            r1 = move-exception
            goto L_0x00a9
        L_0x0067:
            r3 = r1
        L_0x0068:
            r1 = r2
            goto L_0x008b
        L_0x006a:
            r2 = r1
            goto L_0x0075
        L_0x006c:
            r3 = r1
            goto L_0x008b
        L_0x006e:
            r0 = move-exception
            r2 = r1
            r1 = r0
            r0 = r2
            goto L_0x00a9
        L_0x0073:
            r0 = r1
            r2 = r0
        L_0x0075:
            if (r2 == 0) goto L_0x007a
            r2.close()
        L_0x007a:
            if (r0 == 0) goto L_0x007f
        L_0x007c:
            r0.endTransaction()     // Catch: Throwable -> 0x007f
        L_0x007f:
            android.content.Context r0 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r0 = com.umeng.analytics.pro.f.a(r0)
            r0.b()
            goto L_0x00a4
        L_0x0089:
            r0 = r1
            r3 = r0
        L_0x008b:
            android.content.Context r2 = com.umeng.analytics.pro.h.d     // Catch: all -> 0x00a5
            com.umeng.analytics.pro.g.a(r2)     // Catch: all -> 0x00a5
            if (r1 == 0) goto L_0x0095
            r1.close()
        L_0x0095:
            if (r0 == 0) goto L_0x009a
            r0.endTransaction()     // Catch: Throwable -> 0x009a
        L_0x009a:
            android.content.Context r0 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r0 = com.umeng.analytics.pro.f.a(r0)
            r0.b()
            r1 = r3
        L_0x00a4:
            return r1
        L_0x00a5:
            r2 = move-exception
            r6 = r2
            r2 = r1
            r1 = r6
        L_0x00a9:
            if (r2 == 0) goto L_0x00ae
            r2.close()
        L_0x00ae:
            if (r0 == 0) goto L_0x00b3
            r0.endTransaction()     // Catch: Throwable -> 0x00b3
        L_0x00b3:
            android.content.Context r0 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r0 = com.umeng.analytics.pro.f.a(r0)
            r0.b()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.h.g():org.json.JSONObject");
    }

    private JSONArray b(JSONArray jSONArray) {
        JSONArray jSONArray2 = new JSONArray();
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            JSONObject optJSONObject = jSONArray.optJSONObject(i);
            if (optJSONObject != null && optJSONObject.optLong("duration") > 0) {
                jSONArray2.put(optJSONObject);
            }
        }
        return jSONArray2;
    }

    /*  JADX ERROR: NullPointerException in pass: BlockProcessor
        java.lang.NullPointerException
        */
    private java.lang.String a(org.json.JSONObject r21, boolean r22) {
        /*
            Method dump skipped, instructions count: 634
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.h.a(org.json.JSONObject, boolean):java.lang.String");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 0, insn: 0x00f4: MOVE  (r2 I:??[OBJECT, ARRAY]) = (r0 I:??[OBJECT, ARRAY]), block:B:51:0x00f4
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    private java.lang.String b(
    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 0, insn: 0x00f4: MOVE  (r2 I:??[OBJECT, ARRAY]) = (r0 I:??[OBJECT, ARRAY]), block:B:51:0x00f4
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r11v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        */

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0056, code lost:
        if (r0 != null) goto L_0x006a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0068, code lost:
        if (r0 == null) goto L_0x006d;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(boolean r6, boolean r7) {
        /*
            r5 = this;
            r0 = 0
            android.content.Context r1 = com.umeng.analytics.pro.h.d     // Catch: SQLiteDatabaseCorruptException -> 0x0063, Throwable -> 0x005b, all -> 0x0059
            com.umeng.analytics.pro.f r1 = com.umeng.analytics.pro.f.a(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x0063, Throwable -> 0x005b, all -> 0x0059
            android.database.sqlite.SQLiteDatabase r0 = r1.a()     // Catch: SQLiteDatabaseCorruptException -> 0x0063, Throwable -> 0x005b, all -> 0x0059
            r0.beginTransaction()     // Catch: SQLiteDatabaseCorruptException -> 0x0063, Throwable -> 0x005b, all -> 0x0059
            if (r7 == 0) goto L_0x0018
            if (r6 == 0) goto L_0x0053
            java.lang.String r6 = "delete from __is"
            r0.execSQL(r6)     // Catch: SQLiteDatabaseCorruptException -> 0x0063, Throwable -> 0x005b, all -> 0x0059
            goto L_0x0053
        L_0x0018:
            java.util.List<java.lang.String> r6 = r5.l     // Catch: SQLiteDatabaseCorruptException -> 0x0063, Throwable -> 0x005b, all -> 0x0059
            int r6 = r6.size()     // Catch: SQLiteDatabaseCorruptException -> 0x0063, Throwable -> 0x005b, all -> 0x0059
            r7 = 0
            if (r6 <= 0) goto L_0x004c
            r1 = r7
        L_0x0022:
            if (r7 >= r6) goto L_0x004b
            java.util.List<java.lang.String> r2 = r5.l     // Catch: SQLiteDatabaseCorruptException -> 0x0063, Throwable -> 0x005b, all -> 0x0059
            java.lang.Object r2 = r2.get(r7)     // Catch: SQLiteDatabaseCorruptException -> 0x0063, Throwable -> 0x005b, all -> 0x0059
            java.lang.String r2 = (java.lang.String) r2     // Catch: SQLiteDatabaseCorruptException -> 0x0063, Throwable -> 0x005b, all -> 0x0059
            if (r2 != 0) goto L_0x002f
            r1 = 1
        L_0x002f:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: SQLiteDatabaseCorruptException -> 0x0063, Throwable -> 0x005b, all -> 0x0059
            r3.<init>()     // Catch: SQLiteDatabaseCorruptException -> 0x0063, Throwable -> 0x005b, all -> 0x0059
            java.lang.String r4 = "delete from __is where __ii=\""
            r3.append(r4)     // Catch: SQLiteDatabaseCorruptException -> 0x0063, Throwable -> 0x005b, all -> 0x0059
            r3.append(r2)     // Catch: SQLiteDatabaseCorruptException -> 0x0063, Throwable -> 0x005b, all -> 0x0059
            java.lang.String r2 = "\""
            r3.append(r2)     // Catch: SQLiteDatabaseCorruptException -> 0x0063, Throwable -> 0x005b, all -> 0x0059
            java.lang.String r2 = r3.toString()     // Catch: SQLiteDatabaseCorruptException -> 0x0063, Throwable -> 0x005b, all -> 0x0059
            r0.execSQL(r2)     // Catch: SQLiteDatabaseCorruptException -> 0x0063, Throwable -> 0x005b, all -> 0x0059
            int r7 = r7 + 1
            goto L_0x0022
        L_0x004b:
            r7 = r1
        L_0x004c:
            if (r7 == 0) goto L_0x0053
            java.lang.String r6 = "delete from __is where __ii is null"
            r0.execSQL(r6)     // Catch: SQLiteDatabaseCorruptException -> 0x0063, Throwable -> 0x005b, all -> 0x0059
        L_0x0053:
            r0.setTransactionSuccessful()     // Catch: SQLiteDatabaseCorruptException -> 0x0063, Throwable -> 0x005b, all -> 0x0059
            if (r0 == 0) goto L_0x006d
            goto L_0x006a
        L_0x0059:
            r6 = move-exception
            goto L_0x0077
        L_0x005b:
            android.content.Context r6 = com.umeng.analytics.pro.h.d     // Catch: all -> 0x0059
            com.umeng.analytics.pro.g.a(r6)     // Catch: all -> 0x0059
            if (r0 == 0) goto L_0x006d
            goto L_0x006a
        L_0x0063:
            android.content.Context r6 = com.umeng.analytics.pro.h.d     // Catch: all -> 0x0059
            com.umeng.analytics.pro.g.a(r6)     // Catch: all -> 0x0059
            if (r0 == 0) goto L_0x006d
        L_0x006a:
            r0.endTransaction()     // Catch: Throwable -> 0x006d
        L_0x006d:
            android.content.Context r6 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r6 = com.umeng.analytics.pro.f.a(r6)
            r6.b()
            return
        L_0x0077:
            if (r0 == 0) goto L_0x007c
            r0.endTransaction()     // Catch: Throwable -> 0x007c
        L_0x007c:
            android.content.Context r7 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r7 = com.umeng.analytics.pro.f.a(r7)
            r7.b()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.h.a(boolean, boolean):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0050, code lost:
        if (r0 != null) goto L_0x0057;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0055, code lost:
        if (r0 != null) goto L_0x0057;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0057, code lost:
        r0.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0069, code lost:
        if (r0 == null) goto L_0x005a;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void b(boolean r3, boolean r4) {
        /*
            r2 = this;
            r0 = 0
            android.content.Context r1 = com.umeng.analytics.pro.h.d     // Catch: SQLiteDatabaseCorruptException -> 0x0064, Throwable -> 0x0055, all -> 0x0053
            com.umeng.analytics.pro.f r1 = com.umeng.analytics.pro.f.a(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x0064, Throwable -> 0x0055, all -> 0x0053
            android.database.sqlite.SQLiteDatabase r0 = r1.a()     // Catch: SQLiteDatabaseCorruptException -> 0x0064, Throwable -> 0x0055, all -> 0x0053
            r0.beginTransaction()     // Catch: SQLiteDatabaseCorruptException -> 0x0064, Throwable -> 0x0055, all -> 0x0053
            if (r4 == 0) goto L_0x0018
            if (r3 == 0) goto L_0x004d
            java.lang.String r3 = "delete from __sd"
            r0.execSQL(r3)     // Catch: SQLiteDatabaseCorruptException -> 0x0064, Throwable -> 0x0055, all -> 0x0053
            goto L_0x004d
        L_0x0018:
            java.util.List<java.lang.String> r3 = r2.i     // Catch: SQLiteDatabaseCorruptException -> 0x0064, Throwable -> 0x0055, all -> 0x0053
            int r3 = r3.size()     // Catch: SQLiteDatabaseCorruptException -> 0x0064, Throwable -> 0x0055, all -> 0x0053
            if (r3 <= 0) goto L_0x004d
            r3 = 0
        L_0x0021:
            java.util.List<java.lang.String> r4 = r2.i     // Catch: SQLiteDatabaseCorruptException -> 0x0064, Throwable -> 0x0055, all -> 0x0053
            int r4 = r4.size()     // Catch: SQLiteDatabaseCorruptException -> 0x0064, Throwable -> 0x0055, all -> 0x0053
            if (r3 >= r4) goto L_0x004d
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: SQLiteDatabaseCorruptException -> 0x0064, Throwable -> 0x0055, all -> 0x0053
            r4.<init>()     // Catch: SQLiteDatabaseCorruptException -> 0x0064, Throwable -> 0x0055, all -> 0x0053
            java.lang.String r1 = "delete from __sd where __ii=\""
            r4.append(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x0064, Throwable -> 0x0055, all -> 0x0053
            java.util.List<java.lang.String> r1 = r2.i     // Catch: SQLiteDatabaseCorruptException -> 0x0064, Throwable -> 0x0055, all -> 0x0053
            java.lang.Object r1 = r1.get(r3)     // Catch: SQLiteDatabaseCorruptException -> 0x0064, Throwable -> 0x0055, all -> 0x0053
            java.lang.String r1 = (java.lang.String) r1     // Catch: SQLiteDatabaseCorruptException -> 0x0064, Throwable -> 0x0055, all -> 0x0053
            r4.append(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x0064, Throwable -> 0x0055, all -> 0x0053
            java.lang.String r1 = "\""
            r4.append(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x0064, Throwable -> 0x0055, all -> 0x0053
            java.lang.String r4 = r4.toString()     // Catch: SQLiteDatabaseCorruptException -> 0x0064, Throwable -> 0x0055, all -> 0x0053
            r0.execSQL(r4)     // Catch: SQLiteDatabaseCorruptException -> 0x0064, Throwable -> 0x0055, all -> 0x0053
            int r3 = r3 + 1
            goto L_0x0021
        L_0x004d:
            r0.setTransactionSuccessful()     // Catch: SQLiteDatabaseCorruptException -> 0x0064, Throwable -> 0x0055, all -> 0x0053
            if (r0 == 0) goto L_0x005a
            goto L_0x0057
        L_0x0053:
            r3 = move-exception
            goto L_0x006d
        L_0x0055:
            if (r0 == 0) goto L_0x005a
        L_0x0057:
            r0.endTransaction()     // Catch: Throwable -> 0x005a
        L_0x005a:
            android.content.Context r3 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r3 = com.umeng.analytics.pro.f.a(r3)
            r3.b()
            goto L_0x006c
        L_0x0064:
            android.content.Context r3 = com.umeng.analytics.pro.h.d     // Catch: all -> 0x0053
            com.umeng.analytics.pro.g.a(r3)     // Catch: all -> 0x0053
            if (r0 == 0) goto L_0x005a
            goto L_0x0057
        L_0x006c:
            return
        L_0x006d:
            if (r0 == 0) goto L_0x0072
            r0.endTransaction()     // Catch: Throwable -> 0x0072
        L_0x0072:
            android.content.Context r4 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r4 = com.umeng.analytics.pro.f.a(r4)
            r4.b()
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.h.b(boolean, boolean):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0044, code lost:
        if (r0 != null) goto L_0x004b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0049, code lost:
        if (r0 != null) goto L_0x004b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x004b, code lost:
        r0.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x005d, code lost:
        if (r0 == null) goto L_0x004e;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void h() {
        /*
            r4 = this;
            r0 = 0
            android.content.Context r1 = com.umeng.analytics.pro.h.d     // Catch: SQLiteDatabaseCorruptException -> 0x0058, Throwable -> 0x0049, all -> 0x0047
            com.umeng.analytics.pro.f r1 = com.umeng.analytics.pro.f.a(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x0058, Throwable -> 0x0049, all -> 0x0047
            android.database.sqlite.SQLiteDatabase r0 = r1.a()     // Catch: SQLiteDatabaseCorruptException -> 0x0058, Throwable -> 0x0049, all -> 0x0047
            r0.beginTransaction()     // Catch: SQLiteDatabaseCorruptException -> 0x0058, Throwable -> 0x0049, all -> 0x0047
            java.util.List<java.lang.Integer> r1 = r4.j     // Catch: SQLiteDatabaseCorruptException -> 0x0058, Throwable -> 0x0049, all -> 0x0047
            int r1 = r1.size()     // Catch: SQLiteDatabaseCorruptException -> 0x0058, Throwable -> 0x0049, all -> 0x0047
            if (r1 <= 0) goto L_0x003c
            r1 = 0
        L_0x0017:
            java.util.List<java.lang.Integer> r2 = r4.j     // Catch: SQLiteDatabaseCorruptException -> 0x0058, Throwable -> 0x0049, all -> 0x0047
            int r2 = r2.size()     // Catch: SQLiteDatabaseCorruptException -> 0x0058, Throwable -> 0x0049, all -> 0x0047
            if (r1 >= r2) goto L_0x003c
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: SQLiteDatabaseCorruptException -> 0x0058, Throwable -> 0x0049, all -> 0x0047
            r2.<init>()     // Catch: SQLiteDatabaseCorruptException -> 0x0058, Throwable -> 0x0049, all -> 0x0047
            java.lang.String r3 = "delete from __et where rowid="
            r2.append(r3)     // Catch: SQLiteDatabaseCorruptException -> 0x0058, Throwable -> 0x0049, all -> 0x0047
            java.util.List<java.lang.Integer> r3 = r4.j     // Catch: SQLiteDatabaseCorruptException -> 0x0058, Throwable -> 0x0049, all -> 0x0047
            java.lang.Object r3 = r3.get(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x0058, Throwable -> 0x0049, all -> 0x0047
            r2.append(r3)     // Catch: SQLiteDatabaseCorruptException -> 0x0058, Throwable -> 0x0049, all -> 0x0047
            java.lang.String r2 = r2.toString()     // Catch: SQLiteDatabaseCorruptException -> 0x0058, Throwable -> 0x0049, all -> 0x0047
            r0.execSQL(r2)     // Catch: SQLiteDatabaseCorruptException -> 0x0058, Throwable -> 0x0049, all -> 0x0047
            int r1 = r1 + 1
            goto L_0x0017
        L_0x003c:
            java.util.List<java.lang.Integer> r1 = r4.j     // Catch: SQLiteDatabaseCorruptException -> 0x0058, Throwable -> 0x0049, all -> 0x0047
            r1.clear()     // Catch: SQLiteDatabaseCorruptException -> 0x0058, Throwable -> 0x0049, all -> 0x0047
            r0.setTransactionSuccessful()     // Catch: SQLiteDatabaseCorruptException -> 0x0058, Throwable -> 0x0049, all -> 0x0047
            if (r0 == 0) goto L_0x004e
            goto L_0x004b
        L_0x0047:
            r1 = move-exception
            goto L_0x0061
        L_0x0049:
            if (r0 == 0) goto L_0x004e
        L_0x004b:
            r0.endTransaction()     // Catch: Throwable -> 0x004e
        L_0x004e:
            android.content.Context r0 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r0 = com.umeng.analytics.pro.f.a(r0)
            r0.b()
            goto L_0x0060
        L_0x0058:
            android.content.Context r1 = com.umeng.analytics.pro.h.d     // Catch: all -> 0x0047
            com.umeng.analytics.pro.g.a(r1)     // Catch: all -> 0x0047
            if (r0 == 0) goto L_0x004e
            goto L_0x004b
        L_0x0060:
            return
        L_0x0061:
            if (r0 == 0) goto L_0x0066
            r0.endTransaction()     // Catch: Throwable -> 0x0066
        L_0x0066:
            android.content.Context r0 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r0 = com.umeng.analytics.pro.f.a(r0)
            r0.b()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.h.h():void");
    }

    /*  JADX ERROR: NullPointerException in pass: BlockProcessor
        java.lang.NullPointerException
        */
    public void i() {
        /*
            r2 = this;
            r0 = 0
            android.content.Context r1 = com.umeng.analytics.pro.h.d     // Catch: SQLiteDatabaseCorruptException -> 0x002a, Throwable -> 0x001b, all -> 0x0019
            com.umeng.analytics.pro.f r1 = com.umeng.analytics.pro.f.a(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x002a, Throwable -> 0x001b, all -> 0x0019
            android.database.sqlite.SQLiteDatabase r0 = r1.a()     // Catch: SQLiteDatabaseCorruptException -> 0x002a, Throwable -> 0x001b, all -> 0x0019
            r0.beginTransaction()     // Catch: SQLiteDatabaseCorruptException -> 0x002a, Throwable -> 0x001b, all -> 0x0019
            java.lang.String r1 = "delete from __er"
            r0.execSQL(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x002a, Throwable -> 0x001b, all -> 0x0019
            r0.setTransactionSuccessful()     // Catch: SQLiteDatabaseCorruptException -> 0x002a, Throwable -> 0x001b, all -> 0x0019
            if (r0 == 0) goto L_0x0020
            goto L_0x001d
        L_0x0019:
            r1 = move-exception
            goto L_0x0033
        L_0x001b:
            if (r0 == 0) goto L_0x0020
        L_0x001d:
            r0.endTransaction()     // Catch: Throwable -> 0x0020
        L_0x0020:
            android.content.Context r0 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r0 = com.umeng.analytics.pro.f.a(r0)
            r0.b()
            goto L_0x0032
        L_0x002a:
            android.content.Context r1 = com.umeng.analytics.pro.h.d     // Catch: all -> 0x0019
            com.umeng.analytics.pro.g.a(r1)     // Catch: all -> 0x0019
            if (r0 == 0) goto L_0x0020
            goto L_0x001d
        L_0x0032:
            return
        L_0x0033:
            if (r0 == 0) goto L_0x0038
            r0.endTransaction()     // Catch: Throwable -> 0x0038
        L_0x0038:
            android.content.Context r0 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r0 = com.umeng.analytics.pro.f.a(r0)
            r0.b()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.h.i():void");
    }

    /*  JADX ERROR: NullPointerException in pass: BlockProcessor
        java.lang.NullPointerException
        */
    public void j() {
        /*
            r5 = this;
            java.lang.String r0 = r5.k
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            r1 = 0
            if (r0 != 0) goto L_0x0080
            android.content.Context r0 = com.umeng.analytics.pro.h.d     // Catch: SQLiteDatabaseCorruptException -> 0x0067, Throwable -> 0x0057, all -> 0x0052
            com.umeng.analytics.pro.f r0 = com.umeng.analytics.pro.f.a(r0)     // Catch: SQLiteDatabaseCorruptException -> 0x0067, Throwable -> 0x0057, all -> 0x0052
            android.database.sqlite.SQLiteDatabase r0 = r0.a()     // Catch: SQLiteDatabaseCorruptException -> 0x0067, Throwable -> 0x0057, all -> 0x0052
            r0.beginTransaction()     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0070
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0070
            r2.<init>()     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0070
            java.lang.String r3 = "delete from __er where __i=\""
            r2.append(r3)     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0070
            java.lang.String r3 = r5.k     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0070
            r2.append(r3)     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0070
            java.lang.String r3 = "\""
            r2.append(r3)     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0070
            java.lang.String r2 = r2.toString()     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0070
            r0.execSQL(r2)     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0070
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0070
            r2.<init>()     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0070
            java.lang.String r3 = "delete from __et where __i=\""
            r2.append(r3)     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0070
            java.lang.String r3 = r5.k     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0070
            r2.append(r3)     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0070
            java.lang.String r3 = "\""
            r2.append(r3)     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0070
            java.lang.String r2 = r2.toString()     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0070
            r0.execSQL(r2)     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0070
            r0.setTransactionSuccessful()     // Catch: SQLiteDatabaseCorruptException -> 0x0068, Throwable -> 0x0058, all -> 0x0070
            if (r0 == 0) goto L_0x005d
            goto L_0x005a
        L_0x0052:
            r0 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
            goto L_0x0071
        L_0x0057:
            r0 = r1
        L_0x0058:
            if (r0 == 0) goto L_0x005d
        L_0x005a:
            r0.endTransaction()     // Catch: Throwable -> 0x005d
        L_0x005d:
            android.content.Context r0 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r0 = com.umeng.analytics.pro.f.a(r0)
            r0.b()
            goto L_0x0080
        L_0x0067:
            r0 = r1
        L_0x0068:
            android.content.Context r2 = com.umeng.analytics.pro.h.d     // Catch: all -> 0x0070
            com.umeng.analytics.pro.g.a(r2)     // Catch: all -> 0x0070
            if (r0 == 0) goto L_0x005d
            goto L_0x005a
        L_0x0070:
            r1 = move-exception
        L_0x0071:
            if (r0 == 0) goto L_0x0076
            r0.endTransaction()     // Catch: Throwable -> 0x0076
        L_0x0076:
            android.content.Context r0 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r0 = com.umeng.analytics.pro.f.a(r0)
            r0.b()
            throw r1
        L_0x0080:
            r5.k = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.h.j():void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x006e, code lost:
        r3.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0080, code lost:
        if (r3 == 0) goto L_0x0071;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0067, code lost:
        if (r3 != 0) goto L_0x006e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x006c, code lost:
        if (r3 != 0) goto L_0x006e;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v12 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(boolean r3, java.lang.String r4) {
        /*
            r2 = this;
            r3 = 0
            android.content.Context r0 = com.umeng.analytics.pro.h.d     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            com.umeng.analytics.pro.f r0 = com.umeng.analytics.pro.f.a(r0)     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            android.database.sqlite.SQLiteDatabase r3 = r0.a()     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            r3.beginTransaction()     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            boolean r0 = android.text.TextUtils.isEmpty(r4)     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            if (r0 != 0) goto L_0x0064
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            r0.<init>()     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            java.lang.String r1 = "delete from __er where __i=\""
            r0.append(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            r0.append(r4)     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            java.lang.String r1 = "\""
            r0.append(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            java.lang.String r0 = r0.toString()     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            r3.execSQL(r0)     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            r0.<init>()     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            java.lang.String r1 = "delete from __et where __i=\""
            r0.append(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            r0.append(r4)     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            java.lang.String r1 = "\""
            r0.append(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            java.lang.String r0 = r0.toString()     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            r3.execSQL(r0)     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            java.util.List<java.lang.Integer> r0 = r2.j     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            r0.clear()     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            r0.<init>()     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            java.lang.String r1 = "delete from __sd where __ii=\""
            r0.append(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            r0.append(r4)     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            java.lang.String r4 = "\""
            r0.append(r4)     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            java.lang.String r4 = r0.toString()     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            r3.execSQL(r4)     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
        L_0x0064:
            r3.setTransactionSuccessful()     // Catch: SQLiteDatabaseCorruptException -> 0x007b, Throwable -> 0x006c, all -> 0x006a
            if (r3 == 0) goto L_0x0071
            goto L_0x006e
        L_0x006a:
            r4 = move-exception
            goto L_0x0084
        L_0x006c:
            if (r3 == 0) goto L_0x0071
        L_0x006e:
            r3.endTransaction()     // Catch: Throwable -> 0x0071
        L_0x0071:
            android.content.Context r3 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r3 = com.umeng.analytics.pro.f.a(r3)
            r3.b()
            goto L_0x0083
        L_0x007b:
            android.content.Context r4 = com.umeng.analytics.pro.h.d     // Catch: all -> 0x006a
            com.umeng.analytics.pro.g.a(r4)     // Catch: all -> 0x006a
            if (r3 == 0) goto L_0x0071
            goto L_0x006e
        L_0x0083:
            return
        L_0x0084:
            if (r3 == 0) goto L_0x0089
            r3.endTransaction()     // Catch: Throwable -> 0x0089
        L_0x0089:
            android.content.Context r3 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r3 = com.umeng.analytics.pro.f.a(r3)
            r3.b()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.h.a(boolean, java.lang.String):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0037, code lost:
        r0.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0049, code lost:
        if (r0 == null) goto L_0x003a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0030, code lost:
        if (r0 != null) goto L_0x0037;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0035, code lost:
        if (r0 != null) goto L_0x0037;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void b(java.lang.String r4) {
        /*
            r3 = this;
            r0 = 0
            android.content.Context r1 = com.umeng.analytics.pro.h.d     // Catch: SQLiteDatabaseCorruptException -> 0x0044, Throwable -> 0x0035, all -> 0x0033
            com.umeng.analytics.pro.f r1 = com.umeng.analytics.pro.f.a(r1)     // Catch: SQLiteDatabaseCorruptException -> 0x0044, Throwable -> 0x0035, all -> 0x0033
            android.database.sqlite.SQLiteDatabase r0 = r1.a()     // Catch: SQLiteDatabaseCorruptException -> 0x0044, Throwable -> 0x0035, all -> 0x0033
            r0.beginTransaction()     // Catch: SQLiteDatabaseCorruptException -> 0x0044, Throwable -> 0x0035, all -> 0x0033
            boolean r1 = android.text.TextUtils.isEmpty(r4)     // Catch: SQLiteDatabaseCorruptException -> 0x0044, Throwable -> 0x0035, all -> 0x0033
            if (r1 != 0) goto L_0x002d
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: SQLiteDatabaseCorruptException -> 0x0044, Throwable -> 0x0035, all -> 0x0033
            r1.<init>()     // Catch: SQLiteDatabaseCorruptException -> 0x0044, Throwable -> 0x0035, all -> 0x0033
            java.lang.String r2 = "delete from __is where __ii=\""
            r1.append(r2)     // Catch: SQLiteDatabaseCorruptException -> 0x0044, Throwable -> 0x0035, all -> 0x0033
            r1.append(r4)     // Catch: SQLiteDatabaseCorruptException -> 0x0044, Throwable -> 0x0035, all -> 0x0033
            java.lang.String r4 = "\""
            r1.append(r4)     // Catch: SQLiteDatabaseCorruptException -> 0x0044, Throwable -> 0x0035, all -> 0x0033
            java.lang.String r4 = r1.toString()     // Catch: SQLiteDatabaseCorruptException -> 0x0044, Throwable -> 0x0035, all -> 0x0033
            r0.execSQL(r4)     // Catch: SQLiteDatabaseCorruptException -> 0x0044, Throwable -> 0x0035, all -> 0x0033
        L_0x002d:
            r0.setTransactionSuccessful()     // Catch: SQLiteDatabaseCorruptException -> 0x0044, Throwable -> 0x0035, all -> 0x0033
            if (r0 == 0) goto L_0x003a
            goto L_0x0037
        L_0x0033:
            r4 = move-exception
            goto L_0x004d
        L_0x0035:
            if (r0 == 0) goto L_0x003a
        L_0x0037:
            r0.endTransaction()     // Catch: Throwable -> 0x003a
        L_0x003a:
            android.content.Context r4 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r4 = com.umeng.analytics.pro.f.a(r4)
            r4.b()
            goto L_0x004c
        L_0x0044:
            android.content.Context r4 = com.umeng.analytics.pro.h.d     // Catch: all -> 0x0033
            com.umeng.analytics.pro.g.a(r4)     // Catch: all -> 0x0033
            if (r0 == 0) goto L_0x003a
            goto L_0x0037
        L_0x004c:
            return
        L_0x004d:
            if (r0 == 0) goto L_0x0052
            r0.endTransaction()     // Catch: Throwable -> 0x0052
        L_0x0052:
            android.content.Context r0 = com.umeng.analytics.pro.h.d
            com.umeng.analytics.pro.f r0 = com.umeng.analytics.pro.f.a(r0)
            r0.b()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.h.b(java.lang.String):void");
    }

    private void l() {
        try {
            if (TextUtils.isEmpty(e)) {
                String multiProcessSP = UMUtils.getMultiProcessSP(d, g);
                if (TextUtils.isEmpty(multiProcessSP)) {
                    multiProcessSP = PreferenceWrapper.getDefault(d).getString(g, null);
                    if (TextUtils.isEmpty(multiProcessSP)) {
                        multiProcessSP = UMUtils.genId();
                    }
                    if (!TextUtils.isEmpty(multiProcessSP)) {
                        UMUtils.setMultiProcessSP(d, g, multiProcessSP);
                    }
                }
                if (!TextUtils.isEmpty(multiProcessSP)) {
                    String substring = multiProcessSP.substring(1, 9);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < substring.length(); i++) {
                        char charAt = substring.charAt(i);
                        if (!Character.isDigit(charAt)) {
                            sb.append(charAt);
                        } else if (Integer.parseInt(Character.toString(charAt)) == 0) {
                            sb.append(0);
                        } else {
                            sb.append(10 - Integer.parseInt(Character.toString(charAt)));
                        }
                    }
                    e = sb.toString();
                }
                if (!TextUtils.isEmpty(e)) {
                    e += new StringBuilder(e).reverse().toString();
                    String multiProcessSP2 = UMUtils.getMultiProcessSP(d, h);
                    if (TextUtils.isEmpty(multiProcessSP2)) {
                        UMUtils.setMultiProcessSP(d, h, c(f));
                    } else if (!f.equals(d(multiProcessSP2))) {
                        b(true, false);
                        a(true, false);
                        h();
                        i();
                    }
                }
            }
        } catch (Throwable unused) {
        }
    }

    public String c(String str) {
        try {
            return TextUtils.isEmpty(e) ? str : Base64.encodeToString(DataHelper.encrypt(str.getBytes(), e.getBytes()), 0);
        } catch (Exception unused) {
            return null;
        }
    }

    public String d(String str) {
        try {
            return TextUtils.isEmpty(e) ? str : new String(DataHelper.decrypt(Base64.decode(str.getBytes(), 0), e.getBytes()));
        } catch (Exception unused) {
            if (Build.VERSION.SDK_INT >= 29 && !TextUtils.isEmpty(str)) {
                try {
                    new JSONObject(str);
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> UMStoreManager decrypt failed, return origin data.");
                    return str;
                } catch (Throwable unused2) {
                    return null;
                }
            }
            return null;
        }
    }
}
