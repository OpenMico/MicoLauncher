package com.xiaomi.micolauncher.common.services.deviceservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;

/* loaded from: classes3.dex */
public class MicoDeviceService extends Service {
    private MicoDeviceServiceBinder a = new MicoDeviceServiceBinder(MicoApplication.getApp().getApplicationContext());

    @Override // android.app.Service
    public void onCreate() {
        L.base.i("MicoDeviceService created");
    }

    @Override // android.app.Service
    public void onDestroy() {
        L.base.i("MicoDeviceService destroyed");
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.a;
    }
}
