package com.xiaomi.onetrack.b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaomi.onetrack.f.c;
import com.xiaomi.onetrack.util.p;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class l extends BroadcastReceiver {
    final /* synthetic */ k a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public l(k kVar) {
        this.a = kVar;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        boolean a = c.a();
        p.a("UploadTimer", "UploadTimer netReceiver, 网络是否可用=" + a);
        this.a.a();
        if (a) {
            int[] iArr = {0, 1, 2};
            for (int i : iArr) {
                int a2 = com.xiaomi.onetrack.a.l.a(i);
                if (!this.a.hasMessages(i)) {
                    this.a.sendEmptyMessageDelayed(i, a2);
                }
            }
        }
    }
}
