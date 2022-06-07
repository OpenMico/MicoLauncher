package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.util.p;

/* loaded from: classes4.dex */
class z implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ f c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public z(f fVar, String str, String str2) {
        this.c = fVar;
        this.a = str;
        this.b = str2;
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean c;
        d dVar;
        try {
            c = this.c.c(this.a);
            if (!c) {
                dVar = this.c.b;
                dVar.a(this.a, this.b);
            }
        } catch (Exception e) {
            p.b("OneTrackImp", "track map error: " + e.toString());
        }
    }
}
