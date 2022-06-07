package com.xiaomi.push;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class fh implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ fu b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public fh(fu fuVar, String str) {
        this.b = fuVar;
        this.a = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        cv.a().a(this.a, true);
    }
}
