package com.efs.sdk.base.a.e;

import com.efs.sdk.base.a.f.b;
import com.efs.sdk.base.http.HttpResponse;

/* loaded from: classes.dex */
public final class e implements Runnable {
    private b a;
    private c b;
    private String c;

    public e(b bVar, c cVar, String str) {
        this.a = bVar;
        this.b = cVar;
        this.c = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        HttpResponse httpResponse;
        c cVar;
        b bVar = this.a;
        if (bVar == null || (cVar = this.b) == null) {
            httpResponse = new HttpResponse();
        } else {
            httpResponse = cVar.a(bVar, true);
        }
        d.a().a(this.c, httpResponse.succ ? 0 : httpResponse.getHttpCode());
        this.c = null;
        this.b = null;
    }
}
