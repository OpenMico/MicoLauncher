package com.xiaomi.miio.linux.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;

/* loaded from: classes3.dex */
public class LinuxMiioService extends Service {
    private LinuxMiioServiceImpl a;

    @Override // android.app.Service
    public void onCreate() {
        Log.d("LinuxMiioService", "onCreate");
        super.onCreate();
        this.a = new LinuxMiioServiceImpl();
        this.a.Initialize();
    }

    @Override // android.app.Service
    public void onDestroy() {
        Log.d("LinuxMiioService", "onDestroy");
        super.onDestroy();
        Process.killProcess(Process.myPid());
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Log.d("LinuxMiioService", "onBind");
        return this.a;
    }

    @Override // android.app.Service
    public void onRebind(Intent intent) {
        Log.d("LinuxMiioService", "onRebind");
        super.onRebind(intent);
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        Log.d("LinuxMiioService", "onUnbind");
        return super.onUnbind(intent);
    }
}
