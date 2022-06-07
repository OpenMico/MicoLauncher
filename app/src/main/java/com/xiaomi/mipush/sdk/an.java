package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.miplay.mylibrary.DataModel;
import com.xiaomi.push.aj;
import com.xiaomi.push.hh;
import com.xiaomi.push.hn;
import com.xiaomi.push.hr;
import com.xiaomi.push.hu;
import com.xiaomi.push.hz;
import com.xiaomi.push.ig;
import com.xiaomi.push.ir;
import com.xiaomi.push.service.ag;
import com.xiaomi.push.service.ah;

/* loaded from: classes4.dex */
public class an extends aj.a {
    private Context a;

    public an(Context context) {
        this.a = context;
    }

    @Override // com.xiaomi.push.aj.a
    /* renamed from: a */
    public int mo834a() {
        return 2;
    }

    @Override // java.lang.Runnable
    public void run() {
        ag a = ag.a(this.a);
        hz hzVar = new hz();
        hzVar.a(ah.a(a, hn.MISC_CONFIG));
        hzVar.b(ah.a(a, hn.PLUGIN_CONFIG));
        ig igVar = new ig(DataModel.CIRCULATEFAIL_NO_SUPPORT, false);
        igVar.c(hr.DailyCheckClientConfig.f67a);
        igVar.a(ir.a(hzVar));
        ay.a(this.a).a((ay) igVar, hh.Notification, (hu) null);
    }
}
