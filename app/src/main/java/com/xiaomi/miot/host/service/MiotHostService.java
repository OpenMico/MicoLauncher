package com.xiaomi.miot.host.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;

/* loaded from: classes2.dex */
public class MiotHostService extends Service {
    private static final String TAG = "MiotHostService";
    private MiotHostServiceImpl mServiceImpl;

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate log:" + System.getProperty("test.th.log"));
        this.mServiceImpl = new MiotHostServiceImpl();
        this.mServiceImpl.initialize(getApplicationContext());
    }

    @Override // android.app.Service
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        this.mServiceImpl.destroy();
        super.onDestroy();
        Process.killProcess(Process.myPid());
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return this.mServiceImpl;
    }

    @Override // android.app.Service
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind");
        super.onRebind(intent);
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }
}
