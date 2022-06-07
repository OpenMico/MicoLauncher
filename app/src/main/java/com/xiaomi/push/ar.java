package com.xiaomi.push;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class ar {
    public int a;

    /* renamed from: a  reason: collision with other field name */
    public String f8a;

    /* renamed from: a  reason: collision with other field name */
    public Map<String, String> f9a = new HashMap();

    public String a() {
        return this.f8a;
    }

    public String toString() {
        return String.format("resCode = %1$d, headers = %2$s, response = %3$s", Integer.valueOf(this.a), this.f9a.toString(), this.f8a);
    }
}
