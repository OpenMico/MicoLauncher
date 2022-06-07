package com.xiaomi.onetrack.b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaomi.onetrack.util.p;

/* loaded from: classes4.dex */
final class c extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("android.intent.action.SCREEN_OFF") || action.equals("android.intent.action.SCREEN_ON")) {
            p.a("EventManager", "screen on/off");
            int i = action.equals("android.intent.action.SCREEN_ON") ? 0 : 2;
            p.a("EventManager", "screenReceiver will post prio=" + i);
            n.a().a(i, false);
        }
    }
}
