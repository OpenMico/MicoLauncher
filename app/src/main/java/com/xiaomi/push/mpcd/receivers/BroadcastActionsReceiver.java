package com.xiaomi.push.mpcd.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaomi.push.ds;

/* loaded from: classes4.dex */
public class BroadcastActionsReceiver extends BroadcastReceiver {
    private ds a;

    public BroadcastActionsReceiver(ds dsVar) {
        this.a = dsVar;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        ds dsVar = this.a;
        if (dsVar != null) {
            dsVar.a(context, intent);
        }
    }
}
