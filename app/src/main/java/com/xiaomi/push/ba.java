package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.clientreport.data.a;
import com.xiaomi.clientreport.processor.d;

/* loaded from: classes4.dex */
public class ba implements Runnable {
    private Context a;
    private a b;
    private d c;

    public ba(Context context, a aVar, d dVar) {
        this.a = context;
        this.b = aVar;
        this.c = dVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        d dVar = this.c;
        if (dVar != null) {
            dVar.mo151a(this.b);
        }
    }
}
