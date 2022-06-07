package com.xiaomi.push.service.receivers;

import android.content.Context;

/* loaded from: classes4.dex */
class a implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ NetworkStatusReceiver b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(NetworkStatusReceiver networkStatusReceiver, Context context) {
        this.b = networkStatusReceiver;
        this.a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.a(this.a);
    }
}
