package com.xiaomi.push;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class dg implements Runnable {
    final /* synthetic */ dt a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public dg(dt dtVar) {
        this.a = dtVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.a.b();
    }
}
