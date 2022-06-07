package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes4.dex */
public class ec extends dy {
    private SharedPreferences b;

    public ec(Context context, int i) {
        super(context, i);
        this.b = context.getSharedPreferences("mipush_extra", 0);
    }

    @Override // com.xiaomi.push.dy, com.xiaomi.push.aj.a
    /* renamed from: a */
    public int mo834a() {
        return 9;
    }

    @Override // com.xiaomi.push.dy, com.xiaomi.push.aj.a
    /* renamed from: a  reason: collision with other method in class */
    public hj mo834a() {
        return hj.TopApp;
    }

    @Override // com.xiaomi.push.dy, com.xiaomi.push.aj.a
    /* renamed from: a */
    public String mo834a() {
        return null;
    }
}
