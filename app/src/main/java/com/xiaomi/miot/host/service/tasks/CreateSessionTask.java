package com.xiaomi.miot.host.service.tasks;

import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.miot.host.runtime.HostRuntimeManager;
import com.xiaomi.miot.host.service.listener.ISessionListener;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.SessionListener;

/* loaded from: classes2.dex */
public class CreateSessionTask implements SessionListener, Runnable {
    private static final String TAG = "RegisterTask";
    private String config;
    private ISessionListener listener;
    private HostRuntimeManager runtime;

    public CreateSessionTask(HostRuntimeManager hostRuntimeManager, String str, ISessionListener iSessionListener) {
        Log.d(TAG, TAG);
        this.runtime = hostRuntimeManager;
        this.config = str;
        this.listener = iSessionListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.d(TAG, "run");
        try {
            this.runtime.createSession(this.config, this);
        } catch (MiotException e) {
            e.printStackTrace();
            onFailed(e.toMiotError());
        }
    }

    @Override // com.xiaomi.miot.typedef.listener.SessionListener
    public void onSucceed(String str, String str2, int i) {
        try {
            this.listener.onSucceed(str, str2, i);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaomi.miot.typedef.listener.SessionListener
    public void onFailed(MiotError miotError) {
        try {
            this.listener.onFailed(miotError);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
