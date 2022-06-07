package com.xiaomi.push.service.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.ex;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.ap;
import com.xiaomi.push.service.ax;

/* loaded from: classes4.dex */
public class PingReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        b.c(intent.getPackage() + " is the package name");
        if (!ap.o.equals(intent.getAction())) {
            b.m149a("cancel the old ping timer");
            ex.a();
        } else if (TextUtils.equals(context.getPackageName(), intent.getPackage())) {
            b.c("Ping XMChannelService on timer");
            try {
                Intent intent2 = new Intent(context, XMPushService.class);
                intent2.putExtra("time_stamp", System.currentTimeMillis());
                intent2.setAction("com.xiaomi.push.timer");
                ax.a(context).a(intent2);
            } catch (Exception e) {
                b.a(e);
            }
        }
    }
}
