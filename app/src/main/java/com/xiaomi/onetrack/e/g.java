package com.xiaomi.onetrack.e;

import android.content.Context;
import com.xiaomi.onetrack.b.b;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class g implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ f b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public g(f fVar, Context context) {
        this.b = fVar;
        this.a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        f.c(this.a);
        b.a(this.a);
    }
}
