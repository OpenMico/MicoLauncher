package com.xiaomi.smarthome.library.http.async;

import java.lang.ref.WeakReference;
import okhttp3.Call;

/* loaded from: classes4.dex */
public class HttpAsyncHandle {
    private final WeakReference<Call> a;

    public HttpAsyncHandle(Call call) {
        this.a = new WeakReference<>(call);
    }

    public void cancel() {
        Call call = this.a.get();
        if (call != null) {
            call.cancel();
        }
    }
}
