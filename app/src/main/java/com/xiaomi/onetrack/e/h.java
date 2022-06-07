package com.xiaomi.onetrack.e;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaomi.onetrack.util.p;

/* loaded from: classes4.dex */
final class h extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("android.intent.action.SCREEN_OFF") || action.equals("android.intent.action.SCREEN_ON")) {
            p.a("OneTrackApp", action.equals("android.intent.action.SCREEN_ON") ? "屏幕亮" : "屏幕灭");
        }
    }
}
