package com.xiaomi.push.service;

import android.app.NotificationManager;
import com.xiaomi.push.aj;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class c extends aj.a {
    final /* synthetic */ int a;
    final /* synthetic */ NotificationManager b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(int i, NotificationManager notificationManager) {
        this.a = i;
        this.b = notificationManager;
    }

    @Override // com.xiaomi.push.aj.a
    /* renamed from: a */
    public int mo834a() {
        return this.a;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.cancel(this.a);
    }
}
