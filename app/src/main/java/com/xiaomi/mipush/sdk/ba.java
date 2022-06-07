package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.push.el;
import com.xiaomi.push.hm;
import com.xiaomi.push.service.ag;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class ba extends ag.a {
    final /* synthetic */ Context a;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ba(int i, String str, Context context) {
        super(i, str);
        this.a = context;
    }

    @Override // com.xiaomi.push.service.ag.a
    protected void a() {
        el.a(this.a).a(ag.a(this.a).a(hm.AwakeInfoUploadWaySwitch.a(), 0));
    }
}
