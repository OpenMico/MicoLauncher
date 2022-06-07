package com.xiaomi.onetrack.b;

import com.xiaomi.onetrack.a.c;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class m implements Runnable {
    final /* synthetic */ k a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public m(k kVar) {
        this.a = kVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        AtomicBoolean atomicBoolean;
        AtomicBoolean atomicBoolean2;
        atomicBoolean = this.a.i;
        if (atomicBoolean.get()) {
            c.b();
        }
        atomicBoolean2 = this.a.i;
        atomicBoolean2.set(true);
    }
}
