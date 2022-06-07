package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import com.xiaomi.channel.commonutils.logger.b;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class i implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ Intent b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public i(Context context, Intent intent) {
        this.a = context;
        this.b = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.a.startService(this.b);
        } catch (Exception e) {
            b.m149a(e.getMessage());
        }
    }
}
