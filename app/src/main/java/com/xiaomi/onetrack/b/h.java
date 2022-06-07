package com.xiaomi.onetrack.b;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class h implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ String b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public h(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    @Override // java.lang.Runnable
    public void run() {
        g.c(this.a, this.b);
    }
}
