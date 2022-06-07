package com.efs.sdk.base.a.a;

import android.os.SystemClock;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.efs.sdk.base.a.h.d;
import com.efs.sdk.base.http.HttpResponse;
import com.xiaomi.miplay.mylibrary.DataModel;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class a {
    private static volatile long b = -1;
    public boolean a;

    /* synthetic */ a(byte b2) {
        this();
    }

    private a() {
        this.a = true;
    }

    /* renamed from: com.efs.sdk.base.a.a.a$a */
    /* loaded from: classes.dex */
    public static class C0048a {
        private static final a a = new a((byte) 0);
    }

    public static a a() {
        return C0048a.a;
    }

    public static long b() {
        if (b == -1) {
            return System.currentTimeMillis();
        }
        return SystemClock.elapsedRealtime() + b;
    }

    @NonNull
    public final HttpResponse a(String str, c cVar, File file, boolean z) {
        String b2 = cVar.b();
        String a = a(str, cVar);
        if (this.a) {
            d.a("efs.px.api", "Upload file, url is ".concat(String.valueOf(a)));
        }
        HashMap hashMap = new HashMap(1);
        hashMap.put("wpk-header", b2);
        com.efs.sdk.base.a.h.b.d a2 = new com.efs.sdk.base.a.h.b.d(a).a(hashMap);
        a2.a.d = file;
        com.efs.sdk.base.a.h.b.d a3 = a2.a("type", cVar.h);
        StringBuilder sb = new StringBuilder();
        sb.append(cVar.l);
        return a3.a("size", sb.toString()).a("flow_limit", Boolean.toString(z)).a(d.a()).a().b();
    }

    public static String a(@NonNull String str, @NonNull c cVar) {
        String str2;
        switch (cVar.g) {
            case 1:
                str2 = "/api/v1/raw/upload";
                break;
            case 2:
                str2 = "/perf_upload";
                break;
            case 3:
                str2 = "/api/v1/mix/upload";
                break;
            default:
                str2 = "/api/v1/raw/upload";
                break;
        }
        return str + str2;
    }

    public static void a(@Nullable HttpResponse httpResponse) {
        if (httpResponse != null && httpResponse.succ && !TextUtils.isEmpty(httpResponse.data)) {
            try {
                JSONObject jSONObject = new JSONObject(httpResponse.data);
                String optString = jSONObject.optString("code", DataModel.CIRCULATEFAIL_NO_SUPPORT);
                httpResponse.setBizCode(optString);
                if (!"0".equals(optString)) {
                    httpResponse.succ = false;
                }
                if (jSONObject.has("cver")) {
                    ((Map) httpResponse.extra).put("cver", jSONObject.getString("cver"));
                }
                long j = jSONObject.getLong("stm") * 1000;
                if (Math.abs(j - b()) > 1500000) {
                    b = j - SystemClock.elapsedRealtime();
                }
            } catch (Throwable th) {
                d.b("efs.px.api", "checkPxReturn error", th);
            }
        }
    }
}
