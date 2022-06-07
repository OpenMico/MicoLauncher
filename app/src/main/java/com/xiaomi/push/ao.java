package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.aj;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class ao extends aj.a {
    final /* synthetic */ bl a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ao(bl blVar) {
        this.a = blVar;
    }

    @Override // com.xiaomi.push.aj.a
    /* renamed from: a */
    public int mo834a() {
        return 10052;
    }

    @Override // java.lang.Runnable
    public void run() {
        cb cbVar;
        cb cbVar2;
        Context context;
        b.c("exec== mUploadJob");
        cbVar = this.a.j;
        if (cbVar != null) {
            cbVar2 = this.a.j;
            context = this.a.f;
            cbVar2.a(context);
            this.a.b("upload_time");
        }
    }
}
