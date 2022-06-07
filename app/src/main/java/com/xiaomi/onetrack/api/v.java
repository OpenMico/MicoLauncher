package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.b.g;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class v implements Runnable {
    final /* synthetic */ f a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public v(f fVar) {
        this.a = fVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        d dVar;
        dVar = this.a.b;
        g.a(dVar);
    }
}
