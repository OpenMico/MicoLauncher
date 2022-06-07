package com.xiaomi.push.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.xiaomi.channel.commonutils.logger.b;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class an implements ServiceConnection {
    final /* synthetic */ XMPushService a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public an(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        int i;
        int i2;
        b.b("onServiceConnected " + iBinder);
        Service a = XMJobService.a();
        if (a != null) {
            XMPushService xMPushService = this.a;
            i = XMPushService.h;
            xMPushService.startForeground(i, XMPushService.a((Context) this.a));
            i2 = XMPushService.h;
            a.startForeground(i2, XMPushService.a((Context) this.a));
            a.stopForeground(true);
            this.a.unbindService(this);
            return;
        }
        b.m149a("XMService connected but innerService is null " + iBinder);
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
    }
}
