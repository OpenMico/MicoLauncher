package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.push.dn;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class n implements Runnable {
    @Override // java.lang.Runnable
    public void run() {
        Context context;
        context = MiPushClient.b;
        dn.a(context);
    }
}
