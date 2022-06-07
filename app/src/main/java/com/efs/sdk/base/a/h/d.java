package com.efs.sdk.base.a.h;

import android.util.Log;
import com.efs.sdk.base.a.d.a;

/* loaded from: classes.dex */
public final class d {
    public static void a(String str, String str2) {
        if (a.a().f) {
            Log.i(str, str2);
        }
    }

    public static void a(String str, String str2, Throwable th) {
        if (!a.a().f) {
            return;
        }
        if (th == null) {
            Log.w(str, str2);
        } else {
            Log.w(str, str2, th);
        }
    }

    public static void b(String str, String str2, Throwable th) {
        if (!a.a().f) {
            return;
        }
        if (th == null) {
            Log.e(str, str2);
        } else {
            Log.e(str, str2, th);
        }
    }
}
