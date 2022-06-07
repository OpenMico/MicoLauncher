package com.xiaomi.clientreport.manager;

import com.xiaomi.push.bc;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class e implements Runnable {
    final /* synthetic */ bc a;
    final /* synthetic */ a b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(a aVar, bc bcVar) {
        this.b = aVar;
        this.a = bcVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.a.run();
    }
}
