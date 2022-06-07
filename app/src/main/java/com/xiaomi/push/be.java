package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.clientreport.processor.d;

/* loaded from: classes4.dex */
public class be implements Runnable {
    private d a;
    private Context b;

    public be(Context context, d dVar) {
        this.b = context;
        this.a = dVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (this.a != null) {
                this.a.b();
            }
        } catch (Exception e) {
            b.a(e);
        }
    }
}
