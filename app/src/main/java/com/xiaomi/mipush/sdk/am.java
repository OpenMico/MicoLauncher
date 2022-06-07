package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.push.hm;
import com.xiaomi.push.service.ag;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class am implements Runnable {
    final /* synthetic */ Context a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public am(Context context) {
        this.a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean d;
        String b;
        d = b.d(this.a);
        if (d) {
            b = b.b(ag.a(this.a).a(hm.AggregationSdkMonitorDepth.a(), 4));
            if (!TextUtils.isEmpty(b)) {
                MiTinyDataClient.upload(this.a, "monitor_upload", "call_stack", 1L, b);
                b.e(this.a);
            }
        }
    }
}
