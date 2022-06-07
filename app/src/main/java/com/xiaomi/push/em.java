package com.xiaomi.push;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.ex;

/* loaded from: classes4.dex */
public class em implements ex.a {
    protected Context a;
    private PendingIntent b = null;
    private volatile long c = 0;

    public em(Context context) {
        this.a = null;
        this.a = context;
    }

    private void a(AlarmManager alarmManager, long j, PendingIntent pendingIntent) {
        try {
            AlarmManager.class.getMethod("setExact", Integer.TYPE, Long.TYPE, PendingIntent.class).invoke(alarmManager, 0, Long.valueOf(j), pendingIntent);
        } catch (Exception e) {
            b.a(e);
        }
    }

    @Override // com.xiaomi.push.ex.a
    /* renamed from: a */
    public void mo900a() {
        if (this.b != null) {
            try {
                ((AlarmManager) this.a.getSystemService("alarm")).cancel(this.b);
            } catch (Exception unused) {
            } catch (Throwable th) {
                this.b = null;
                b.c("unregister timer");
                this.c = 0L;
                throw th;
            }
            this.b = null;
            b.c("unregister timer");
            this.c = 0L;
        }
        this.c = 0L;
    }

    public void a(Intent intent, long j) {
        AlarmManager alarmManager = (AlarmManager) this.a.getSystemService("alarm");
        this.b = PendingIntent.getBroadcast(this.a, 0, intent, 0);
        if (Build.VERSION.SDK_INT >= 23) {
            au.a(alarmManager, "setExactAndAllowWhileIdle", 0, Long.valueOf(j), this.b);
        } else if (Build.VERSION.SDK_INT >= 19) {
            a(alarmManager, j, this.b);
        } else {
            alarmManager.set(0, j, this.b);
        }
        b.c("register timer " + j);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x002a, code lost:
        if (r6.c < java.lang.System.currentTimeMillis()) goto L_0x0033;
     */
    @Override // com.xiaomi.push.ex.a
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(boolean r7) {
        /*
            r6 = this;
            long r0 = r6.b()
            r2 = 0
            if (r7 != 0) goto L_0x000f
            long r4 = r6.c
            int r4 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r4 != 0) goto L_0x000f
            return
        L_0x000f:
            if (r7 == 0) goto L_0x0014
            r6.mo900a()
        L_0x0014:
            if (r7 != 0) goto L_0x002d
            long r4 = r6.c
            int r7 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r7 != 0) goto L_0x001d
            goto L_0x002d
        L_0x001d:
            long r2 = r6.c
            long r2 = r2 + r0
            r6.c = r2
            long r2 = r6.c
            long r4 = java.lang.System.currentTimeMillis()
            int r7 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r7 >= 0) goto L_0x003a
            goto L_0x0033
        L_0x002d:
            long r2 = android.os.SystemClock.elapsedRealtime()
            long r2 = r2 % r0
            long r0 = r0 - r2
        L_0x0033:
            long r2 = java.lang.System.currentTimeMillis()
            long r2 = r2 + r0
            r6.c = r2
        L_0x003a:
            android.content.Intent r7 = new android.content.Intent
            java.lang.String r0 = com.xiaomi.push.service.ap.o
            r7.<init>(r0)
            android.content.Context r0 = r6.a
            java.lang.String r0 = r0.getPackageName()
            r7.setPackage(r0)
            long r0 = r6.c
            r6.a(r7, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.em.a(boolean):void");
    }

    @Override // com.xiaomi.push.ex.a
    /* renamed from: a */
    public boolean mo900a() {
        return this.c != 0;
    }

    long b() {
        return ft.b();
    }
}
