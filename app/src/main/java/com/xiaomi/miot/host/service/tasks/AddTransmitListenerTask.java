package com.xiaomi.miot.host.service.tasks;

import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.miot.host.runtime.HostRuntimeManager;
import com.xiaomi.miot.host.service.listener.IOnTransmitListener;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.OnTransmitListener;

/* loaded from: classes2.dex */
public class AddTransmitListenerTask implements Runnable {
    private static final String TAG = "AddTransmitListenerTask";
    private IOnTransmitListener listener;
    private OnTransmitListener mOnTransmitListener = new OnTransmitListener() { // from class: com.xiaomi.miot.host.service.tasks.AddTransmitListenerTask.1
        @Override // com.xiaomi.miot.typedef.listener.OnTransmitListener
        public void onTransmit(String str) {
            try {
                AddTransmitListenerTask.this.listener.onTransmit(str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };
    private HostRuntimeManager runtime;

    public AddTransmitListenerTask(HostRuntimeManager hostRuntimeManager, IOnTransmitListener iOnTransmitListener) {
        Log.d(TAG, TAG);
        this.runtime = hostRuntimeManager;
        this.listener = iOnTransmitListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.d(TAG, "run");
        try {
            register();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void register() {
        try {
            this.runtime.registerOnTransmitListener(this.mOnTransmitListener);
        } catch (MiotException e) {
            e.printStackTrace();
        }
    }
}
