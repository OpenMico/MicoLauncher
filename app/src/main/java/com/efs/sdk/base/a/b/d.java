package com.efs.sdk.base.a.b;

import com.efs.sdk.base.a.h.a;
import com.efs.sdk.base.a.h.b;
import com.efs.sdk.base.a.i.f;
import java.io.File;

/* loaded from: classes.dex */
public final class d implements e {
    @Override // com.efs.sdk.base.a.b.e
    public final void a(String str) {
    }

    @Override // com.efs.sdk.base.a.b.e
    public final void a(File file) {
        b.a(file, a.f(com.efs.sdk.base.a.d.a.a().c, com.efs.sdk.base.a.d.a.a().a));
    }

    @Override // com.efs.sdk.base.a.b.e
    public final boolean a(File file, com.efs.sdk.base.a.f.b bVar) {
        if (!file.exists()) {
            return false;
        }
        bVar.d = file;
        bVar.d();
        bVar.b(1);
        return true;
    }

    @Override // com.efs.sdk.base.a.b.e
    public final void a(com.efs.sdk.base.a.f.b bVar) {
        f fVar;
        f fVar2;
        if (bVar.c != null) {
            b.a(new File(a.f(com.efs.sdk.base.a.d.a.a().c, com.efs.sdk.base.a.d.a.a().a), b.a(bVar)), bVar.c);
            fVar = f.a.a;
            fVar.c.b();
            fVar2 = f.a.a;
            fVar2.c.c();
        }
    }
}
