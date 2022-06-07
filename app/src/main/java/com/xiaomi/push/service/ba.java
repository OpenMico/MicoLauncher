package com.xiaomi.push.service;

import android.content.SharedPreferences;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.am;
import com.xiaomi.push.c;
import com.xiaomi.push.ee;
import com.xiaomi.push.ef;
import com.xiaomi.push.gq;
import com.xiaomi.push.i;
import com.xiaomi.push.u;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ba {
    private static String a;
    private static ba e = new ba();
    private List<a> b = new ArrayList();
    private ee.a c;
    private am.b d;

    /* loaded from: classes4.dex */
    public static abstract class a {
        public void a(ee.a aVar) {
        }

        public void a(ef.b bVar) {
        }
    }

    private ba() {
    }

    public static ba a() {
        return e;
    }

    /* renamed from: a */
    public static synchronized String m1145a() {
        String str;
        synchronized (ba.class) {
            if (a == null) {
                SharedPreferences sharedPreferences = u.m1170a().getSharedPreferences("XMPushServiceConfig", 0);
                a = sharedPreferences.getString("DeviceUUID", null);
                if (a == null) {
                    a = i.a(u.m1170a(), false);
                    if (a != null) {
                        sharedPreferences.edit().putString("DeviceUUID", a).commit();
                    }
                }
            }
            str = a;
        }
        return str;
    }

    private void d() {
        if (this.c == null) {
            f();
        }
    }

    private void e() {
        if (this.d == null) {
            this.d = new x(this);
            gq.a(this.d);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void f() {
        /*
            r4 = this;
            r0 = 0
            android.content.Context r1 = com.xiaomi.push.u.m1170a()     // Catch: Exception -> 0x002b, all -> 0x0029
            java.lang.String r2 = "XMCloudCfg"
            java.io.FileInputStream r1 = r1.openFileInput(r2)     // Catch: Exception -> 0x002b, all -> 0x0029
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch: Exception -> 0x002b, all -> 0x0029
            r2.<init>(r1)     // Catch: Exception -> 0x002b, all -> 0x0029
            com.xiaomi.push.b r0 = com.xiaomi.push.b.a(r2)     // Catch: Exception -> 0x0025, all -> 0x0021
            com.xiaomi.push.ee$a r0 = com.xiaomi.push.ee.a.b(r0)     // Catch: Exception -> 0x0025, all -> 0x0021
            r4.c = r0     // Catch: Exception -> 0x0025, all -> 0x0021
            r2.close()     // Catch: Exception -> 0x0025, all -> 0x0021
            com.xiaomi.push.z.a(r2)
            goto L_0x0047
        L_0x0021:
            r0 = move-exception
            r1 = r0
            r0 = r2
            goto L_0x0053
        L_0x0025:
            r0 = move-exception
            r1 = r0
            r0 = r2
            goto L_0x002c
        L_0x0029:
            r1 = move-exception
            goto L_0x0053
        L_0x002b:
            r1 = move-exception
        L_0x002c:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: all -> 0x0029
            r2.<init>()     // Catch: all -> 0x0029
            java.lang.String r3 = "load config failure: "
            r2.append(r3)     // Catch: all -> 0x0029
            java.lang.String r1 = r1.getMessage()     // Catch: all -> 0x0029
            r2.append(r1)     // Catch: all -> 0x0029
            java.lang.String r1 = r2.toString()     // Catch: all -> 0x0029
            com.xiaomi.channel.commonutils.logger.b.m149a(r1)     // Catch: all -> 0x0029
            com.xiaomi.push.z.a(r0)
        L_0x0047:
            com.xiaomi.push.ee$a r0 = r4.c
            if (r0 != 0) goto L_0x0052
            com.xiaomi.push.ee$a r0 = new com.xiaomi.push.ee$a
            r0.<init>()
            r4.c = r0
        L_0x0052:
            return
        L_0x0053:
            com.xiaomi.push.z.a(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.ba.f():void");
    }

    public void g() {
        try {
            if (this.c != null) {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(u.m1170a().openFileOutput("XMCloudCfg", 0));
                c a2 = c.a(bufferedOutputStream);
                this.c.a(a2);
                a2.m786a();
                bufferedOutputStream.close();
            }
        } catch (Exception e2) {
            b.m149a("save config failure: " + e2.getMessage());
        }
    }

    /* renamed from: a */
    public ee.a m1146a() {
        d();
        return this.c;
    }

    public void a(ef.b bVar) {
        a[] aVarArr;
        if (bVar.m854d() && bVar.d() > c()) {
            e();
        }
        synchronized (this) {
            aVarArr = (a[]) this.b.toArray(new a[this.b.size()]);
        }
        for (a aVar : aVarArr) {
            aVar.a(bVar);
        }
    }

    public synchronized void a(a aVar) {
        this.b.add(aVar);
    }

    public synchronized void b() {
        this.b.clear();
    }

    public int c() {
        d();
        ee.a aVar = this.c;
        if (aVar != null) {
            return aVar.c();
        }
        return 0;
    }
}
