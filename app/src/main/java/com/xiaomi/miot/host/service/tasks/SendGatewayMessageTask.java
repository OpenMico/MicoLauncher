package com.xiaomi.miot.host.service.tasks;

import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.miot.host.runtime.HostRuntimeManager;
import com.xiaomi.miot.host.service.listener.IGatewayMessageListener;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.GatewayMessageListener;

/* loaded from: classes2.dex */
public class SendGatewayMessageTask implements GatewayMessageListener, Runnable {
    private static final String TAG = "SendGatewayMessageTask";
    private IGatewayMessageListener listener;
    private String message;
    private HostRuntimeManager runtime;

    public SendGatewayMessageTask(HostRuntimeManager hostRuntimeManager, String str, IGatewayMessageListener iGatewayMessageListener) {
        Log.d(TAG, TAG);
        this.runtime = hostRuntimeManager;
        this.message = str;
        this.listener = iGatewayMessageListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.d(TAG, "run");
        try {
            this.runtime.sendGatewayMessage(this.message, this);
        } catch (MiotException e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaomi.miot.typedef.listener.GatewayMessageListener
    public void onSucceed(String str) {
        try {
            if (this.listener != null) {
                this.listener.onSucceed(str);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaomi.miot.typedef.listener.GatewayMessageListener
    public void onFailed(MiotError miotError) {
        try {
            if (this.listener != null) {
                this.listener.onFailed(miotError);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
