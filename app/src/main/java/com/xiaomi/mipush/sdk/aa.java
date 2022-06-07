package com.xiaomi.mipush.sdk;

import com.xiaomi.mipush.sdk.MiTinyDataClient;
import com.xiaomi.push.hl;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class aa implements Runnable {
    final /* synthetic */ hl a;
    final /* synthetic */ MiTinyDataClient.a.C0177a b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public aa(MiTinyDataClient.a.C0177a aVar, hl hlVar) {
        this.b = aVar;
        this.a = hlVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.a.add(this.a);
        this.b.a();
    }
}
