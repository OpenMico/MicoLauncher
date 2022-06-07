package com.xiaomi.miot.host.service.tasks;

import android.os.RemoteException;
import android.util.Log;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.xiaomi.miot.host.runtime.HostRuntimeManager;
import com.xiaomi.miot.host.service.listener.ICompletedListener;
import com.xiaomi.miot.typedef.exception.MiotException;

/* loaded from: classes2.dex */
public class ResetTask implements Runnable {
    private static final String TAG = "AddBindListenerTask";
    private ICompletedListener handler;
    private HostRuntimeManager runtime;

    public ResetTask(HostRuntimeManager hostRuntimeManager, ICompletedListener iCompletedListener) {
        Log.d(TAG, TAG);
        this.runtime = hostRuntimeManager;
        this.handler = iCompletedListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            reset();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void reset() throws RemoteException {
        try {
            this.runtime.reset();
            new Thread(new Runnable() { // from class: com.xiaomi.miot.host.service.tasks.ResetTask.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Thread.sleep(SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        ResetTask.this.handler.onSucceed(null);
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                    }
                }
            }).start();
        } catch (MiotException e) {
            e.printStackTrace();
            this.handler.onFailed(e.toMiotError());
        }
    }
}
