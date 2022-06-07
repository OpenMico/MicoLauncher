package com.efs.sdk.base.a.g.a;

import com.efs.sdk.base.a.d.a;
import com.efs.sdk.base.a.g.b;
import com.efs.sdk.base.processor.action.ILogEncryptAction;

/* loaded from: classes.dex */
public final class d extends a {
    private ILogEncryptAction b;

    public d() {
        if (a.a().m == null) {
            this.b = new b();
        } else {
            this.b = a.a().m;
        }
    }

    @Override // com.efs.sdk.base.a.g.a.a
    public final void a(com.efs.sdk.base.a.f.b bVar) {
        if (c(bVar)) {
            b(bVar);
            return;
        }
        byte[] encrypt = this.b.encrypt(a.a().b, bVar.c);
        if (encrypt != null) {
            bVar.a(encrypt);
            bVar.a(this.b.getDeVal());
        }
        b(bVar);
    }

    private static boolean c(com.efs.sdk.base.a.f.b bVar) {
        return bVar.c() || "wa".equals(bVar.a.a) || (1 == bVar.a.b && !bVar.b.a) || 1 == bVar.a.c;
    }
}
