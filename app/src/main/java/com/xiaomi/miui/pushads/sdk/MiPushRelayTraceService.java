package com.xiaomi.miui.pushads.sdk;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.xiaomi.push.ch;
import com.xiaomi.push.ck;

/* loaded from: classes4.dex */
public class MiPushRelayTraceService extends Service {
    private static ck a;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        a = ck.a();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        super.onStartCommand(intent, i, i2);
        if (intent == null) {
            return 0;
        }
        Bundle extras = intent.getExtras();
        int i3 = extras.getInt("intenttype");
        long j = extras.getLong("id");
        int i4 = extras.getInt("showType");
        ch chVar = new ch();
        chVar.f18a = j;
        chVar.a = i4;
        chVar.f19a = "";
        if (a == null) {
            Log.e("MiPushRelayTraceService", "log sender is null!");
            return 0;
        }
        switch (i3) {
            case 1:
                l.a("deleteT:");
                a.b(chVar);
                break;
            case 2:
            case 3:
                l.a("clickT:");
                a.a(chVar);
                PendingIntent pendingIntent = (PendingIntent) intent.getParcelableExtra("pendingintent");
                if (pendingIntent != null) {
                    try {
                        pendingIntent.send();
                        break;
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                        break;
                    }
                }
                break;
        }
        int i5 = extras.getInt("notifyid", 0);
        if (i5 != 0) {
            l.a("action，remove noti");
            ((NotificationManager) getSystemService("notification")).cancel(i5);
            try {
                Object systemService = getSystemService((String) Context.class.getField("STATUS_BAR_SERVICE").get(null));
                systemService.getClass().getMethod("collapse", new Class[0]).invoke(systemService, new Object[0]);
                k.a("关闭status bar 成功");
            } catch (Exception unused) {
                Log.e("ads-notify-fd5dfce4", "Reflect failed");
            }
        }
        return 0;
    }
}
