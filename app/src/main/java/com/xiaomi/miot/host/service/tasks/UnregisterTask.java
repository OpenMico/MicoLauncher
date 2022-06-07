package com.xiaomi.miot.host.service.tasks;

import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.miot.host.runtime.HostRuntimeManager;
import com.xiaomi.miot.host.service.listener.ICompletedListener;
import com.xiaomi.miot.typedef.device.Device;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;

/* loaded from: classes2.dex */
public class UnregisterTask implements Runnable {
    private static final String TAG = "UnregisterTask";
    private Device device;
    private ICompletedListener listener;
    private HostRuntimeManager runtime;

    public UnregisterTask(HostRuntimeManager hostRuntimeManager, Device device, ICompletedListener iCompletedListener) {
        Log.d(TAG, TAG);
        this.runtime = hostRuntimeManager;
        this.device = device;
        this.listener = iCompletedListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.d(TAG, "run");
        try {
            this.runtime.unregister(this.device);
            onSucceed();
        } catch (MiotException e) {
            e.printStackTrace();
            onFailed(e.toMiotError());
        }
    }

    private void onSucceed() {
        try {
            this.listener.onSucceed(null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void onFailed(MiotError miotError) {
        try {
            this.listener.onFailed(miotError);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
