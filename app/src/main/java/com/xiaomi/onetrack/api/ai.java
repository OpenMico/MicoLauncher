package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.util.p;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class ai implements Runnable {
    final /* synthetic */ ah a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ai(ah ahVar) {
        this.a = ahVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        ConcurrentHashMap concurrentHashMap;
        ConcurrentHashMap concurrentHashMap2;
        ConcurrentHashMap concurrentHashMap3;
        ConcurrentHashMap concurrentHashMap4;
        aj ajVar;
        Configuration configuration;
        try {
            concurrentHashMap = this.a.d;
            synchronized (concurrentHashMap) {
                concurrentHashMap2 = this.a.d;
                if (concurrentHashMap2.size() > 0) {
                    concurrentHashMap3 = this.a.d;
                    for (Map.Entry entry : concurrentHashMap3.entrySet()) {
                        String str = (String) entry.getKey();
                        String str2 = (String) entry.getValue();
                        ajVar = this.a.f;
                        configuration = this.a.e;
                        ajVar.b(str2, str, configuration);
                        if (p.a) {
                            p.a("OneTrackSystemImp", "name:" + str2 + "data :" + str);
                        }
                    }
                    concurrentHashMap4 = this.a.d;
                    concurrentHashMap4.clear();
                }
            }
        } catch (Exception e) {
            p.a("OneTrackSystemImp", "trackCachedEvents: " + e.toString());
        }
    }
}
