package com.efs.sdk.base.a.h.a;

import android.os.HandlerThread;

/* loaded from: classes.dex */
public final class a {
    public static HandlerThread a;

    static {
        HandlerThread handlerThread = new HandlerThread("efs-base", 10);
        a = handlerThread;
        handlerThread.start();
    }
}
