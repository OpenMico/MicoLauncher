package com.xiaomi.miot.host.service.tasks;

import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.miot.host.runtime.HostRuntimeManager;
import com.xiaomi.miot.host.service.listener.ICompletedListener;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.CompletedListener;

/* loaded from: classes2.dex */
public class SyncDeviceNameTask implements Runnable {
    private static final String TAG = "SyncDeviceNameTask";
    private String deviceName;
    private ICompletedListener listener;
    private HostRuntimeManager runtime;

    public SyncDeviceNameTask(HostRuntimeManager hostRuntimeManager, String str, ICompletedListener iCompletedListener) {
        Log.d(TAG, TAG);
        this.runtime = hostRuntimeManager;
        this.deviceName = str;
        this.listener = iCompletedListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.d(TAG, "run");
        try {
            this.runtime.syncDeviceName(this.deviceName, new CompletedListener() { // from class: com.xiaomi.miot.host.service.tasks.SyncDeviceNameTask.1
                @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                public void onSucceed(String str) {
                    if (SyncDeviceNameTask.this.listener != null) {
                        try {
                            SyncDeviceNameTask.this.listener.onSucceed(str);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                public void onFailed(MiotError miotError) {
                    if (SyncDeviceNameTask.this.listener != null) {
                        try {
                            SyncDeviceNameTask.this.listener.onFailed(miotError);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (MiotException e) {
            e.printStackTrace();
        }
    }
}
