package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.util.aa;
import com.xiaomi.onetrack.util.r;
import com.xiaomi.onetrack.util.v;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class x implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ f b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public x(f fVar, boolean z) {
        this.b = fVar;
        this.a = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        v vVar;
        Configuration configuration;
        vVar = this.b.i;
        vVar.a(this.a);
        configuration = this.b.f;
        aa.a(r.a(configuration), this.a);
    }
}
