package com.xiaomi.onetrack.b;

import com.xiaomi.onetrack.e.b;
import com.xiaomi.onetrack.util.p;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class d implements Runnable {
    final /* synthetic */ b a;
    final /* synthetic */ b b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(b bVar, b bVar2) {
        this.b = bVar;
        this.a = bVar2;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.b.b(this.a);
            p.a("EventManager", "addEvent: " + this.a.d() + "data:" + this.a.f().toString());
            n.a().a(this.a.e(), false);
        } catch (Exception e) {
            p.b("EventManager", "EventManager.addEvent exception: ", e);
        }
    }
}
