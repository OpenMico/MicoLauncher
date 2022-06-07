package com.efs.sdk.base.a.a;

import android.os.Message;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.efs.sdk.base.a.h.a.c;
import com.efs.sdk.base.a.h.b.b;
import com.efs.sdk.base.a.i.f;
import com.efs.sdk.base.http.AbsHttpListener;
import com.efs.sdk.base.http.HttpResponse;
import java.util.Map;

/* loaded from: classes.dex */
public final class d extends AbsHttpListener {
    /* synthetic */ d(byte b) {
        this();
    }

    @Override // com.efs.sdk.base.a.h.a.b
    public final /* synthetic */ void a(@NonNull c<HttpResponse> cVar, @Nullable HttpResponse httpResponse) {
        HttpResponse httpResponse2 = httpResponse;
        if (httpResponse2 != null) {
            b bVar = (b) cVar;
            ((Map) httpResponse2.extra).putAll(bVar.f);
            bVar.f.clear();
            a.a();
            a.a(httpResponse2);
        }
    }

    private d() {
    }

    /* loaded from: classes.dex */
    static class a {
        private static final d a = new d((byte) 0);
    }

    public static d a() {
        return a.a;
    }

    @Override // com.efs.sdk.base.http.AbsHttpListener
    public final void onError(@Nullable HttpResponse httpResponse) {
        a(httpResponse);
        if (httpResponse != null) {
            b(httpResponse);
            c(httpResponse);
        }
    }

    private static void a(@Nullable HttpResponse httpResponse) {
        String str;
        if (com.efs.sdk.base.a.d.a.a().f) {
            if (httpResponse == null) {
                str = "upload result : false";
            } else {
                str = "upload result : " + httpResponse.succ + ", resp is " + httpResponse.toString();
            }
            com.efs.sdk.base.a.h.d.a("efs.px.api", str);
        }
    }

    private static void c(HttpResponse httpResponse) {
        int parseInt;
        if (((Map) httpResponse.extra).containsKey("cver")) {
            String str = (String) ((Map) httpResponse.extra).get("cver");
            if (!TextUtils.isEmpty(str) && (parseInt = Integer.parseInt(str)) > com.efs.sdk.base.a.c.a.c.a().d.a) {
                com.efs.sdk.base.a.c.a.c.a().a(parseInt);
            }
        }
    }

    @Override // com.efs.sdk.base.http.AbsHttpListener
    public final void onSuccess(@NonNull HttpResponse httpResponse) {
        int i;
        if (!((Map) httpResponse.extra).containsKey("flow_limit") || !Boolean.FALSE.toString().equals(((Map) httpResponse.extra).get("flow_limit"))) {
            String str = "";
            if (((Map) httpResponse.extra).containsKey("type")) {
                str = (String) ((Map) httpResponse.extra).get("type");
            }
            if (((Map) httpResponse.extra).containsKey("size")) {
                String str2 = (String) ((Map) httpResponse.extra).get("size");
                if (!TextUtils.isEmpty(str2)) {
                    i = Integer.parseInt(str2);
                    com.efs.sdk.base.a.e.b a2 = com.efs.sdk.base.a.e.b.a();
                    Message obtain = Message.obtain();
                    obtain.what = 0;
                    obtain.obj = str;
                    obtain.arg1 = i;
                    a2.sendMessage(obtain);
                }
            }
            i = 0;
            com.efs.sdk.base.a.e.b a22 = com.efs.sdk.base.a.e.b.a();
            Message obtain2 = Message.obtain();
            obtain2.what = 0;
            obtain2.obj = str;
            obtain2.arg1 = i;
            a22.sendMessage(obtain2);
        }
        b(httpResponse);
        f.a.a().c.b.incrementAndGet();
        c(httpResponse);
        a(httpResponse);
    }

    private static void b(HttpResponse httpResponse) {
        f.a.a().a(String.valueOf(httpResponse.getHttpCode()), httpResponse.getBizCode(), httpResponse.getReqUrl());
    }
}
