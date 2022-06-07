package com.efs.sdk.base.a.c.a;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.efs.sdk.base.a.d.a;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class b {
    public int a = -1;
    String b = "https://";
    String c = "errlog.umeng.com";
    private Boolean f = null;
    public Map<String, Double> d = new HashMap();
    public Map<String, String> e = new HashMap();

    private b() {
    }

    public static b a() {
        b bVar = new b();
        if (a.a().i) {
            bVar.c = "errlogos.umeng.com";
        } else {
            bVar.c = "errlog.umeng.com";
        }
        return bVar;
    }

    public final void a(@NonNull Map<String, String> map) {
        if (map.containsKey("gate_way")) {
            String str = map.get("gate_way");
            if (!TextUtils.isEmpty(str)) {
                this.c = str;
            }
        }
        if (map.containsKey("gate_way_https")) {
            String str2 = map.get("gate_way_https");
            if (!TextUtils.isEmpty(str2)) {
                this.b = Boolean.parseBoolean(str2) ? "https://" : "http://";
            }
        }
        HashMap hashMap = new HashMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key.startsWith("data_sampling_rate_") || key.startsWith("file_sampling_rate_")) {
                String replace = key.replace("data_sampling_rate_", "").replace("file_sampling_rate_", "");
                double d = 100.0d;
                try {
                    d = Double.parseDouble(entry.getValue());
                } catch (Throwable unused) {
                }
                hashMap.put(replace, Double.valueOf(d));
            }
        }
        this.d = hashMap;
        this.e = map;
    }
}
