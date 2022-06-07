package com.xiaomi.push;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.xiaomi.push.am;

/* loaded from: classes4.dex */
class x extends Handler {
    final /* synthetic */ am a;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public x(am amVar, Looper looper) {
        super(looper);
        this.a = amVar;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        am.b bVar = (am.b) message.obj;
        if (message.what == 0) {
            bVar.a();
        } else if (message.what == 1) {
            bVar.c();
        }
        super.handleMessage(message);
    }
}
