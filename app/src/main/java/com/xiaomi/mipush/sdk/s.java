package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.push.dl;
import com.xiaomi.push.hh;
import com.xiaomi.push.hu;
import com.xiaomi.push.ig;

/* loaded from: classes4.dex */
public class s implements dl {
    private Context a;

    public s(Context context) {
        this.a = context;
    }

    @Override // com.xiaomi.push.dl
    public String a() {
        return d.m727a(this.a).d();
    }

    @Override // com.xiaomi.push.dl
    public void a(ig igVar, hh hhVar, hu huVar) {
        ay.a(this.a).a((ay) igVar, hhVar, huVar);
    }
}
