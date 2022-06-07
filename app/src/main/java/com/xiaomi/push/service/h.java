package com.xiaomi.push.service;

import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class h implements Runnable {
    final /* synthetic */ List a;
    final /* synthetic */ boolean b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public h(List list, boolean z) {
        this.a = list;
        this.b = z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x001b, code lost:
        r0 = com.xiaomi.push.service.ac.b(r2);
     */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() {
        /*
            r4 = this;
            java.lang.String r0 = "www.baidu.com:80"
            boolean r0 = com.xiaomi.push.service.ac.a(r0)
            java.util.List r1 = r4.a
            java.util.Iterator r1 = r1.iterator()
        L_0x000c:
            boolean r2 = r1.hasNext()
            r3 = 1
            if (r2 == 0) goto L_0x002b
            java.lang.Object r2 = r1.next()
            java.lang.String r2 = (java.lang.String) r2
            if (r0 != 0) goto L_0x0024
            boolean r0 = com.xiaomi.push.service.ac.a(r2)
            if (r0 == 0) goto L_0x0022
            goto L_0x0024
        L_0x0022:
            r0 = 0
            goto L_0x0025
        L_0x0024:
            r0 = r3
        L_0x0025:
            if (r0 == 0) goto L_0x000c
            boolean r2 = r4.b
            if (r2 != 0) goto L_0x000c
        L_0x002b:
            if (r0 == 0) goto L_0x002e
            goto L_0x002f
        L_0x002e:
            r3 = 2
        L_0x002f:
            com.xiaomi.push.hb.a(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.h.run():void");
    }
}
