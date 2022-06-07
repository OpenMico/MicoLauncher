package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.clientreport.manager.a;
import com.xiaomi.push.aj;

/* loaded from: classes4.dex */
public class bb extends aj.a {
    private Context a;

    public bb(Context context) {
        this.a = context;
    }

    private boolean b() {
        return a.a(this.a).a().isEventUploadSwitchOpen();
    }

    @Override // com.xiaomi.push.aj.a
    /* renamed from: a */
    public int mo834a() {
        return 100886;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (b()) {
                b.c(this.a.getPackageName() + " begin upload event");
                a.a(this.a).b();
            }
        } catch (Exception e) {
            b.a(e);
        }
    }
}
