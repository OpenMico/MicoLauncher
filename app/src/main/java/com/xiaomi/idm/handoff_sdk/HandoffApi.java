package com.xiaomi.idm.handoff_sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.protobuf.ByteString;
import com.xiaomi.mi_connect_sdk.IHandoffProcess;
import com.xiaomi.mi_connect_sdk.IHandoffProcessCallback;
import com.xiaomi.mi_connect_sdk.util.LogUtil;
import com.xiaomi.mi_connect_service.proto.IPCParam;

/* loaded from: classes3.dex */
public class HandoffApi {
    public static final String HANDOFF_PROCESS_SERVICE = "com.xiaomi.idm.handoff_process_service.HandoffProcessService";
    public static final String MI_CONNECT_SERVICE_PKG = "com.xiaomi.mi_connect_service";
    private IHandoffProcess a;
    private final Context c;
    private volatile boolean b = false;
    private IHandoffProcessCallback d = new IHandoffProcessCallback.Stub() { // from class: com.xiaomi.idm.handoff_sdk.HandoffApi.1
    };
    private ServiceConnection e = new ServiceConnection() { // from class: com.xiaomi.idm.handoff_sdk.HandoffApi.2
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogUtil.d("HandoffApi", "onServiceConnected", new Object[0]);
            HandoffApi.this.a = IHandoffProcess.Stub.asInterface(iBinder);
            try {
                HandoffApi.this.a.registerClient(HandoffApi.this.d);
            } catch (RemoteException e) {
                LogUtil.e("HandoffApi", e.getMessage(), e);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtil.d("HandoffApi", "onServiceDisconnected", new Object[0]);
        }
    };

    public void init() {
        a();
    }

    public void destroy() {
        LogUtil.d("HandoffApi", "destroy", new Object[0]);
        if (!this.b) {
            LogUtil.d("HandoffApi", "Haven't init yet", new Object[0]);
            return;
        }
        try {
            if (c()) {
                this.a.destroy();
            }
        } catch (RemoteException e) {
            LogUtil.e("HandoffApi", e.getMessage(), e);
        }
        b();
    }

    private void a() {
        LogUtil.d("HandoffApi", "do bind handoff process service", new Object[0]);
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(MI_CONNECT_SERVICE_PKG, HANDOFF_PROCESS_SERVICE));
        if (!this.c.bindService(intent, this.e, 1)) {
            LogUtil.e("HandoffApi", "Bind handoff service process failed", new Object[0]);
        } else {
            this.b = true;
        }
    }

    private void b() {
        if (this.b) {
            LogUtil.d("HandoffApi", "unBindHandoffService", new Object[0]);
            this.c.unbindService(this.e);
            this.b = false;
            this.a = null;
        }
    }

    public HandoffApi(Context context) {
        this.c = context.getApplicationContext();
    }

    public void notifyHandoffData(String str, byte[] bArr) throws RemoteException {
        if (c()) {
            this.a.notifyHandoffEvent(IPCParam.IDMNotifyHandoffEvent.newBuilder().setKey(str).setValue(ByteString.copyFrom(bArr)).build().toByteArray());
        } else {
            LogUtil.e("HandoffApi", "notifyHandoffData Called, but service not available", new Object[0]);
        }
    }

    private boolean c() {
        IHandoffProcess iHandoffProcess = this.a;
        return iHandoffProcess != null && iHandoffProcess.asBinder().isBinderAlive();
    }
}
