package com.efs.sdk.base.a.g.a;

import com.efs.sdk.base.a.b.a;
import com.efs.sdk.base.a.b.c;
import com.efs.sdk.base.a.b.e;
import com.efs.sdk.base.a.i.f;

/* loaded from: classes.dex */
public final class b extends a {
    @Override // com.efs.sdk.base.a.g.a.a
    public final void a(com.efs.sdk.base.a.f.b bVar) {
        a aVar;
        e a;
        f fVar;
        if (bVar.b.a) {
            b(bVar);
            return;
        }
        aVar = a.b.a;
        if (!"wa".equals(bVar.a.a) && !c.a().a) {
            if (!aVar.a) {
                fVar = f.a.a;
                int i = com.efs.sdk.base.a.c.a.c.a().d.a;
                if (fVar.b != null || com.efs.sdk.base.a.d.a.a().d) {
                    fVar.b.a(fVar.a("disk_limit", i));
                }
            }
            aVar.a = true;
        } else if ((bVar.a.c != 0 || (bVar.c != null && bVar.c.length != 0)) && (a = aVar.c.a(bVar.a.b)) != null) {
            a.a(bVar);
        }
    }
}
