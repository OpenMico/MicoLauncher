package com.xiaomi.push;

import android.content.Context;
import android.net.TrafficStats;
import android.os.Process;
import android.os.SystemClock;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
public class gy implements fq {
    XMPushService a;
    fn b;
    private int c;
    private Exception d;
    private long j;
    private long k;
    private long f = 0;
    private long g = 0;
    private long h = 0;
    private long i = 0;
    private String e = "";

    public gy(XMPushService xMPushService) {
        this.j = 0L;
        this.k = 0L;
        this.a = xMPushService;
        c();
        int myUid = Process.myUid();
        this.k = TrafficStats.getUidRxBytes(myUid);
        this.j = TrafficStats.getUidTxBytes(myUid);
    }

    private void c() {
        this.g = 0L;
        this.i = 0L;
        this.f = 0L;
        this.h = 0L;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (at.b(this.a)) {
            this.f = elapsedRealtime;
        }
        if (this.a.c()) {
            this.h = elapsedRealtime;
        }
    }

    private synchronized void d() {
        b.c("stat connpt = " + this.e + " netDuration = " + this.g + " ChannelDuration = " + this.i + " channelConnectedTime = " + this.h);
        fd fdVar = new fd();
        fdVar.a = (byte) 0;
        fdVar.a(fc.CHANNEL_ONLINE_RATE.a());
        fdVar.a(this.e);
        fdVar.d((int) (System.currentTimeMillis() / 1000));
        fdVar.b((int) (this.g / 1000));
        fdVar.c((int) (this.i / 1000));
        gz.m951a().a(fdVar);
        c();
    }

    public synchronized void a() {
        if (this.a != null) {
            String a = at.m756a((Context) this.a);
            boolean b = at.b(this.a);
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (this.f > 0) {
                this.g += elapsedRealtime - this.f;
                this.f = 0L;
            }
            if (this.h != 0) {
                this.i += elapsedRealtime - this.h;
                this.h = 0L;
            }
            if (b) {
                if ((!TextUtils.equals(this.e, a) && this.g > 30000) || this.g > 5400000) {
                    d();
                }
                this.e = a;
                if (this.f == 0) {
                    this.f = elapsedRealtime;
                }
                if (this.a.c()) {
                    this.h = elapsedRealtime;
                }
            }
        }
    }

    @Override // com.xiaomi.push.fq
    public void a(fn fnVar) {
        a();
        this.h = SystemClock.elapsedRealtime();
        hb.a(0, fc.CONN_SUCCESS.a(), fnVar.m916a(), fnVar.mo927a());
    }

    @Override // com.xiaomi.push.fq
    public void a(fn fnVar, int i, Exception exc) {
        if (this.c == 0 && this.d == null) {
            this.c = i;
            this.d = exc;
            hb.b(fnVar.m916a(), exc);
        }
        if (i == 22 && this.h != 0) {
            long a = fnVar.a() - this.h;
            if (a < 0) {
                a = 0;
            }
            this.i += a + (ft.b() / 2);
            this.h = 0L;
        }
        a();
        int myUid = Process.myUid();
        long uidRxBytes = TrafficStats.getUidRxBytes(myUid);
        long uidTxBytes = TrafficStats.getUidTxBytes(myUid);
        b.c("Stats rx=" + (uidRxBytes - this.k) + ", tx=" + (uidTxBytes - this.j));
        this.k = uidRxBytes;
        this.j = uidTxBytes;
    }

    @Override // com.xiaomi.push.fq
    public void a(fn fnVar, Exception exc) {
        hb.a(0, fc.CHANNEL_CON_FAIL.a(), 1, fnVar.m916a(), at.b(this.a) ? 1 : 0);
        a();
    }

    public Exception b() {
        return this.d;
    }

    @Override // com.xiaomi.push.fq
    public void b(fn fnVar) {
        this.c = 0;
        this.d = null;
        this.b = fnVar;
        this.e = at.m756a((Context) this.a);
        hb.a(0, fc.CONN_SUCCESS.a());
    }
}
