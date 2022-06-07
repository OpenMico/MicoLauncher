package com.xiaomi.miot.host.service.tasks;

import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.miot.host.runtime.HostRuntimeManager;
import com.xiaomi.miot.host.service.listener.ISyncTimeListener;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.SyncTimeListener;

/* loaded from: classes2.dex */
public class SyncTimeTask implements SyncTimeListener, Runnable {
    private static final String TAG = "SyncTimeTask";
    private ISyncTimeListener listener;
    private HostRuntimeManager runtime;

    public SyncTimeTask(HostRuntimeManager hostRuntimeManager, ISyncTimeListener iSyncTimeListener) {
        Log.d(TAG, TAG);
        this.runtime = hostRuntimeManager;
        this.listener = iSyncTimeListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.d(TAG, "run");
        try {
            this.runtime.getSyncTime(this);
        } catch (MiotException e) {
            e.printStackTrace();
            onFailed(e.toMiotError());
        }
    }

    @Override // com.xiaomi.miot.typedef.listener.SyncTimeListener
    public void onSucceed(long j) {
        try {
            this.listener.onSucceed(j);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaomi.miot.typedef.listener.SyncTimeListener
    public void onFailed(MiotError miotError) {
        try {
            this.listener.onFailed(miotError);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
