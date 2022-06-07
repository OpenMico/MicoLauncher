package com.efs.sdk.base.a.e;

import androidx.annotation.NonNull;
import com.efs.sdk.base.a.a.c;
import com.efs.sdk.base.a.f.b;
import com.efs.sdk.base.a.h.d;
import com.efs.sdk.base.http.HttpResponse;
import java.util.HashMap;

/* loaded from: classes.dex */
public final class a implements c {
    @Override // com.efs.sdk.base.a.e.c
    @NonNull
    public final HttpResponse a(b bVar, boolean z) {
        HttpResponse httpResponse;
        c a = c.a();
        a.d = bVar.a.d;
        a.e = bVar.a.e;
        a.g = bVar.a.b;
        a.h = bVar.a.a;
        a.l = bVar.a();
        String a2 = com.efs.sdk.base.a.c.a.c.a().a(false);
        if (bVar.a.c == 0) {
            com.efs.sdk.base.a.a.a a3 = com.efs.sdk.base.a.a.a.a();
            byte[] bArr = bVar.c;
            boolean z2 = bVar.b.b;
            String b = a.b();
            String a4 = com.efs.sdk.base.a.a.a.a(a2, a);
            if (a3.a) {
                d.a("efs.px.api", "upload buffer file, url is ".concat(String.valueOf(a4)));
            }
            HashMap hashMap = new HashMap(1);
            hashMap.put("wpk-header", b);
            com.efs.sdk.base.a.h.b.d a5 = new com.efs.sdk.base.a.h.b.d(a4).a(hashMap);
            a5.a.c = bArr;
            a5.a.g = true;
            com.efs.sdk.base.a.h.b.d a6 = a5.a("type", a.h);
            StringBuilder sb = new StringBuilder();
            sb.append(a.l);
            httpResponse = a6.a("size", sb.toString()).a("flow_limit", Boolean.toString(z2)).a(com.efs.sdk.base.a.a.d.a()).a().b();
        } else if (1 == bVar.a.c) {
            httpResponse = com.efs.sdk.base.a.a.a.a().a(a2, a, bVar.d, bVar.b.b);
        } else {
            httpResponse = new HttpResponse();
        }
        if (httpResponse.succ && z) {
            com.efs.sdk.base.a.h.b.b(bVar.d);
        }
        return httpResponse;
    }
}
