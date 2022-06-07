package com.xiaomi.push.service;

import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.bc;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
class y implements Runnable {
    final /* synthetic */ bc a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public y(bc bcVar) {
        this.a = bcVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        ConcurrentHashMap concurrentHashMap;
        try {
            concurrentHashMap = this.a.e;
            for (bc.a aVar : concurrentHashMap.values()) {
                aVar.run();
            }
        } catch (Exception e) {
            b.m149a("Sync job exception :" + e.getMessage());
        }
        this.a.d = false;
    }
}
