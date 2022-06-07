package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.clientreport.manager.a;
import com.xiaomi.push.aj;

/* loaded from: classes4.dex */
public class bc extends aj.a {
    private Context a;

    public bc(Context context) {
        this.a = context;
    }

    private boolean b() {
        return a.a(this.a).a().isPerfUploadSwitchOpen();
    }

    @Override // com.xiaomi.push.aj.a
    /* renamed from: a */
    public int mo834a() {
        return 100887;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (b()) {
                a.a(this.a).c();
                b.c(this.a.getPackageName() + "perf  begin upload");
            }
        } catch (Exception e) {
            b.a(e);
        }
    }
}
