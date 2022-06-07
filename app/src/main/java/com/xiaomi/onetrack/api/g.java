package com.xiaomi.onetrack.api;

import android.text.TextUtils;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrackDebugger;
import com.xiaomi.onetrack.util.aa;
import com.xiaomi.onetrack.util.d;
import com.xiaomi.onetrack.util.o;
import com.xiaomi.onetrack.util.p;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class g implements Runnable {
    final /* synthetic */ f a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public g(f fVar) {
        this.a = fVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        Configuration configuration;
        Configuration configuration2;
        Configuration configuration3;
        if (aa.B() == 0) {
            aa.n(System.currentTimeMillis());
        }
        configuration = this.a.f;
        if (!TextUtils.isEmpty(configuration.getInstanceId())) {
            o a = o.a();
            configuration3 = this.a.f;
            a.a(configuration3.getInstanceId());
        }
        this.a.j();
        d.a();
        com.xiaomi.onetrack.b.g.c(false);
        if (p.a) {
            OneTrackDebugger instance = OneTrackDebugger.getInstance();
            configuration2 = this.a.f;
            instance.setSdkConfig(configuration2);
            OneTrackDebugger.getInstance().startDebugger();
        }
    }
}
