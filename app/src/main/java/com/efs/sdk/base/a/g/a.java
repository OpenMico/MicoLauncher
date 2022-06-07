package com.efs.sdk.base.a.g;

import com.efs.sdk.base.a.f.b;
import com.efs.sdk.base.a.h.d;

/* loaded from: classes.dex */
public abstract class a {
    private com.efs.sdk.base.a.g.a.a a;

    public abstract com.efs.sdk.base.a.g.a.a a();

    public final void a(b bVar) {
        try {
            if (this.a == null) {
                synchronized (this) {
                    if (this.a == null) {
                        this.a = a();
                        if (this.a == null) {
                            return;
                        }
                    }
                }
            }
            this.a.a(bVar);
        } catch (Throwable th) {
            d.b("efs.processor", "log handle error", th);
        }
    }
}
