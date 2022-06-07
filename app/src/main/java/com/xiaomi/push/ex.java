package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.XMJobService;

/* loaded from: classes4.dex */
public final class ex {
    private static a a;
    private static final String b = XMJobService.class.getCanonicalName();
    private static int c = 0;

    /* loaded from: classes4.dex */
    public interface a {
        /* renamed from: a */
        void mo900a();

        void a(boolean z);

        boolean a();
    }

    public static synchronized void a() {
        synchronized (ex.class) {
            if (a != null) {
                a.mo900a();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x005e, code lost:
        if (com.xiaomi.push.ex.b.equals(java.lang.Class.forName(r5.name).getSuperclass().getCanonicalName()) != false) goto L_0x0048;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void a(android.content.Context r8) {
        /*
            android.content.Context r8 = r8.getApplicationContext()
            java.lang.String r0 = "com.xiaomi.xmsf"
            java.lang.String r1 = r8.getPackageName()
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0019
            com.xiaomi.push.em r0 = new com.xiaomi.push.em
            r0.<init>(r8)
        L_0x0015:
            com.xiaomi.push.ex.a = r0
            goto L_0x00d7
        L_0x0019:
            android.content.pm.PackageManager r0 = r8.getPackageManager()
            r1 = 0
            java.lang.String r2 = r8.getPackageName()     // Catch: Exception -> 0x0081
            r3 = 4
            android.content.pm.PackageInfo r0 = r0.getPackageInfo(r2, r3)     // Catch: Exception -> 0x0081
            android.content.pm.ServiceInfo[] r2 = r0.services     // Catch: Exception -> 0x0081
            r3 = 1
            if (r2 == 0) goto L_0x007f
            android.content.pm.ServiceInfo[] r0 = r0.services     // Catch: Exception -> 0x0081
            int r2 = r0.length     // Catch: Exception -> 0x0081
            r4 = r1
        L_0x0030:
            if (r1 >= r2) goto L_0x009b
            r5 = r0[r1]     // Catch: Exception -> 0x007d
            java.lang.String r6 = "android.permission.BIND_JOB_SERVICE"
            java.lang.String r7 = r5.permission     // Catch: Exception -> 0x007d
            boolean r6 = r6.equals(r7)     // Catch: Exception -> 0x007d
            if (r6 == 0) goto L_0x0064
            java.lang.String r6 = com.xiaomi.push.ex.b     // Catch: Exception -> 0x007d
            java.lang.String r7 = r5.name     // Catch: Exception -> 0x007d
            boolean r6 = r6.equals(r7)     // Catch: Exception -> 0x007d
            if (r6 == 0) goto L_0x004a
        L_0x0048:
            r4 = r3
            goto L_0x0061
        L_0x004a:
            java.lang.String r6 = r5.name     // Catch: Exception -> 0x0061
            java.lang.Class r6 = java.lang.Class.forName(r6)     // Catch: Exception -> 0x0061
            java.lang.String r7 = com.xiaomi.push.ex.b     // Catch: Exception -> 0x0061
            java.lang.Class r6 = r6.getSuperclass()     // Catch: Exception -> 0x0061
            java.lang.String r6 = r6.getCanonicalName()     // Catch: Exception -> 0x0061
            boolean r6 = r7.equals(r6)     // Catch: Exception -> 0x0061
            if (r6 == 0) goto L_0x0061
            goto L_0x0048
        L_0x0061:
            if (r4 != r3) goto L_0x0064
            goto L_0x009b
        L_0x0064:
            java.lang.String r6 = com.xiaomi.push.ex.b     // Catch: Exception -> 0x007d
            java.lang.String r7 = r5.name     // Catch: Exception -> 0x007d
            boolean r6 = r6.equals(r7)     // Catch: Exception -> 0x007d
            if (r6 == 0) goto L_0x007a
            java.lang.String r6 = "android.permission.BIND_JOB_SERVICE"
            java.lang.String r5 = r5.permission     // Catch: Exception -> 0x007d
            boolean r5 = r6.equals(r5)     // Catch: Exception -> 0x007d
            if (r5 == 0) goto L_0x007a
            r4 = r3
            goto L_0x009b
        L_0x007a:
            int r1 = r1 + 1
            goto L_0x0030
        L_0x007d:
            r0 = move-exception
            goto L_0x0083
        L_0x007f:
            r4 = r1
            goto L_0x009b
        L_0x0081:
            r0 = move-exception
            r4 = r1
        L_0x0083:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "check service err : "
            r1.append(r2)
            java.lang.String r0 = r0.getMessage()
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            com.xiaomi.channel.commonutils.logger.b.m149a(r0)
        L_0x009b:
            if (r4 != 0) goto L_0x00cc
            boolean r0 = com.xiaomi.push.u.m1174a(r8)
            if (r0 != 0) goto L_0x00a4
            goto L_0x00cc
        L_0x00a4:
            java.lang.RuntimeException r8 = new java.lang.RuntimeException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Should export service: "
            r0.append(r1)
            java.lang.String r1 = com.xiaomi.push.ex.b
            r0.append(r1)
            java.lang.String r1 = " with permission "
            r0.append(r1)
            java.lang.String r1 = "android.permission.BIND_JOB_SERVICE"
            r0.append(r1)
            java.lang.String r1 = " in AndroidManifest.xml file"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r8.<init>(r0)
            throw r8
        L_0x00cc:
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 21
            com.xiaomi.push.em r0 = new com.xiaomi.push.em
            r0.<init>(r8)
            goto L_0x0015
        L_0x00d7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.ex.a(android.content.Context):void");
    }

    public static synchronized void a(Context context, int i) {
        synchronized (ex.class) {
            int i2 = c;
            if (!"com.xiaomi.xmsf".equals(context.getPackageName())) {
                if (i == 2) {
                    c = 2;
                } else {
                    c = 0;
                }
            }
            if (i2 != c && c == 2) {
                a();
                a = new eq(context);
            }
        }
    }

    public static synchronized void a(boolean z) {
        synchronized (ex.class) {
            if (a == null) {
                b.m149a("timer is not initialized");
            } else {
                a.a(z);
            }
        }
    }

    /* renamed from: a */
    public static synchronized boolean m899a() {
        synchronized (ex.class) {
            if (a == null) {
                return false;
            }
            return a.a();
        }
    }
}
