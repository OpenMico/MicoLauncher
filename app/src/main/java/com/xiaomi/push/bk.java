package com.xiaomi.push;

import com.xiaomi.push.aj;
import com.xiaomi.push.bx;
import java.util.ArrayList;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class bk extends aj.a {
    final /* synthetic */ bx a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public bk(bx bxVar) {
        this.a = bxVar;
    }

    @Override // com.xiaomi.push.aj.a
    /* renamed from: a */
    public int mo834a() {
        return 100957;
    }

    @Override // java.lang.Runnable
    public void run() {
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        ArrayList arrayList4;
        ArrayList arrayList5;
        ArrayList<bx.a> arrayList6;
        arrayList = this.a.f;
        synchronized (arrayList) {
            arrayList2 = this.a.f;
            if (arrayList2.size() > 0) {
                arrayList3 = this.a.f;
                if (arrayList3.size() > 1) {
                    bx bxVar = this.a;
                    arrayList6 = this.a.f;
                    bxVar.a(arrayList6);
                } else {
                    bx bxVar2 = this.a;
                    arrayList5 = this.a.f;
                    bxVar2.b((bx.a) arrayList5.get(0));
                }
                arrayList4 = this.a.f;
                arrayList4.clear();
                System.gc();
            }
        }
    }
}
