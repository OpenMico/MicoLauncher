package com.xiaomi.smarthome.ui.media;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.media.RelayMediaEvent;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.smarthome.core.entity.MediaType;

/* loaded from: classes4.dex */
public class TestDataReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        RelayMediaEvent relayMediaEvent;
        String stringExtra = intent.getStringExtra("did");
        int intValue = Integer.valueOf(intent.getStringExtra("status")).intValue();
        if (intValue == 0) {
            relayMediaEvent = new RelayMediaEvent(MediaType.RELAY.name(), "", "", null, "", stringExtra, intValue, null);
        } else {
            relayMediaEvent = new RelayMediaEvent(MediaType.RELAY.name(), stringExtra, stringExtra, null, stringExtra, stringExtra, intValue, null);
        }
        Logger logger = L.relay;
        logger.d("send:" + stringExtra + Constants.COLON_SEPARATOR + intValue);
        EventBusRegistry.getEventBus().post(relayMediaEvent);
    }
}
