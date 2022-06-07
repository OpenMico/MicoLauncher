package com.xiaomi.onetrack.a;

import java.util.concurrent.Callable;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class i implements Callable<j> {
    final /* synthetic */ String a;
    final /* synthetic */ f b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public i(f fVar, String str) {
        this.b = fVar;
        this.a = str;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x007c, code lost:
        if (r1 != null) goto L_0x0093;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0091, code lost:
        if (r1 == null) goto L_0x009e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0093, code lost:
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0097, code lost:
        com.xiaomi.onetrack.util.p.a("ConfigDbManager", "getConfig  cursor.close");
     */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00a2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.xiaomi.onetrack.a.j call() throws java.lang.Exception {
        /*
            r10 = this;
            r0 = 0
            java.lang.String r4 = "app_id=?"
            com.xiaomi.onetrack.a.f r1 = r10.b     // Catch: Exception -> 0x0086, all -> 0x0081
            com.xiaomi.onetrack.a.e r1 = com.xiaomi.onetrack.a.f.a(r1)     // Catch: Exception -> 0x0086, all -> 0x0081
            android.database.sqlite.SQLiteDatabase r1 = r1.getWritableDatabase()     // Catch: Exception -> 0x0086, all -> 0x0081
            java.lang.String r2 = "events_cloud"
            r3 = 0
            r5 = 1
            java.lang.String[] r5 = new java.lang.String[r5]     // Catch: Exception -> 0x0086, all -> 0x0081
            r6 = 0
            java.lang.String r7 = r10.a     // Catch: Exception -> 0x0086, all -> 0x0081
            r5[r6] = r7     // Catch: Exception -> 0x0086, all -> 0x0081
            r6 = 0
            r7 = 0
            r8 = 0
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6, r7, r8)     // Catch: Exception -> 0x0086, all -> 0x0081
            java.lang.String r2 = "app_id"
            int r2 = r1.getColumnIndex(r2)     // Catch: Exception -> 0x007f, all -> 0x009f
            java.lang.String r3 = "cloud_data"
            int r3 = r1.getColumnIndex(r3)     // Catch: Exception -> 0x007f, all -> 0x009f
            java.lang.String r4 = "data_hash"
            int r4 = r1.getColumnIndex(r4)     // Catch: Exception -> 0x007f, all -> 0x009f
            java.lang.String r5 = "timestamp"
            int r5 = r1.getColumnIndex(r5)     // Catch: Exception -> 0x007f, all -> 0x009f
            boolean r6 = r1.moveToNext()     // Catch: Exception -> 0x007f, all -> 0x009f
            if (r6 == 0) goto L_0x007c
            com.xiaomi.onetrack.a.j r6 = new com.xiaomi.onetrack.a.j     // Catch: Exception -> 0x007f, all -> 0x009f
            r6.<init>()     // Catch: Exception -> 0x007f, all -> 0x009f
            java.lang.String r2 = r1.getString(r2)     // Catch: Exception -> 0x007f, all -> 0x009f
            r6.a = r2     // Catch: Exception -> 0x007f, all -> 0x009f
            java.lang.String r2 = r1.getString(r3)     // Catch: Exception -> 0x007f, all -> 0x009f
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch: Exception -> 0x007f, all -> 0x009f
            if (r3 != 0) goto L_0x0059
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch: Exception -> 0x007f, all -> 0x009f
            r3.<init>(r2)     // Catch: Exception -> 0x007f, all -> 0x009f
            r6.e = r3     // Catch: Exception -> 0x007f, all -> 0x009f
        L_0x0059:
            org.json.JSONObject r2 = r6.e     // Catch: Exception -> 0x007f, all -> 0x009f
            int r2 = com.xiaomi.onetrack.a.f.a(r2)     // Catch: Exception -> 0x007f, all -> 0x009f
            long r2 = (long) r2     // Catch: Exception -> 0x007f, all -> 0x009f
            r6.b = r2     // Catch: Exception -> 0x007f, all -> 0x009f
            java.lang.String r2 = r1.getString(r4)     // Catch: Exception -> 0x007f, all -> 0x009f
            r6.d = r2     // Catch: Exception -> 0x007f, all -> 0x009f
            long r2 = r1.getLong(r5)     // Catch: Exception -> 0x007f, all -> 0x009f
            r6.c = r2     // Catch: Exception -> 0x007f, all -> 0x009f
            if (r1 == 0) goto L_0x007b
            r1.close()     // Catch: Exception -> 0x0074
            goto L_0x007b
        L_0x0074:
            java.lang.String r0 = "ConfigDbManager"
            java.lang.String r1 = "getConfig  cursor.close"
            com.xiaomi.onetrack.util.p.a(r0, r1)
        L_0x007b:
            return r6
        L_0x007c:
            if (r1 == 0) goto L_0x009e
            goto L_0x0093
        L_0x007f:
            r2 = move-exception
            goto L_0x0088
        L_0x0081:
            r1 = move-exception
            r9 = r1
            r1 = r0
            r0 = r9
            goto L_0x00a0
        L_0x0086:
            r2 = move-exception
            r1 = r0
        L_0x0088:
            java.lang.String r3 = "ConfigDbManager"
            java.lang.String r2 = r2.getMessage()     // Catch: all -> 0x009f
            com.xiaomi.onetrack.util.p.a(r3, r2)     // Catch: all -> 0x009f
            if (r1 == 0) goto L_0x009e
        L_0x0093:
            r1.close()     // Catch: Exception -> 0x0097
            goto L_0x009e
        L_0x0097:
            java.lang.String r1 = "ConfigDbManager"
            java.lang.String r2 = "getConfig  cursor.close"
            com.xiaomi.onetrack.util.p.a(r1, r2)
        L_0x009e:
            return r0
        L_0x009f:
            r0 = move-exception
        L_0x00a0:
            if (r1 == 0) goto L_0x00ad
            r1.close()     // Catch: Exception -> 0x00a6
            goto L_0x00ad
        L_0x00a6:
            java.lang.String r1 = "ConfigDbManager"
            java.lang.String r2 = "getConfig  cursor.close"
            com.xiaomi.onetrack.util.p.a(r1, r2)
        L_0x00ad:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.onetrack.a.i.call():com.xiaomi.onetrack.a.j");
    }
}
