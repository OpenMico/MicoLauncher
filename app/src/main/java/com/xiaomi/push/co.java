package com.xiaomi.push;

import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class co extends cr {
    cr i;
    final /* synthetic */ cr j;
    final /* synthetic */ cv k;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public co(cv cvVar, String str, cr crVar) {
        super(str);
        this.k = cvVar;
        this.j = crVar;
        this.i = this.j;
        this.b = this.b;
        cr crVar2 = this.j;
        if (crVar2 != null) {
            this.f = crVar2.f;
        }
    }

    @Override // com.xiaomi.push.cr
    public synchronized ArrayList<String> a(boolean z) {
        ArrayList<String> arrayList;
        arrayList = new ArrayList<>();
        if (this.i != null) {
            arrayList.addAll(this.i.a(true));
        }
        synchronized (cv.b) {
            cr crVar = cv.b.get(this.b);
            if (crVar != null) {
                Iterator<String> it = crVar.a(true).iterator();
                while (it.hasNext()) {
                    String next = it.next();
                    if (arrayList.indexOf(next) == -1) {
                        arrayList.add(next);
                    }
                }
                arrayList.remove(this.b);
                arrayList.add(this.b);
            }
        }
        return arrayList;
    }

    @Override // com.xiaomi.push.cr
    public synchronized void a(String str, cq cqVar) {
        if (this.i != null) {
            this.i.a(str, cqVar);
        }
    }

    @Override // com.xiaomi.push.cr
    public boolean b() {
        return false;
    }
}
