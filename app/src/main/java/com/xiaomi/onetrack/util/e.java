package com.xiaomi.onetrack.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class e extends Handler {
    final /* synthetic */ d a;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public e(d dVar, Looper looper) {
        super(looper);
        this.a = dVar;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        if (message.what == 100) {
            this.a.b(message.getData().getString("hint"));
        }
    }
}
