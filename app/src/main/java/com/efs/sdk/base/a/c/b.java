package com.efs.sdk.base.a.c;

import com.efs.sdk.base.a.a.a;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public final class b {
    public Map<String, Object> a = new ConcurrentHashMap();

    public final void a(String str, Object obj) {
        this.a.put(str, obj);
    }

    public final Object b(String str, Object obj) {
        Object obj2 = this.a.get(str);
        return (obj2 != null || this.a.containsKey(str)) ? obj2 : obj;
    }

    public final Map<String, Object> a() {
        HashMap hashMap = new HashMap(this.a);
        a.a();
        hashMap.put("ctime", Long.valueOf(a.b() / 1000));
        a.a();
        hashMap.put("w_tm", Long.valueOf(a.b() / 1000));
        return hashMap;
    }
}
