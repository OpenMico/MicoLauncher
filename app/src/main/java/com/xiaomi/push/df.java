package com.xiaomi.push;

import android.content.Context;
import android.content.Intent;

/* loaded from: classes4.dex */
class df implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ Intent b;
    final /* synthetic */ dd c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public df(dd ddVar, Context context, Intent intent) {
        this.c = ddVar;
        this.a = context;
        this.b = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.c.b(this.a, this.b);
    }
}
