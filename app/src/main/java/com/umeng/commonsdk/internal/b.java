package com.umeng.commonsdk.internal;

import android.content.Context;

/* compiled from: UMInternalData.java */
/* loaded from: classes2.dex */
public class b {
    private static b b;
    private Context a;
    private c c;

    private b(Context context) {
        this.a = context;
        this.c = new c(context);
    }

    public static synchronized b a(Context context) {
        b bVar;
        synchronized (b.class) {
            if (b == null) {
                b = new b(context.getApplicationContext());
            }
            bVar = b;
        }
        return bVar;
    }

    public c a() {
        return this.c;
    }
}
