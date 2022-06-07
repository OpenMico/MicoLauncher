package com.xiaomi.micolauncher.module.multicp;

import android.os.Handler;
import android.os.Message;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.module.multicp.events.CollapseEvent;

/* loaded from: classes3.dex */
public class CountDownHandler extends Handler {
    public static final int MSG_COLLAPSE = 2;

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        super.handleMessage(message);
        if (message.what == 2) {
            EventBusRegistry.getEventBus().post(new CollapseEvent(true));
        }
    }
}
