package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.b.n;

/* loaded from: classes4.dex */
class ag implements Runnable {
    final /* synthetic */ af a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ag(af afVar) {
        this.a = afVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        n.a().a(0, true);
        n.a().a(1, true);
    }
}
