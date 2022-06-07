package com.google.android.exoplayer2.util;

import android.os.Trace;
import androidx.annotation.RequiresApi;

/* loaded from: classes2.dex */
public final class TraceUtil {
    private TraceUtil() {
    }

    public static void beginSection(String str) {
        if (Util.SDK_INT >= 18) {
            a(str);
        }
    }

    public static void endSection() {
        if (Util.SDK_INT >= 18) {
            a();
        }
    }

    @RequiresApi(18)
    private static void a(String str) {
        Trace.beginSection(str);
    }

    @RequiresApi(18)
    private static void a() {
        Trace.endSection();
    }
}
