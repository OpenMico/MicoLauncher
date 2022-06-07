package com.xiaomi.onetrack.b;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class e implements Runnable {
    final /* synthetic */ b a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(b bVar) {
        this.a = bVar;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x00a3, code lost:
        if (r1 != null) goto L_0x00a5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x00a5, code lost:
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x00c2, code lost:
        if (r1 == null) goto L_0x00c5;
     */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() {
        /*
            r15 = this;
            com.xiaomi.onetrack.b.b r0 = r15.a
            com.xiaomi.onetrack.b.b$a r0 = com.xiaomi.onetrack.b.b.a(r0)
            monitor-enter(r0)
            r1 = 0
            com.xiaomi.onetrack.b.b r2 = r15.a     // Catch: Exception -> 0x00ab, all -> 0x00a9
            com.xiaomi.onetrack.b.b$a r2 = com.xiaomi.onetrack.b.b.a(r2)     // Catch: Exception -> 0x00ab, all -> 0x00a9
            android.database.sqlite.SQLiteDatabase r2 = r2.getWritableDatabase()     // Catch: Exception -> 0x00ab, all -> 0x00a9
            java.util.Calendar r3 = java.util.Calendar.getInstance()     // Catch: Exception -> 0x00ab, all -> 0x00a9
            long r4 = java.lang.System.currentTimeMillis()     // Catch: Exception -> 0x00ab, all -> 0x00a9
            r3.setTimeInMillis(r4)     // Catch: Exception -> 0x00ab, all -> 0x00a9
            r4 = 6
            int r5 = r3.get(r4)     // Catch: Exception -> 0x00ab, all -> 0x00a9
            int r5 = r5 + (-7)
            r3.set(r4, r5)     // Catch: Exception -> 0x00ab, all -> 0x00a9
            r4 = 11
            r11 = 0
            r3.set(r4, r11)     // Catch: Exception -> 0x00ab, all -> 0x00a9
            r4 = 12
            r3.set(r4, r11)     // Catch: Exception -> 0x00ab, all -> 0x00a9
            r4 = 13
            r3.set(r4, r11)     // Catch: Exception -> 0x00ab, all -> 0x00a9
            long r3 = r3.getTimeInMillis()     // Catch: Exception -> 0x00ab, all -> 0x00a9
            java.lang.String r12 = "timestamp < ? "
            r13 = 1
            java.lang.String[] r14 = new java.lang.String[r13]     // Catch: Exception -> 0x00ab, all -> 0x00a9
            java.lang.String r3 = java.lang.Long.toString(r3)     // Catch: Exception -> 0x00ab, all -> 0x00a9
            r14[r11] = r3     // Catch: Exception -> 0x00ab, all -> 0x00a9
            java.lang.String r4 = "events"
            java.lang.String r3 = "timestamp"
            java.lang.String[] r5 = new java.lang.String[]{r3}     // Catch: Exception -> 0x00ab, all -> 0x00a9
            r8 = 0
            r9 = 0
            java.lang.String r10 = "timestamp ASC"
            r3 = r2
            r6 = r12
            r7 = r14
            android.database.Cursor r1 = r3.query(r4, r5, r6, r7, r8, r9, r10)     // Catch: Exception -> 0x00ab, all -> 0x00a9
            int r3 = r1.getCount()     // Catch: Exception -> 0x00ab, all -> 0x00a9
            if (r3 == 0) goto L_0x007b
            java.lang.String r3 = "events"
            int r2 = r2.delete(r3, r12, r14)     // Catch: Exception -> 0x00ab, all -> 0x00a9
            java.lang.String r3 = "EventManager"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: Exception -> 0x00ab, all -> 0x00a9
            r4.<init>()     // Catch: Exception -> 0x00ab, all -> 0x00a9
            java.lang.String r5 = "*** deleted obsolete item count="
            r4.append(r5)     // Catch: Exception -> 0x00ab, all -> 0x00a9
            r4.append(r2)     // Catch: Exception -> 0x00ab, all -> 0x00a9
            java.lang.String r2 = r4.toString()     // Catch: Exception -> 0x00ab, all -> 0x00a9
            com.xiaomi.onetrack.util.p.a(r3, r2)     // Catch: Exception -> 0x00ab, all -> 0x00a9
        L_0x007b:
            com.xiaomi.onetrack.b.b r2 = com.xiaomi.onetrack.b.b.a()     // Catch: Exception -> 0x00ab, all -> 0x00a9
            long r2 = r2.c()     // Catch: Exception -> 0x00ab, all -> 0x00a9
            r4 = 0
            int r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r4 != 0) goto L_0x008a
            r11 = r13
        L_0x008a:
            com.xiaomi.onetrack.a.l.a(r11)     // Catch: Exception -> 0x00ab, all -> 0x00a9
            java.lang.String r4 = "EventManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: Exception -> 0x00ab, all -> 0x00a9
            r5.<init>()     // Catch: Exception -> 0x00ab, all -> 0x00a9
            java.lang.String r6 = "after delete obsolete record remains="
            r5.append(r6)     // Catch: Exception -> 0x00ab, all -> 0x00a9
            r5.append(r2)     // Catch: Exception -> 0x00ab, all -> 0x00a9
            java.lang.String r2 = r5.toString()     // Catch: Exception -> 0x00ab, all -> 0x00a9
            com.xiaomi.onetrack.util.p.a(r4, r2)     // Catch: Exception -> 0x00ab, all -> 0x00a9
            if (r1 == 0) goto L_0x00c5
        L_0x00a5:
            r1.close()     // Catch: all -> 0x00cd
            goto L_0x00c5
        L_0x00a9:
            r2 = move-exception
            goto L_0x00c7
        L_0x00ab:
            r2 = move-exception
            java.lang.String r3 = "EventManager"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: all -> 0x00a9
            r4.<init>()     // Catch: all -> 0x00a9
            java.lang.String r5 = "remove obsolete events failed with "
            r4.append(r5)     // Catch: all -> 0x00a9
            r4.append(r2)     // Catch: all -> 0x00a9
            java.lang.String r2 = r4.toString()     // Catch: all -> 0x00a9
            com.xiaomi.onetrack.util.p.d(r3, r2)     // Catch: all -> 0x00a9
            if (r1 == 0) goto L_0x00c5
            goto L_0x00a5
        L_0x00c5:
            monitor-exit(r0)     // Catch: all -> 0x00cd
            return
        L_0x00c7:
            if (r1 == 0) goto L_0x00cc
            r1.close()     // Catch: all -> 0x00cd
        L_0x00cc:
            throw r2     // Catch: all -> 0x00cd
        L_0x00cd:
            r1 = move-exception
            monitor-exit(r0)     // Catch: all -> 0x00cd
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.onetrack.b.e.run():void");
    }
}
