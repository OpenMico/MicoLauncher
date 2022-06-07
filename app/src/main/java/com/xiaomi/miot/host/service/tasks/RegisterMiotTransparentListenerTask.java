package com.xiaomi.miot.host.service.tasks;

import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.miot.host.runtime.HostRuntimeManager;
import com.xiaomi.miot.host.service.listener.ITransparentListener;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.TransparentListener;
import java.util.List;

/* loaded from: classes2.dex */
public class RegisterMiotTransparentListenerTask implements Runnable {
    private static final String TAG = "RegisterTransparentTask";
    private ITransparentListener listener;
    private TransparentListener mTransparentListener = new TransparentListener() { // from class: com.xiaomi.miot.host.service.tasks.RegisterMiotTransparentListenerTask.1
        @Override // com.xiaomi.miot.typedef.listener.TransparentListener
        public void onMessage(String str) {
            try {
                RegisterMiotTransparentListenerTask.this.listener.onMessage(str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override // com.xiaomi.miot.typedef.listener.TransparentListener
        public void onFailed(MiotError miotError) {
            try {
                RegisterMiotTransparentListenerTask.this.listener.onFailed(miotError);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };
    private List<String> methods;
    private HostRuntimeManager runtime;

    public RegisterMiotTransparentListenerTask(HostRuntimeManager hostRuntimeManager, List<String> list, ITransparentListener iTransparentListener) {
        Log.d(TAG, "RegisterMiotTransparentListenerTask");
        this.runtime = hostRuntimeManager;
        this.listener = iTransparentListener;
        this.methods = list;
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
            this.runtime.registerMiotTransparentListener(this.methods, this.mTransparentListener);
        } catch (MiotException e) {
            e.printStackTrace();
        }
    }
}
