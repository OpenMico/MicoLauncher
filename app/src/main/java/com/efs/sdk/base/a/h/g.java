package com.efs.sdk.base.a.h;

import android.os.Process;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

/* loaded from: classes.dex */
public final class g {
    private static String a = null;
    private static List<Integer> b = null;
    private static long c = -1;

    public static int a() {
        return Process.myPid();
    }

    public static String b() {
        String str = a;
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        String a2 = a(Process.myPid());
        a = a2;
        return a2;
    }

    public static String a(int i) {
        String str;
        BufferedReader bufferedReader;
        Throwable th;
        Throwable th2;
        BufferedReader bufferedReader2;
        try {
            str = "";
            bufferedReader = null;
            try {
                try {
                    bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/" + i + "/cmdline")));
                } catch (Throwable th3) {
                    th2 = th3;
                }
            } catch (Throwable th4) {
                th = th4;
            }
        } catch (Throwable th5) {
            th5.printStackTrace();
        }
        try {
            StringBuilder sb = new StringBuilder();
            while (true) {
                int read = bufferedReader2.read();
                if (read <= 0) {
                    break;
                }
                sb.append((char) read);
            }
            sb.trimToSize();
            str = sb.toString();
            bufferedReader2.close();
        } catch (Throwable th6) {
            th2 = th6;
            bufferedReader = bufferedReader2;
            d.b("efs.base", "get process name error", th2);
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            return str;
        }
        return str;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x002f, code lost:
        if (com.efs.sdk.base.a.h.g.b == null) goto L_0x0037;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0031, code lost:
        com.efs.sdk.base.a.h.g.b.clear();
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0037, code lost:
        com.efs.sdk.base.a.h.g.b = new java.util.ArrayList();
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x003e, code lost:
        r7 = (android.app.ActivityManager) r7.getSystemService(com.xiaomi.idm.api.IDMServer.PERSIST_TYPE_ACTIVITY);
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0046, code lost:
        if (r7 == null) goto L_0x0068;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0048, code lost:
        r7 = r7.getRunningAppProcesses().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0054, code lost:
        if (r7.hasNext() == false) goto L_0x0068;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0056, code lost:
        com.efs.sdk.base.a.h.g.b.add(java.lang.Integer.valueOf(r7.next().pid));
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0068, code lost:
        com.efs.sdk.base.a.h.g.c = java.lang.System.currentTimeMillis();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean a(android.content.Context r7, java.lang.String r8) {
        /*
            r0 = 1
            int r8 = java.lang.Integer.parseInt(r8)     // Catch: Throwable -> 0x0079
            java.util.List<java.lang.Integer> r1 = com.efs.sdk.base.a.h.g.b     // Catch: Throwable -> 0x0079
            r2 = 0
            if (r1 == 0) goto L_0x002b
            java.util.List<java.lang.Integer> r1 = com.efs.sdk.base.a.h.g.b     // Catch: Throwable -> 0x0079
            boolean r1 = r1.isEmpty()     // Catch: Throwable -> 0x0079
            if (r1 == 0) goto L_0x0013
            goto L_0x002b
        L_0x0013:
            long r3 = com.efs.sdk.base.a.h.g.c     // Catch: Throwable -> 0x0079
            r5 = 0
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 <= 0) goto L_0x002b
            long r3 = java.lang.System.currentTimeMillis()     // Catch: Throwable -> 0x0079
            long r5 = com.efs.sdk.base.a.h.g.c     // Catch: Throwable -> 0x0079
            long r3 = r3 - r5
            r5 = 600000(0x927c0, double:2.964394E-318)
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 <= 0) goto L_0x002a
            goto L_0x002b
        L_0x002a:
            r2 = r0
        L_0x002b:
            if (r2 != 0) goto L_0x006e
            java.util.List<java.lang.Integer> r1 = com.efs.sdk.base.a.h.g.b     // Catch: Throwable -> 0x0079
            if (r1 == 0) goto L_0x0037
            java.util.List<java.lang.Integer> r1 = com.efs.sdk.base.a.h.g.b     // Catch: Throwable -> 0x0079
            r1.clear()     // Catch: Throwable -> 0x0079
            goto L_0x003e
        L_0x0037:
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch: Throwable -> 0x0079
            r1.<init>()     // Catch: Throwable -> 0x0079
            com.efs.sdk.base.a.h.g.b = r1     // Catch: Throwable -> 0x0079
        L_0x003e:
            java.lang.String r1 = "activity"
            java.lang.Object r7 = r7.getSystemService(r1)     // Catch: Throwable -> 0x0079
            android.app.ActivityManager r7 = (android.app.ActivityManager) r7     // Catch: Throwable -> 0x0079
            if (r7 == 0) goto L_0x0068
            java.util.List r7 = r7.getRunningAppProcesses()     // Catch: Throwable -> 0x0079
            java.util.Iterator r7 = r7.iterator()     // Catch: Throwable -> 0x0079
        L_0x0050:
            boolean r1 = r7.hasNext()     // Catch: Throwable -> 0x0079
            if (r1 == 0) goto L_0x0068
            java.lang.Object r1 = r7.next()     // Catch: Throwable -> 0x0079
            android.app.ActivityManager$RunningAppProcessInfo r1 = (android.app.ActivityManager.RunningAppProcessInfo) r1     // Catch: Throwable -> 0x0079
            java.util.List<java.lang.Integer> r2 = com.efs.sdk.base.a.h.g.b     // Catch: Throwable -> 0x0079
            int r1 = r1.pid     // Catch: Throwable -> 0x0079
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch: Throwable -> 0x0079
            r2.add(r1)     // Catch: Throwable -> 0x0079
            goto L_0x0050
        L_0x0068:
            long r1 = java.lang.System.currentTimeMillis()     // Catch: Throwable -> 0x0079
            com.efs.sdk.base.a.h.g.c = r1     // Catch: Throwable -> 0x0079
        L_0x006e:
            java.util.List<java.lang.Integer> r7 = com.efs.sdk.base.a.h.g.b     // Catch: Throwable -> 0x0079
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch: Throwable -> 0x0079
            boolean r7 = r7.contains(r8)     // Catch: Throwable -> 0x0079
            return r7
        L_0x0079:
            r7 = move-exception
            java.lang.String r8 = "efs.base"
            java.lang.String r1 = "Process exist judge error"
            com.efs.sdk.base.a.h.d.b(r8, r1, r7)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.efs.sdk.base.a.h.g.a(android.content.Context, java.lang.String):boolean");
    }
}
