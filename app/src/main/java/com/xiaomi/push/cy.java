package com.xiaomi.push;

import android.content.Context;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class cy {
    private int a;

    public cy(int i) {
        this.a = i;
    }

    public int a() {
        return this.a;
    }

    public abstract String a(Context context, String str, List<as> list);

    /* renamed from: a  reason: collision with other method in class */
    public boolean m823a(Context context, String str, List<as> list) {
        return true;
    }
}
