package com.efs.sdk.base.a.a;

import android.text.TextUtils;
import com.efs.sdk.base.BuildConfig;
import com.efs.sdk.base.a.d.a;
import com.efs.sdk.base.a.h.c.b;
import com.efs.sdk.base.a.h.f;
import com.umeng.analytics.pro.ai;
import com.xiaomi.ai.android.track.TraceConstants;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import com.xiaomi.smarthome.setting.ServerSetting;
import io.netty.handler.codec.rtsp.RtspHeaders;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/* loaded from: classes.dex */
public final class c {
    String a;
    String b;
    String c;
    public String d;
    public int e;
    public String f;
    public byte g;
    public String h;
    String i;
    String j;
    String k;
    public long l = 0;

    public static c a() {
        c cVar = new c();
        cVar.a = a.a().a;
        cVar.b = a.a().b;
        cVar.k = a.a().h;
        cVar.j = BuildConfig.VERSION_NAME;
        cVar.c = f.a(a.a().c);
        cVar.i = String.valueOf(com.efs.sdk.base.a.c.a.c.a().d.a);
        return cVar;
    }

    public final String b() {
        a.a();
        String valueOf = String.valueOf(a.b() / 1000);
        String a = b.a(com.efs.sdk.base.a.h.c.a.a(this.k + valueOf, this.b));
        TreeMap treeMap = new TreeMap();
        treeMap.put("app", this.a);
        treeMap.put("sd", a);
        if (!TextUtils.isEmpty(this.d)) {
            treeMap.put(TraceConstants.Result.CP, this.d);
        }
        if (this.g != 0) {
            treeMap.put(ServerSetting.SERVER_DE, String.valueOf(this.e));
            treeMap.put("type", this.h);
            String str = this.f;
            if (TextUtils.isEmpty(str)) {
                a.a();
                long b = a.b();
                str = String.format(Locale.SIMPLIFIED_CHINESE, "%d%04d", Long.valueOf(b), Integer.valueOf(new Random(b).nextInt(10000)));
            }
            treeMap.put(RtspHeaders.Values.SEQ, str);
        }
        treeMap.put("cver", this.i);
        treeMap.put(ai.x, "android");
        treeMap.put("sver", this.i);
        treeMap.put("tm", valueOf);
        treeMap.put("ver", this.c);
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (Map.Entry entry : treeMap.entrySet()) {
            String str2 = ((String) entry.getKey()) + "=" + ((String) entry.getValue());
            sb2.append(str2);
            sb.append(str2);
            sb.append(MusicGroupListActivity.SPECIAL_SYMBOL);
        }
        String a2 = b.a(sb2.toString() + this.b);
        sb.append("sign=");
        sb.append(a2);
        return b.b(sb.toString());
    }
}
