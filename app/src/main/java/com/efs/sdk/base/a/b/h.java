package com.efs.sdk.base.a.b;

import com.efs.sdk.base.a.b.a;
import com.efs.sdk.base.a.f.b;
import java.io.File;

/* loaded from: classes.dex */
public final class h implements f {
    @Override // com.efs.sdk.base.a.b.f
    public final boolean a(File file) {
        a unused;
        b b = com.efs.sdk.base.a.h.b.b(file.getName());
        if (b != null) {
            return !"wa".equals(b.a.a) && !com.efs.sdk.base.a.e.b.a().a(b.a.a, file.length());
        }
        unused = a.b.a;
        a.b(file);
        return true;
    }
}
