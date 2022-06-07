package com.xiaomi.smarthome.core.server.internal;

import java.lang.ref.WeakReference;
import okhttp3.Call;

/* loaded from: classes4.dex */
public class NetHandle {
    private final WeakReference<Call> a;

    public NetHandle(Call call) {
        this.a = new WeakReference<>(call);
    }

    public void cancel() {
        Call call = this.a.get();
        if (call != null) {
            call.cancel();
        }
    }
}
