package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.util.k;
import com.xiaomi.onetrack.util.r;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class p implements Runnable {
    final /* synthetic */ f a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public p(f fVar) {
        this.a = fVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        Configuration configuration;
        configuration = this.a.f;
        k.b(r.a(configuration));
    }
}
