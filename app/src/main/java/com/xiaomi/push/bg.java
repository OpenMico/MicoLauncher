package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.aj;
import java.lang.ref.WeakReference;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class bg extends aj.a {
    final /* synthetic */ bl a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public bg(bl blVar) {
        this.a = blVar;
    }

    @Override // com.xiaomi.push.aj.a
    /* renamed from: a */
    public int mo834a() {
        return 10054;
    }

    @Override // java.lang.Runnable
    public void run() {
        String d;
        Context context;
        Context context2;
        b.c("exec== DbSizeControlJob");
        d = this.a.d();
        context = this.a.f;
        bq bqVar = new bq(d, new WeakReference(context));
        context2 = this.a.f;
        bx.a(context2).a(bqVar);
        this.a.b("check_time");
    }
}
