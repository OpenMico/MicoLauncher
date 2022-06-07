package com.xiaomi.clientreport.manager;

import com.xiaomi.push.bb;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class d implements Runnable {
    final /* synthetic */ bb a;
    final /* synthetic */ a b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(a aVar, bb bbVar) {
        this.b = aVar;
        this.a = bbVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.a.run();
    }
}
