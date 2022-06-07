package com.efs.sdk.base.a.a;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.efs.sdk.base.a.h.a.c;
import com.efs.sdk.base.a.i.f;
import com.efs.sdk.base.http.AbsHttpListener;
import com.efs.sdk.base.http.HttpResponse;
import java.util.Map;

/* loaded from: classes.dex */
public final class b extends AbsHttpListener {
    /* synthetic */ b(byte b) {
        this();
    }

    @Override // com.efs.sdk.base.a.h.a.b
    public final /* bridge */ /* synthetic */ void a(@NonNull c<HttpResponse> cVar, @Nullable HttpResponse httpResponse) {
        HttpResponse httpResponse2 = httpResponse;
        if (httpResponse2 != null) {
            a.a();
            a.a(httpResponse2);
        }
    }

    private b() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a {
        private static final b a = new b((byte) 0);
    }

    public static b a() {
        return a.a;
    }

    @Override // com.efs.sdk.base.http.AbsHttpListener
    public final void onSuccess(@NonNull HttpResponse httpResponse) {
        f fVar;
        a(httpResponse);
        if (((Map) httpResponse.extra).containsKey("cver")) {
            String str = (String) ((Map) httpResponse.extra).get("cver");
            if (!TextUtils.isEmpty(str)) {
                int parseInt = Integer.parseInt(str);
                fVar = f.a.a;
                if (fVar.b != null && com.efs.sdk.base.a.d.a.a().d) {
                    com.efs.sdk.base.a.i.b bVar = new com.efs.sdk.base.a.i.b("efs_core", "config_coverage", fVar.a.c);
                    bVar.put("cver", Integer.valueOf(parseInt));
                    fVar.b.a(bVar);
                }
            }
        }
    }

    @Override // com.efs.sdk.base.http.AbsHttpListener
    public final void onError(@Nullable HttpResponse httpResponse) {
        if (httpResponse != null) {
            a(httpResponse);
        }
    }

    private static void a(@NonNull HttpResponse httpResponse) {
        f fVar;
        fVar = f.a.a;
        fVar.a(String.valueOf(httpResponse.getHttpCode()), httpResponse.getBizCode(), httpResponse.getReqUrl());
    }
}
