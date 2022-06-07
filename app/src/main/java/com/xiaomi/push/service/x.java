package com.xiaomi.push.service;

import android.util.Base64;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.am;
import com.xiaomi.push.as;
import com.xiaomi.push.cz;
import com.xiaomi.push.ee;
import com.xiaomi.push.service.ba;
import com.xiaomi.push.u;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class x extends am.b {
    boolean a = false;
    final /* synthetic */ ba b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public x(ba baVar) {
        this.b = baVar;
    }

    @Override // com.xiaomi.push.am.b
    public void b() {
        try {
            ee.a a = ee.a.a(Base64.decode(cz.a(u.m1170a(), "http://resolver.msg.xiaomi.net/psc/?t=a", (List<as>) null), 10));
            if (a != null) {
                this.b.c = a;
                this.a = true;
                this.b.g();
            }
        } catch (Exception e) {
            b.m149a("fetch config failure: " + e.getMessage());
        }
    }

    @Override // com.xiaomi.push.am.b
    public void c() {
        List list;
        List list2;
        ba.a[] aVarArr;
        ee.a aVar;
        this.b.d = null;
        if (this.a) {
            synchronized (this.b) {
                list = this.b.b;
                list2 = this.b.b;
                aVarArr = (ba.a[]) list.toArray(new ba.a[list2.size()]);
            }
            for (ba.a aVar2 : aVarArr) {
                aVar = this.b.c;
                aVar2.a(aVar);
            }
        }
    }
}
