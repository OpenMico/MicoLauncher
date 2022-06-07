package com.xiaomi.miot.host.service.tasks;

import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.miot.host.runtime.HostRuntimeManager;
import com.xiaomi.miot.host.service.listener.ICompletedListener;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.CompletedListener;

/* loaded from: classes2.dex */
public class GetLocalStatusTask implements CompletedListener, Runnable {
    private static final String TAG = "GetLocalStatusTask";
    private ICompletedListener listener;
    private HostRuntimeManager runtime;

    public GetLocalStatusTask(HostRuntimeManager hostRuntimeManager, ICompletedListener iCompletedListener) {
        Log.d(TAG, TAG);
        this.runtime = hostRuntimeManager;
        this.listener = iCompletedListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.d(TAG, "run");
        try {
            this.runtime.getLocalStatus(this);
        } catch (MiotException e) {
            e.printStackTrace();
            onFailed(e.toMiotError());
        }
    }

    @Override // com.xiaomi.miot.typedef.listener.CompletedListener
    public void onSucceed(String str) {
        try {
            this.listener.onSucceed(str);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaomi.miot.typedef.listener.CompletedListener
    public void onFailed(MiotError miotError) {
        try {
            this.listener.onFailed(miotError);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
