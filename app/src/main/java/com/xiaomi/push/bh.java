package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.push.aj;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class bh extends aj.a {
    final /* synthetic */ bl a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public bh(bl blVar) {
        this.a = blVar;
    }

    @Override // com.xiaomi.push.aj.a
    /* renamed from: a */
    public int mo834a() {
        return 10053;
    }

    @Override // java.lang.Runnable
    public void run() {
        cb cbVar;
        cb cbVar2;
        Context context;
        cbVar = this.a.j;
        if (cbVar != null) {
            cbVar2 = this.a.j;
            context = this.a.f;
            cbVar2.b(context);
            this.a.b("delete_time");
        }
    }
}
