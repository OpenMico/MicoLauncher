package com.xiaomi.push;

import android.util.SparseArray;
import com.xiaomi.push.aj;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class r extends aj.b {
    final /* synthetic */ aj b;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public r(aj ajVar, aj.a aVar) {
        super(aVar);
        this.b = ajVar;
    }

    @Override // com.xiaomi.push.aj.b
    void b() {
        Object obj;
        SparseArray sparseArray;
        obj = this.b.d;
        synchronized (obj) {
            sparseArray = this.b.c;
            sparseArray.remove(this.a.mo834a());
        }
    }
}
