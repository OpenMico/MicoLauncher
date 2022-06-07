package com.xiaomi.push;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class ey extends Thread {
    final /* synthetic */ fl a;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ey(fl flVar, String str) {
        super(str);
        this.a = flVar;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        es esVar;
        try {
            esVar = this.a.j;
            esVar.a();
        } catch (Exception e) {
            this.a.c(9, e);
        }
    }
}
