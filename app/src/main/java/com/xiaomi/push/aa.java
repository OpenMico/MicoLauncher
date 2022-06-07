package com.xiaomi.push;

import com.xiaomi.push.am;

/* loaded from: classes4.dex */
class aa implements Runnable {
    final /* synthetic */ am.b a;
    final /* synthetic */ am b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public aa(am amVar, am.b bVar) {
        this.b = amVar;
        this.a = bVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.a(this.a);
    }
}
