package com.xiaomi.push;

import android.os.SystemClock;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.XMPushService;
import java.io.IOException;
import java.net.Socket;

/* loaded from: classes4.dex */
public abstract class fu extends fn {

    /* renamed from: a */
    protected Socket f45a;
    protected XMPushService b;
    private String i;
    private int j;
    protected Exception a = null;
    String h = null;
    protected volatile long e = 0;
    protected volatile long f = 0;
    protected volatile long g = 0;

    public fu(XMPushService xMPushService, fo foVar) {
        super(xMPushService, foVar);
        this.b = xMPushService;
    }

    private void a(fo foVar) {
        a(foVar.c(), foVar.mo923a());
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x0228, code lost:
        if (android.text.TextUtils.equals(r13, com.xiaomi.push.at.m756a((android.content.Context) r18.b)) == false) goto L_0x022a;
     */
    /* JADX WARN: Removed duplicated region for block: B:46:0x01cc  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0239 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x023a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(java.lang.String r19, int r20) {
        /*
            Method dump skipped, instructions count: 580
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.fu.a(java.lang.String, int):void");
    }

    @Override // com.xiaomi.push.fn
    /* renamed from: a */
    public String mo927a() {
        return this.i;
    }

    @Override // com.xiaomi.push.fn
    /* renamed from: a */
    public Socket mo927a() {
        return new Socket();
    }

    @Override // com.xiaomi.push.fn
    /* renamed from: a */
    public synchronized void mo927a() {
    }

    public synchronized void a(int i, Exception exc) {
        if (b() != 2) {
            a(2, i, exc);
            this.f40a = "";
            try {
                this.f45a.close();
            } catch (Throwable unused) {
            }
            this.e = 0L;
            this.f = 0L;
        }
    }

    protected void a(Exception exc) {
        if (SystemClock.elapsedRealtime() - this.g < 300000) {
            if (at.b(this.b)) {
                this.j++;
                if (this.j >= 2) {
                    String a = mo927a();
                    b.m149a("max short conn time reached, sink down current host:" + a);
                    a(a, 0L, exc);
                } else {
                    return;
                }
            } else {
                return;
            }
        }
        this.j = 0;
    }

    protected void a(String str, long j, Exception exc) {
        cr a = cv.a().a(fo.a(), false);
        if (a != null) {
            a.b(str, j, 0L, exc);
            cv.a().m821c();
        }
    }

    protected abstract void a(boolean z);

    @Override // com.xiaomi.push.fn
    public void a(fg[] fgVarArr) {
        throw new fy("Don't support send Blob");
    }

    cr b(String str) {
        cr a = cv.a().a(str, false);
        if (!a.b()) {
            gq.a(new fh(this, str));
        }
        return a;
    }

    @Override // com.xiaomi.push.fn
    public void b(int i, Exception exc) {
        a(i, exc);
        if ((exc != null || i == 18) && this.g != 0) {
            a(exc);
        }
    }

    @Override // com.xiaomi.push.fn
    public void b(boolean z) {
        long currentTimeMillis = System.currentTimeMillis();
        a(z);
        if (!z) {
            this.b.a(new fa(this, 13, currentTimeMillis), 10000L);
        }
    }

    @Override // com.xiaomi.push.fn
    public String c() {
        return this.f40a;
    }

    public void c(int i, Exception exc) {
        this.b.a(new ff(this, 2, i, exc));
    }

    @Override // com.xiaomi.push.fn
    public synchronized void e() {
        try {
            if (!c() && !b()) {
                a(0, 0, (Exception) null);
                a(this.f37a);
                return;
            }
            b.m149a("WARNING: current xmpp has connected");
        } catch (IOException e) {
            throw new fy(e);
        }
    }

    public void f() {
        this.e = SystemClock.elapsedRealtime();
    }

    public void g() {
        this.f = SystemClock.elapsedRealtime();
    }
}
