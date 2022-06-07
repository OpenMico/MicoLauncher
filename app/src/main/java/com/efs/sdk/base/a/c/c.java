package com.efs.sdk.base.a.c;

import android.content.Context;
import com.xiaomi.onetrack.OneTrack;

/* loaded from: classes.dex */
public final class c {
    public b a;
    public Context b;

    /* synthetic */ c(byte b) {
        this();
    }

    private c() {
        this.b = com.efs.sdk.base.a.d.a.a().c;
    }

    /* loaded from: classes.dex */
    public static class a {
        private static final c a = new c((byte) 0);

        public static /* synthetic */ c a() {
            return a;
        }
    }

    public final String a() {
        return this.a.b(OneTrack.Param.NET, "disconnected").toString();
    }
}
