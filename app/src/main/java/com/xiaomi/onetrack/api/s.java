package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.util.o;
import com.xiaomi.onetrack.util.p;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class s implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ f b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public s(f fVar, String str) {
        this.b = fVar;
        this.a = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            o.a().a(this.a);
        } catch (Exception e) {
            p.b("OneTrackImp", "setInstanceId error: " + e.toString());
        }
    }
}
