package com.xiaomi.ai.android.track;

import android.content.Context;
import com.xiaomi.ai.android.utils.a;
import com.xiaomi.ai.android.utils.d;
import com.xiaomi.ai.b.f;
import com.xiaomi.ai.log.Logger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes2.dex */
public class e {
    private Context a;
    private String b;
    private g d;
    private ConcurrentHashMap<String, byte[]> c = new ConcurrentHashMap<>();
    private boolean e = false;

    public e(Context context, String str, g gVar) {
        this.a = context;
        this.b = str;
        this.d = gVar;
    }

    private void c(String str) {
        if (d.a(this.a, this.b, str) != null) {
            Logger.a("LimitedDiskCache", "removeSpKey and add times: " + this.b + StringUtils.SPACE + str);
            d.b(this.a, this.b, str);
            this.d.b();
        }
    }

    private String d(String str) {
        return this.b + "_" + str;
    }

    public synchronized String a(String str) {
        long currentTimeMillis = System.currentTimeMillis();
        String a = a.a(this.c.get(d(str)), "utf-8");
        Logger.a("LimitedDiskCache", "readKeyValue Uncompress time: " + (System.currentTimeMillis() - currentTimeMillis));
        if (!f.a(a)) {
            return a;
        }
        return d.a(this.a, this.b, str);
    }

    public synchronized void a() {
        Logger.a("LimitedDiskCache", "removeAll " + this.b, this.e);
        this.c.clear();
        Map<String, ?> b = d.b(this.a, this.b);
        if (b == null || b.keySet().size() > 0) {
            this.d.b();
            d.a(this.a, this.b);
        }
    }

    public synchronized void a(String str, String str2) {
        if (this.d.c()) {
            long currentTimeMillis = System.currentTimeMillis();
            this.c.put(d(str), a.a(str2, "utf-8"));
            Logger.a("LimitedDiskCache", "addToCacheMap Compress :" + this.b + StringUtils.SPACE + str + " ,Compress time:" + (System.currentTimeMillis() - currentTimeMillis));
            c(str);
        } else {
            this.c.remove(d(str));
            this.d.b();
            Logger.a("LimitedDiskCache", "write SpKey and remove map: " + this.b + StringUtils.SPACE + str);
            d.a(this.a, this.b, str, str2);
        }
    }

    public synchronized void b(String str) {
        c(str);
        if (this.c.get(d(str)) != null) {
            Logger.a("LimitedDiskCache", "removeMap: " + this.b + StringUtils.SPACE + str);
            this.c.remove(d(str));
        }
    }
}
