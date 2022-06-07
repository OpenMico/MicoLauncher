package com.xiaomi.mi_connect_sdk.api;

import android.content.Context;

/* loaded from: classes3.dex */
public class MiConnect {
    private static final String TAG = "MiConnect";

    public static MiApp newApp(Context context, MiAppCallback miAppCallback, int i) {
        return new DefaultMiApp(context, miAppCallback, i);
    }

    public static void delApp(MiApp miApp, int i) {
        ((InnerMiApp) miApp).destroy(i);
    }
}
