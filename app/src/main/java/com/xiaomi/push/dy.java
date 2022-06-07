package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.push.aj;

/* loaded from: classes4.dex */
public abstract class dy extends aj.a {
    protected int a;

    /* renamed from: a */
    protected Context f24a;

    public dy(Context context, int i) {
        this.a = i;
        this.f24a = context;
    }

    public static void a(Context context, hp hpVar) {
        dl a = dm.a().m824a();
        String a2 = a == null ? "" : a.a();
        if (!TextUtils.isEmpty(a2) && !TextUtils.isEmpty(hpVar.a())) {
            a(context, hpVar, a2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0096 A[Catch: all -> 0x00a6, TRY_LEAVE, TryCatch #1 {, blocks: (B:14:0x0056, B:16:0x005c, B:17:0x005f, B:18:0x0062, B:35:0x0082, B:37:0x0088, B:38:0x008b, B:39:0x008f, B:45:0x0096, B:47:0x009c, B:48:0x009f, B:49:0x00a5), top: B:57:0x0012 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void a(android.content.Context r6, com.xiaomi.push.hp r7, java.lang.String r8) {
        /*
            byte[] r7 = com.xiaomi.push.ir.a(r7)
            byte[] r7 = com.xiaomi.push.dq.b(r8, r7)
            if (r7 == 0) goto L_0x00a9
            int r8 = r7.length
            if (r8 != 0) goto L_0x000f
            goto L_0x00a9
        L_0x000f:
            java.lang.Object r8 = com.xiaomi.push.dr.a
            monitor-enter(r8)
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch: IOException -> 0x007a, all -> 0x0076
            java.io.File r2 = r6.getExternalFilesDir(r0)     // Catch: IOException -> 0x007a, all -> 0x0076
            java.lang.String r3 = "push_cdata.lock"
            r1.<init>(r2, r3)     // Catch: IOException -> 0x007a, all -> 0x0076
            com.xiaomi.push.z.m1176a(r1)     // Catch: IOException -> 0x007a, all -> 0x0076
            java.io.RandomAccessFile r2 = new java.io.RandomAccessFile     // Catch: IOException -> 0x007a, all -> 0x0076
            java.lang.String r3 = "rw"
            r2.<init>(r1, r3)     // Catch: IOException -> 0x007a, all -> 0x0076
            java.nio.channels.FileChannel r1 = r2.getChannel()     // Catch: IOException -> 0x0073, all -> 0x0070
            java.nio.channels.FileLock r1 = r1.lock()     // Catch: IOException -> 0x0073, all -> 0x0070
            java.io.File r3 = new java.io.File     // Catch: IOException -> 0x006c, all -> 0x006a
            java.io.File r6 = r6.getExternalFilesDir(r0)     // Catch: IOException -> 0x006c, all -> 0x006a
            java.lang.String r4 = "push_cdata.data"
            r3.<init>(r6, r4)     // Catch: IOException -> 0x006c, all -> 0x006a
            java.io.BufferedOutputStream r6 = new java.io.BufferedOutputStream     // Catch: IOException -> 0x006c, all -> 0x006a
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch: IOException -> 0x006c, all -> 0x006a
            r5 = 1
            r4.<init>(r3, r5)     // Catch: IOException -> 0x006c, all -> 0x006a
            r6.<init>(r4)     // Catch: IOException -> 0x006c, all -> 0x006a
            int r0 = r7.length     // Catch: IOException -> 0x0068, all -> 0x0066
            byte[] r0 = com.xiaomi.push.ad.a(r0)     // Catch: IOException -> 0x0068, all -> 0x0066
            r6.write(r0)     // Catch: IOException -> 0x0068, all -> 0x0066
            r6.write(r7)     // Catch: IOException -> 0x0068, all -> 0x0066
            r6.flush()     // Catch: IOException -> 0x0068, all -> 0x0066
            if (r1 == 0) goto L_0x005f
            boolean r7 = r1.isValid()     // Catch: all -> 0x00a6
            if (r7 == 0) goto L_0x005f
            r1.release()     // Catch: IOException -> 0x005f, all -> 0x00a6
        L_0x005f:
            com.xiaomi.push.z.a(r6)     // Catch: all -> 0x00a6
        L_0x0062:
            com.xiaomi.push.z.a(r2)     // Catch: all -> 0x00a6
            goto L_0x008f
        L_0x0066:
            r7 = move-exception
            goto L_0x0093
        L_0x0068:
            r7 = move-exception
            goto L_0x006e
        L_0x006a:
            r7 = move-exception
            goto L_0x0094
        L_0x006c:
            r7 = move-exception
            r6 = r0
        L_0x006e:
            r0 = r1
            goto L_0x007d
        L_0x0070:
            r7 = move-exception
            r1 = r0
            goto L_0x0094
        L_0x0073:
            r7 = move-exception
            r6 = r0
            goto L_0x007d
        L_0x0076:
            r7 = move-exception
            r1 = r0
            r2 = r1
            goto L_0x0094
        L_0x007a:
            r7 = move-exception
            r6 = r0
            r2 = r6
        L_0x007d:
            r7.printStackTrace()     // Catch: all -> 0x0091
            if (r0 == 0) goto L_0x008b
            boolean r7 = r0.isValid()     // Catch: all -> 0x00a6
            if (r7 == 0) goto L_0x008b
            r0.release()     // Catch: IOException -> 0x008b, all -> 0x00a6
        L_0x008b:
            com.xiaomi.push.z.a(r6)     // Catch: all -> 0x00a6
            goto L_0x0062
        L_0x008f:
            monitor-exit(r8)     // Catch: all -> 0x00a6
            return
        L_0x0091:
            r7 = move-exception
            r1 = r0
        L_0x0093:
            r0 = r6
        L_0x0094:
            if (r1 == 0) goto L_0x009f
            boolean r6 = r1.isValid()     // Catch: all -> 0x00a6
            if (r6 == 0) goto L_0x009f
            r1.release()     // Catch: IOException -> 0x009f, all -> 0x00a6
        L_0x009f:
            com.xiaomi.push.z.a(r0)     // Catch: all -> 0x00a6
            com.xiaomi.push.z.a(r2)     // Catch: all -> 0x00a6
            throw r7     // Catch: all -> 0x00a6
        L_0x00a6:
            r6 = move-exception
            monitor-exit(r8)     // Catch: all -> 0x00a6
            throw r6
        L_0x00a9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.dy.a(android.content.Context, com.xiaomi.push.hp, java.lang.String):void");
    }

    @Override // com.xiaomi.push.aj.a
    /* renamed from: a */
    public abstract hj mo834a();

    @Override // com.xiaomi.push.aj.a
    /* renamed from: a  reason: collision with other method in class */
    public abstract String mo834a();

    @Override // com.xiaomi.push.aj.a
    /* renamed from: a */
    public boolean mo834a() {
        return ah.a(this.f24a, String.valueOf(mo834a()), this.a);
    }

    protected boolean b() {
        return true;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (mo834a()) {
            dl a = dm.a().m824a();
            String a2 = a == null ? "" : a.a();
            if (!TextUtils.isEmpty(a2) && b()) {
                String a3 = mo834a();
                if (!TextUtils.isEmpty(a3)) {
                    hp hpVar = new hp();
                    hpVar.a(a3);
                    hpVar.a(System.currentTimeMillis());
                    hpVar.a(mo834a());
                    a(this.f24a, hpVar, a2);
                }
            }
        }
    }
}
