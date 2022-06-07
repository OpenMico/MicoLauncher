package com.xiaomi.push;

import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.ef;
import com.xiaomi.push.fn;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.al;
import com.xiaomi.push.service.au;
import com.xiaomi.push.service.ba;

/* loaded from: classes4.dex */
public class fl extends fu {
    private Thread i;
    private es j;
    private fi k;
    private byte[] l;

    public fl(XMPushService xMPushService, fo foVar) {
        super(xMPushService, foVar);
    }

    private fg c(boolean z) {
        fk fkVar = new fk();
        if (z) {
            fkVar.a("1");
        }
        byte[] a = hb.m953a();
        if (a != null) {
            ef.j jVar = new ef.j();
            jVar.a(a.a(a));
            fkVar.a(jVar.mo888a(), (String) null);
        }
        return fkVar;
    }

    private void i() {
        try {
            this.j = new es(this.f45a.getInputStream(), this);
            this.k = new fi(this.f45a.getOutputStream(), this);
            this.i = new ey(this, "Blob Reader (" + this.b + ")");
            this.i.start();
        } catch (Exception e) {
            throw new fy("Error to init reader and writer", e);
        }
    }

    @Override // com.xiaomi.push.fu, com.xiaomi.push.fn
    /* renamed from: a */
    public synchronized void mo927a() {
        i();
        this.k.a();
    }

    @Override // com.xiaomi.push.fu
    public synchronized void a(int i, Exception exc) {
        if (this.j != null) {
            this.j.b();
            this.j = null;
        }
        if (this.k != null) {
            try {
                this.k.b();
            } catch (Exception e) {
                b.a(e);
            }
            this.k = null;
        }
        this.l = null;
        super.a(i, exc);
    }

    public void a(fg fgVar) {
        if (fgVar != null) {
            if (fgVar.m909a()) {
                b.m149a("[Slim] RCV blob chid=" + fgVar.a() + "; id=" + fgVar.e() + "; errCode=" + fgVar.b() + "; err=" + fgVar.m913c());
            }
            if (fgVar.a() == 0) {
                if ("PING".equals(fgVar.m907a())) {
                    b.m149a("[Slim] RCV ping id=" + fgVar.e());
                    g();
                } else if ("CLOSE".equals(fgVar.m907a())) {
                    c(13, null);
                }
            }
            for (fn.a aVar : this.f41a.values()) {
                aVar.a(fgVar);
            }
        }
    }

    @Override // com.xiaomi.push.fn
    @Deprecated
    public void a(ge geVar) {
        b(fg.a(geVar, (String) null));
    }

    @Override // com.xiaomi.push.fn
    public synchronized void a(al.b bVar) {
        er.a(bVar, c(), this);
    }

    @Override // com.xiaomi.push.fn
    public synchronized void a(String str, String str2) {
        er.a(str, str2, this);
    }

    @Override // com.xiaomi.push.fu
    protected void a(boolean z) {
        if (this.k != null) {
            fg c = c(z);
            b.m149a("[Slim] SND ping id=" + c.e());
            b(c);
            f();
            return;
        }
        throw new fy("The BlobWriter is null.");
    }

    @Override // com.xiaomi.push.fu, com.xiaomi.push.fn
    public void a(fg[] fgVarArr) {
        for (fg fgVar : fgVarArr) {
            b(fgVar);
        }
    }

    @Override // com.xiaomi.push.fu, com.xiaomi.push.fn
    /* renamed from: a */
    public boolean mo927a() {
        return true;
    }

    @Override // com.xiaomi.push.fn
    public void b(fg fgVar) {
        fi fiVar = this.k;
        if (fiVar != null) {
            try {
                int a = fiVar.a(fgVar);
                this.d = System.currentTimeMillis();
                String f = fgVar.f();
                if (!TextUtils.isEmpty(f)) {
                    gs.a(this.f39a, f, a, false, true, System.currentTimeMillis());
                }
                for (fn.a aVar : this.f44b.values()) {
                    aVar.a(fgVar);
                }
            } catch (Exception e) {
                throw new fy(e);
            }
        } else {
            throw new fy("the writer is null.");
        }
    }

    public void b(ge geVar) {
        if (geVar != null) {
            for (fn.a aVar : this.f41a.values()) {
                aVar.a(geVar);
            }
        }
    }

    public synchronized byte[] h() {
        if (this.l == null && !TextUtils.isEmpty(this.f40a)) {
            String a = ba.m1145a();
            this.l = au.a(this.f40a.getBytes(), (this.f40a.substring(this.f40a.length() / 2) + a.substring(a.length() / 2)).getBytes());
        }
        return this.l;
    }
}
