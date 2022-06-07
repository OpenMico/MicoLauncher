package com.xiaomi.miot.host.service.tasks;

import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.miot.host.runtime.HostRuntimeManager;
import com.xiaomi.miot.host.service.listener.ICheckBindListener;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.CheckBindListener;

/* loaded from: classes2.dex */
public class CheckBindTask implements CheckBindListener, Runnable {
    private static final String TAG = "CheckBindTask";
    private ICheckBindListener listener;
    private HostRuntimeManager runtime;

    public CheckBindTask(HostRuntimeManager hostRuntimeManager, ICheckBindListener iCheckBindListener) {
        Log.d(TAG, TAG);
        this.runtime = hostRuntimeManager;
        this.listener = iCheckBindListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.d(TAG, "run");
        try {
            this.runtime.checkBind(this);
        } catch (MiotException e) {
            e.printStackTrace();
            onFailed(e.toMiotError());
        }
    }

    @Override // com.xiaomi.miot.typedef.listener.CheckBindListener
    public void onSucceed(boolean z) {
        try {
            this.listener.onSucceed(z);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaomi.miot.typedef.listener.CheckBindListener
    public void onFailed(MiotError miotError) {
        try {
            this.listener.onFailed(miotError);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
