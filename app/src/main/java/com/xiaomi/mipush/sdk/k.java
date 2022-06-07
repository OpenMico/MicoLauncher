package com.xiaomi.mipush.sdk;

import android.content.Context;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class k implements Runnable {
    final /* synthetic */ Context a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public k(Context context) {
        this.a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        MessageHandleService.c(this.a);
    }
}
