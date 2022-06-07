package com.efs.sdk.base.a.g.a;

import com.efs.sdk.base.a.e.d;
import com.efs.sdk.base.a.f.b;
import com.efs.sdk.base.a.i.f;
import com.efs.sdk.base.http.HttpResponse;

/* loaded from: classes.dex */
public final class e extends a {
    @Override // com.efs.sdk.base.a.g.a.a
    public final void a(b bVar) {
        HttpResponse httpResponse;
        f fVar;
        f fVar2;
        if (!bVar.b.a) {
            b(bVar);
            return;
        }
        d a = d.a();
        if (!bVar.b.b || com.efs.sdk.base.a.e.b.a().a(bVar.a.a, bVar.a())) {
            fVar = f.a.a;
            fVar.c.b();
            fVar2 = f.a.a;
            fVar2.c.c();
            httpResponse = a.b.a(bVar, false);
        } else {
            httpResponse = new HttpResponse();
            httpResponse.data = "flow_limit";
        }
        bVar.b.c = httpResponse;
        b(bVar);
    }
}
