package com.efs.sdk.base.a.i;

import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public abstract class a extends Handler {
    public com.efs.sdk.base.a.d.a a;

    abstract void a();

    /* JADX INFO: Access modifiers changed from: package-private */
    public a() {
        super(com.efs.sdk.base.a.h.a.a.a.getLooper());
        sendEmptyMessageDelayed(0, 60000L);
    }

    @Override // android.os.Handler
    public void handleMessage(@NonNull Message message) {
        super.handleMessage(message);
        a();
        sendEmptyMessageDelayed(0, 60000L);
    }
}
