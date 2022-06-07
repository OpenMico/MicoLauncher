package com.xiaomi.micolauncher.common;

import android.os.Handler;
import android.os.Message;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public class WeakRefHandler extends Handler {
    private WeakReference<Handler.Callback> a;

    public WeakRefHandler(Handler.Callback callback) {
        this.a = new WeakReference<>(callback);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        Handler.Callback callback;
        WeakReference<Handler.Callback> weakReference = this.a;
        if (weakReference != null && (callback = weakReference.get()) != null) {
            callback.handleMessage(message);
        }
    }
}
