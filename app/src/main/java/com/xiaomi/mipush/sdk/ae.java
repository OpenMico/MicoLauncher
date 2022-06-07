package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;

/* loaded from: classes4.dex */
final class ae implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ Intent b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ae(Context context, Intent intent) {
        this.a = context;
        this.b = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        PushMessageHandler.b(this.a, this.b);
    }
}
