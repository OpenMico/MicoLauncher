package com.efs.sdk.base.a.g.a;

import com.efs.sdk.base.a.c.a.c;
import com.efs.sdk.base.a.f.b;

/* loaded from: classes.dex */
public final class f extends a {
    @Override // com.efs.sdk.base.a.g.a.a
    public final void a(b bVar) {
        Double d;
        c a = c.a();
        String str = bVar.a.a;
        com.efs.sdk.base.a.c.a.b bVar2 = a.d;
        if (c.a.nextDouble() * 100.0d <= ((!bVar2.d.containsKey(str) || (d = bVar2.d.get(str)) == null) ? 100.0d : d.doubleValue())) {
            b(bVar);
        }
    }
}
