package com.efs.sdk.base.a.i;

import com.efs.sdk.base.a.d.a;
import com.efs.sdk.base.a.i.f;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public final class d extends a {
    private AtomicInteger c = new AtomicInteger(0);
    private AtomicInteger d = new AtomicInteger(0);
    public AtomicInteger b = new AtomicInteger(0);
    private AtomicInteger e = new AtomicInteger(0);
    private AtomicInteger f = new AtomicInteger(0);

    public final void b() {
        this.c.incrementAndGet();
    }

    public final void c() {
        this.d.incrementAndGet();
    }

    public final void d() {
        this.e.incrementAndGet();
    }

    public final void e() {
        this.f.incrementAndGet();
    }

    @Override // com.efs.sdk.base.a.i.a
    public final void a() {
        if ((this.c.get() != 0 || this.d.get() != 0 || this.b.get() != 0 || this.f.get() != 0 || this.e.get() != 0) && this.a != null && a.a().d) {
            a aVar = this.a;
            int i = this.c.get();
            int i2 = this.d.get();
            int i3 = this.b.get();
            int i4 = this.f.get();
            int i5 = this.e.get();
            b bVar = new b("efs_core", "lf_st", f.a.a().a.c);
            bVar.put("create_cnt", Integer.valueOf(i));
            bVar.put("cache_cnt", Integer.valueOf(i2));
            bVar.put("req_cnt", Integer.valueOf(i3));
            bVar.put("err_cnt", Integer.valueOf(i4));
            bVar.put("expire_cnt", Integer.valueOf(i5));
            this.c.addAndGet(i * (-1));
            this.d.addAndGet(i2 * (-1));
            this.b.addAndGet(i3 * (-1));
            this.f.addAndGet(i4 * (-1));
            this.e.addAndGet(i5 * (-1));
            aVar.a(bVar);
        }
    }
}
