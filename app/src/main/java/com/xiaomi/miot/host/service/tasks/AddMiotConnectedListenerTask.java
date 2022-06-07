package com.xiaomi.miot.host.service.tasks;

import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.miot.host.runtime.HostRuntimeManager;
import com.xiaomi.miot.host.service.listener.IMiotConnectedListener;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.MiotConnectedListener;

/* loaded from: classes2.dex */
public class AddMiotConnectedListenerTask implements Runnable {
    private static final String TAG = "AddMCListenerTask";
    private IMiotConnectedListener listener;
    private MiotConnectedListener mMiotConnectedListener = new MiotConnectedListener() { // from class: com.xiaomi.miot.host.service.tasks.AddMiotConnectedListenerTask.1
        @Override // com.xiaomi.miot.typedef.listener.MiotConnectedListener
        public void onConnected() {
            try {
                AddMiotConnectedListenerTask.this.listener.onConnected();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override // com.xiaomi.miot.typedef.listener.MiotConnectedListener
        public void onDisconnected() {
            try {
                AddMiotConnectedListenerTask.this.listener.onDisconnected();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override // com.xiaomi.miot.typedef.listener.MiotConnectedListener
        public void onTokenException() {
            try {
                AddMiotConnectedListenerTask.this.listener.onTokenException();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };
    private HostRuntimeManager runtime;

    public AddMiotConnectedListenerTask(HostRuntimeManager hostRuntimeManager, IMiotConnectedListener iMiotConnectedListener) {
        Log.d(TAG, "AddMiotConnectedListenerTask");
        this.runtime = hostRuntimeManager;
        this.listener = iMiotConnectedListener;
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
            this.runtime.registerMiotConnectedListener(this.mMiotConnectedListener);
        } catch (MiotException e) {
            e.printStackTrace();
        }
    }
}
