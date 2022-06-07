package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.be;
import com.xiaomi.push.service.bf;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class hf {
    private static hf a;
    private final Context b;
    private Map<String, hg> c = new HashMap();

    private hf(Context context) {
        this.b = context;
    }

    public static hf a(Context context) {
        if (context == null) {
            b.d("[TinyDataManager]:mContext is null, TinyDataManager.getInstance(Context) failed.");
            return null;
        }
        if (a == null) {
            synchronized (hf.class) {
                if (a == null) {
                    a = new hf(context);
                }
            }
        }
        return a;
    }

    private boolean a(String str, String str2, String str3, String str4, long j, String str5) {
        hl hlVar = new hl();
        hlVar.d(str3);
        hlVar.c(str4);
        hlVar.a(j);
        hlVar.b(str5);
        hlVar.a(true);
        hlVar.a("push_sdk_channel");
        hlVar.e(str2);
        b.m149a("TinyData TinyDataManager.upload item:" + hlVar.d() + "   ts:" + System.currentTimeMillis());
        return a(hlVar, str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public hg a() {
        hg hgVar = this.c.get("UPLOADER_PUSH_CHANNEL");
        if (hgVar != null) {
            return hgVar;
        }
        hg hgVar2 = this.c.get("UPLOADER_HTTP");
        if (hgVar2 != null) {
            return hgVar2;
        }
        return null;
    }

    public void a(hg hgVar, String str) {
        if (hgVar == null) {
            b.d("[TinyDataManager]: please do not add null mUploader to TinyDataManager.");
        } else if (TextUtils.isEmpty(str)) {
            b.d("[TinyDataManager]: can not add a provider from unkown resource.");
        } else {
            b().put(str, hgVar);
        }
    }

    public boolean a(hl hlVar, String str) {
        if (TextUtils.isEmpty(str)) {
            b.m149a("pkgName is null or empty, upload ClientUploadDataItem failed.");
            return false;
        } else if (be.a(hlVar, false)) {
            return false;
        } else {
            if (TextUtils.isEmpty(hlVar.d())) {
                hlVar.f(be.a());
            }
            hlVar.g(str);
            bf.a(this.b, hlVar);
            return true;
        }
    }

    public boolean a(String str, String str2, long j, String str3) {
        return a(this.b.getPackageName(), this.b.getPackageName(), str, str2, j, str3);
    }

    Map<String, hg> b() {
        return this.c;
    }
}
