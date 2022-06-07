package com.xiaomi.push;

import com.xiaomi.push.gt;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.al;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class gn implements al.b.a {
    private XMPushService a;
    private al.b b;
    private fn c;
    private int e;
    private boolean f = false;
    private al.c d = al.c.binding;

    /* JADX INFO: Access modifiers changed from: package-private */
    public gn(XMPushService xMPushService, al.b bVar) {
        this.a = xMPushService;
        this.b = bVar;
    }

    private void b() {
        this.b.b(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        fc fcVar;
        b();
        if (this.f && this.e != 11) {
            fd d = gz.m951a().d();
            switch (gr.a[this.d.ordinal()]) {
                case 1:
                    int i = this.e;
                    if (i != 17) {
                        if (i != 21) {
                            try {
                                gt.a c = gt.c(gz.a().b());
                                d.f28a = c.a.a();
                                d.c(c.b);
                                break;
                            } catch (NullPointerException unused) {
                                d = null;
                                break;
                            }
                        } else {
                            fcVar = fc.BIND_TIMEOUT;
                        }
                    } else {
                        fcVar = fc.BIND_TCP_READ_TIMEOUT;
                    }
                    d.f28a = fcVar.a();
                    break;
                case 3:
                    fcVar = fc.BIND_SUCCESS;
                    d.f28a = fcVar.a();
                    break;
            }
            if (d != null) {
                d.b(this.c.m916a());
                d.d(this.b.b);
                d.b = 1;
                try {
                    d.a((byte) Integer.parseInt(this.b.g));
                } catch (NumberFormatException unused2) {
                }
                gz.m951a().a(d);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        this.b.a(this);
        this.c = this.a.a();
    }

    @Override // com.xiaomi.push.service.al.b.a
    public void a(al.c cVar, al.c cVar2, int i) {
        if (!this.f && cVar == al.c.binding) {
            this.d = cVar2;
            this.e = i;
            this.f = true;
        }
        this.a.a(new go(this, 4));
    }
}
