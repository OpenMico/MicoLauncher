package com.xiaomi.mipush.sdk;

import com.xiaomi.mipush.sdk.MiTinyDataClient;
import java.util.concurrent.ScheduledFuture;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class ab implements Runnable {
    final /* synthetic */ MiTinyDataClient.a.C0177a a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ab(MiTinyDataClient.a.C0177a aVar) {
        this.a = aVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        ScheduledFuture scheduledFuture;
        ScheduledFuture scheduledFuture2;
        if (this.a.a.size() != 0) {
            this.a.b();
            return;
        }
        scheduledFuture = this.a.d;
        if (scheduledFuture != null) {
            scheduledFuture2 = this.a.d;
            scheduledFuture2.cancel(false);
            this.a.d = null;
        }
    }
}
