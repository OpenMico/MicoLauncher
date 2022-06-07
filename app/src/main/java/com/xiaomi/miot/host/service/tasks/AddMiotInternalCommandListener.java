package com.xiaomi.miot.host.service.tasks;

import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.miot.host.runtime.HostRuntimeManager;
import com.xiaomi.miot.host.service.listener.IMiotInternalCommandListener;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.MiotInternalCommandListener;

/* loaded from: classes2.dex */
public class AddMiotInternalCommandListener implements Runnable {
    private static final String TAG = "AddMiotInternalCommand";
    private IMiotInternalCommandListener listener;
    private MiotInternalCommandListener mMiotInternalCommandListener = new MiotInternalCommandListener() { // from class: com.xiaomi.miot.host.service.tasks.AddMiotInternalCommandListener.1
        @Override // com.xiaomi.miot.typedef.listener.MiotInternalCommandListener
        public void clearUserData() {
            if (AddMiotInternalCommandListener.this.listener != null) {
                try {
                    AddMiotInternalCommandListener.this.listener.clearUserData();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private HostRuntimeManager runtime;

    public AddMiotInternalCommandListener(HostRuntimeManager hostRuntimeManager, IMiotInternalCommandListener iMiotInternalCommandListener) {
        Log.d(TAG, "AddMiotInternalCommandListener");
        this.runtime = hostRuntimeManager;
        this.listener = iMiotInternalCommandListener;
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
            this.runtime.registerMiotInternalCommandListener(this.mMiotInternalCommandListener);
        } catch (MiotException e) {
            e.printStackTrace();
        }
    }
}
