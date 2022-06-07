package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.bx;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class bq implements Runnable {
    private String a;
    private WeakReference<Context> b;

    public bq(String str, WeakReference<Context> weakReference) {
        this.a = str;
        this.b = weakReference;
    }

    @Override // java.lang.Runnable
    public void run() {
        Context context;
        WeakReference<Context> weakReference = this.b;
        if (weakReference != null && (context = weakReference.get()) != null) {
            if (cd.a(this.a) > bp.f14a) {
                bt a = bt.a(this.a);
                bs a2 = bs.a(this.a);
                a.a(a2);
                a2.a(br.a(context, this.a, 1000));
                bx.a(context).a((bx.a) a);
                return;
            }
            b.b("=====> do not need clean db");
        }
    }
}
