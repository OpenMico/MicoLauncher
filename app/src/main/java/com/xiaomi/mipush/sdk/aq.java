package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.push.hm;
import com.xiaomi.push.service.ag;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class aq extends ag.a {
    final /* synthetic */ g a;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public aq(g gVar, int i, String str) {
        super(i, str);
        this.a = gVar;
    }

    @Override // com.xiaomi.push.service.ag.a
    protected void a() {
        Context context;
        boolean z;
        Context context2;
        context = this.a.b;
        boolean a = ag.a(context).a(hm.AggregatePushSwitch.a(), true);
        z = this.a.d;
        if (z != a) {
            this.a.d = a;
            context2 = this.a.b;
            j.b(context2);
        }
    }
}
