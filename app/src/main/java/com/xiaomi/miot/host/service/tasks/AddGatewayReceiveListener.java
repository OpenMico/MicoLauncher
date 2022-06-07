package com.xiaomi.miot.host.service.tasks;

import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.miot.host.runtime.HostRuntimeManager;
import com.xiaomi.miot.host.service.listener.IGatewayMessageListener;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.GatewayMessageListener;

/* loaded from: classes2.dex */
public class AddGatewayReceiveListener implements Runnable {
    private static final String TAG = "AddGatewayListener";
    private IGatewayMessageListener listener;
    private GatewayMessageListener mGatewayMessageListener = new GatewayMessageListener() { // from class: com.xiaomi.miot.host.service.tasks.AddGatewayReceiveListener.1
        @Override // com.xiaomi.miot.typedef.listener.GatewayMessageListener
        public void onSucceed(String str) {
            if (AddGatewayReceiveListener.this.listener != null) {
                try {
                    AddGatewayReceiveListener.this.listener.onSucceed(str);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override // com.xiaomi.miot.typedef.listener.GatewayMessageListener
        public void onFailed(MiotError miotError) {
            if (AddGatewayReceiveListener.this.listener != null) {
                try {
                    AddGatewayReceiveListener.this.listener.onFailed(miotError);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private HostRuntimeManager runtime;

    public AddGatewayReceiveListener(HostRuntimeManager hostRuntimeManager, IGatewayMessageListener iGatewayMessageListener) {
        Log.d(TAG, "AddGatewayReceiveListener");
        this.runtime = hostRuntimeManager;
        this.listener = iGatewayMessageListener;
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
            this.runtime.registerGatewayReceiveListener(this.mGatewayMessageListener);
        } catch (MiotException e) {
            e.printStackTrace();
        }
    }
}
