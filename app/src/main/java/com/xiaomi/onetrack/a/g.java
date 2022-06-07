package com.xiaomi.onetrack.a;

import com.xiaomi.onetrack.util.p;
import java.util.ArrayList;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class g implements Runnable {
    final /* synthetic */ ArrayList a;
    final /* synthetic */ f b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public g(f fVar, ArrayList arrayList) {
        this.b = fVar;
        this.a = arrayList;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (p.a) {
            p.a("ConfigDbManager", "update: " + this.a);
        }
        this.b.b(this.a);
    }
}
