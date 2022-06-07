package com.xiaomi.push;

import com.xiaomi.push.am;

/* loaded from: classes4.dex */
final class fw extends am.b {
    final /* synthetic */ Runnable a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public fw(Runnable runnable) {
        this.a = runnable;
    }

    @Override // com.xiaomi.push.am.b
    public void b() {
        this.a.run();
    }
}
