package com.efs.sdk.base.a.i;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.efs.sdk.base.a.e.c;
import com.efs.sdk.base.a.f.b;
import com.efs.sdk.base.a.h.b.d;
import com.efs.sdk.base.a.i.f;
import com.efs.sdk.base.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class e implements c {
    private static void a(HttpResponse httpResponse) {
        if (httpResponse != null && !TextUtils.isEmpty(httpResponse.data)) {
            for (String str : httpResponse.data.split("`")) {
                String[] split = str.split("=");
                if (split.length >= 2) {
                    if (split[0].equalsIgnoreCase("retcode")) {
                        httpResponse.setBizCode(split[1]);
                    } else {
                        ((Map) httpResponse.extra).put(split[0], split[1]);
                    }
                }
            }
        }
    }

    @Override // com.efs.sdk.base.a.e.c
    @NonNull
    public final HttpResponse a(b bVar, boolean z) {
        f fVar;
        HttpResponse httpResponse = null;
        try {
            fVar = f.a.a;
            c cVar = fVar.a;
            String valueOf = String.valueOf(System.currentTimeMillis());
            String a = com.efs.sdk.base.a.h.c.b.a(cVar.b + cVar.c + valueOf + "AppChk#2014");
            StringBuilder sb = new StringBuilder();
            String str = cVar.a;
            if (str.startsWith("http")) {
                sb.append(str);
                sb.append("?chk=");
            } else {
                sb.append(str);
                sb.append("?chk=");
            }
            sb.append(a.substring(a.length() - 8));
            sb.append("&vno=");
            sb.append(valueOf);
            sb.append("&uuid=");
            sb.append(cVar.c);
            sb.append("&app=");
            sb.append(cVar.b);
            sb.append("&zip=gzip");
            String sb2 = sb.toString();
            int i = 0;
            byte[] bArr = new byte[0];
            if (bVar.a.c == 0) {
                bArr = bVar.c;
                i = bArr.length;
            } else if (1 == bVar.a.c) {
                bArr = com.efs.sdk.base.a.h.b.a(bVar.d.getPath());
                i = bArr.length;
            }
            HashMap hashMap = new HashMap();
            hashMap.put("Content-Type", "application/x-www-form-urlencoded");
            hashMap.put("Content-Length", String.valueOf(i));
            d a2 = new d(sb2).a(hashMap);
            a2.a.c = bArr;
            httpResponse = a2.a().b();
            a(httpResponse);
        } catch (Throwable th) {
            if (0 == 0) {
                httpResponse = new HttpResponse();
            }
            com.efs.sdk.base.a.h.d.b("efs.wa.send", "get file size error", th);
        }
        if (httpResponse.succ) {
            com.efs.sdk.base.a.h.d.a("efs.base", "wa upload succ, " + httpResponse.toString());
            com.efs.sdk.base.a.h.b.b(bVar.d);
            return httpResponse;
        }
        com.efs.sdk.base.a.h.d.a("efs.base", "wa upload fail, resp is " + httpResponse.toString());
        return httpResponse;
    }
}
