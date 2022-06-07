package com.xiaomi.push.service;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/* loaded from: classes4.dex */
class ao extends Handler {
    final /* synthetic */ XMPushService a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ao(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        String str;
        super.handleMessage(message);
        if (message != null) {
            try {
                switch (message.what) {
                    case 17:
                        if (message.obj != null) {
                            this.a.onStart((Intent) message.obj, XMPushService.a);
                            break;
                        }
                        break;
                    case 18:
                        Message obtain = Message.obtain((Handler) null, 0);
                        obtain.what = 18;
                        Bundle bundle = new Bundle();
                        str = this.a.e;
                        bundle.putString("xmsf_region", str);
                        obtain.setData(bundle);
                        message.replyTo.send(obtain);
                        break;
                }
            } catch (Throwable unused) {
            }
        }
    }
}
