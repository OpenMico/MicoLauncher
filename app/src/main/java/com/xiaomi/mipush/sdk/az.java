package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.push.aj;
import com.xiaomi.push.hh;
import com.xiaomi.push.hu;
import com.xiaomi.push.ig;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class az extends aj.a {
    final /* synthetic */ ig a;
    final /* synthetic */ Context b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public az(ig igVar, Context context) {
        this.a = igVar;
        this.b = context;
    }

    @Override // com.xiaomi.push.aj.a
    /* renamed from: a */
    public int mo834a() {
        return 22;
    }

    @Override // java.lang.Runnable
    public void run() {
        ig igVar = this.a;
        if (igVar != null) {
            igVar.a(com.xiaomi.push.service.aj.a());
            ay.a(this.b.getApplicationContext()).a((ay) this.a, hh.Notification, true, (hu) null, true);
        }
    }
}
