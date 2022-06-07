package com.efs.sdk.base.a.g.a;

import com.efs.sdk.base.a.f.b;
import com.efs.sdk.base.a.h.d;

/* loaded from: classes.dex */
public final class c extends a {
    @Override // com.efs.sdk.base.a.g.a.a
    public final void a(b bVar) {
        if (c(bVar)) {
            b(bVar);
            return;
        }
        byte[] a = com.efs.sdk.base.a.h.c.a(bVar.c);
        if (a == null) {
            d.a("efs.base", "gzip error", null);
            b(bVar);
            return;
        }
        bVar.a(a);
        bVar.a("gzip");
        b(bVar);
    }

    private static boolean c(b bVar) {
        return bVar.b() || (1 == bVar.a.b && !bVar.b.a) || 1 == bVar.a.c;
    }
}
