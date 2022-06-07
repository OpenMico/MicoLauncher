package com.xiaomi.push;

import com.xiaomi.push.am;
import com.xiaomi.push.de;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class da extends am.b {
    am.b a;
    final /* synthetic */ de b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public da(de deVar) {
        this.b = deVar;
    }

    @Override // com.xiaomi.push.am.b
    public void b() {
        de.b bVar = (de.b) this.b.a.peek();
        if (bVar != null && bVar.d()) {
            if (this.b.a.remove(bVar)) {
                this.a = bVar;
            }
            am.b bVar2 = this.a;
            if (bVar2 != null) {
                bVar2.b();
            }
        }
    }

    @Override // com.xiaomi.push.am.b
    public void c() {
        am.b bVar = this.a;
        if (bVar != null) {
            bVar.c();
        }
    }
}
