package com.xiaomi.miot.host.service.tasks;

import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.miot.host.runtime.HostRuntimeManager;
import com.xiaomi.miot.host.service.listener.ICompletedListener;
import com.xiaomi.miot.host.service.listener.IOnBindListener;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.OnBindListener;

/* loaded from: classes2.dex */
public class AddBindListenerTask implements Runnable {
    private static final String TAG = "AddBindListenerTask";
    private ICompletedListener handler;
    private IOnBindListener listener;
    private OnBindListener mOnBindListener = new OnBindListener() { // from class: com.xiaomi.miot.host.service.tasks.AddBindListenerTask.1
        @Override // com.xiaomi.miot.typedef.listener.OnBindListener
        public void onBind() {
            Log.d(AddBindListenerTask.TAG, "onBind");
            try {
                AddBindListenerTask.this.listener.onBind();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override // com.xiaomi.miot.typedef.listener.OnBindListener
        public void onUnBind() {
            Log.d(AddBindListenerTask.TAG, "onUnBind");
            try {
                AddBindListenerTask.this.listener.onUnBind();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };
    private HostRuntimeManager runtime;

    public AddBindListenerTask(HostRuntimeManager hostRuntimeManager, IOnBindListener iOnBindListener, ICompletedListener iCompletedListener) {
        Log.d(TAG, TAG);
        this.runtime = hostRuntimeManager;
        this.listener = iOnBindListener;
        this.handler = iCompletedListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.d(TAG, "run");
        try {
            register();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void register() throws RemoteException {
        try {
            this.runtime.registerOnBindListener(this.mOnBindListener);
            this.handler.onSucceed(null);
        } catch (MiotException e) {
            this.handler.onFailed(e.toMiotError());
            e.printStackTrace();
        }
    }
}
