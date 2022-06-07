package com.xiaomi.miot.host.service.tasks;

import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.miot.host.runtime.HostRuntimeManager;
import com.xiaomi.miot.host.service.listener.IBindKeyListener;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.BindKeyListener;

/* loaded from: classes2.dex */
public class GetBindKeyTask implements BindKeyListener, Runnable {
    private static final String TAG = "GetBindKeyTask";
    private IBindKeyListener listener;
    private HostRuntimeManager runtime;

    public GetBindKeyTask(HostRuntimeManager hostRuntimeManager, IBindKeyListener iBindKeyListener) {
        Log.d(TAG, TAG);
        this.runtime = hostRuntimeManager;
        this.listener = iBindKeyListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.d(TAG, "run");
        try {
            this.runtime.getBindKey(this);
        } catch (MiotException e) {
            e.printStackTrace();
            onFailed(e.toMiotError());
        }
    }

    @Override // com.xiaomi.miot.typedef.listener.BindKeyListener
    public void onSucceed(String str, int i) {
        try {
            this.listener.onSucceed(str, i);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaomi.miot.typedef.listener.BindKeyListener
    public void onFailed(MiotError miotError) {
        try {
            this.listener.onFailed(miotError);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
