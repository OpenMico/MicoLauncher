package com.xiaomi.miot.host.service.tasks;

import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.miot.host.runtime.HostRuntimeManager;
import com.xiaomi.miot.host.service.handler.IOperationHandler;
import com.xiaomi.miot.host.service.listener.ICompletedListener;
import com.xiaomi.miot.typedef.device.ActionInfo;
import com.xiaomi.miot.typedef.device.Device;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.handler.OperationHandler;
import com.xiaomi.miot.typedef.listener.CompletedListener;
import com.xiaomi.miot.typedef.property.Property;

/* loaded from: classes2.dex */
public class RegisterTask implements OperationHandler, Runnable {
    private static final String TAG = "RegisterTask";
    private Device device;
    private IOperationHandler handler;
    private ICompletedListener listener;
    private HostRuntimeManager runtime;

    public RegisterTask(HostRuntimeManager hostRuntimeManager, Device device, ICompletedListener iCompletedListener, IOperationHandler iOperationHandler) {
        Log.d(TAG, TAG);
        this.runtime = hostRuntimeManager;
        this.device = device;
        this.listener = iCompletedListener;
        this.handler = iOperationHandler;
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.d(TAG, "run");
        try {
            this.runtime.register(this.device, new CompletedListener() { // from class: com.xiaomi.miot.host.service.tasks.RegisterTask.1
                @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                public void onSucceed(String str) {
                    RegisterTask.this.doSucceed(str);
                }

                @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                public void onFailed(MiotError miotError) {
                    RegisterTask.this.doFailed(miotError);
                }
            }, this);
        } catch (MiotException e) {
            e.printStackTrace();
            doFailed(e.toMiotError());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doSucceed(String str) {
        Log.d(TAG, "Register succeed!");
        try {
            this.listener.onSucceed(str);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doFailed(MiotError miotError) {
        Log.d(TAG, "Register failed!");
        try {
            this.listener.onFailed(miotError);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaomi.miot.typedef.handler.OperationHandler
    public MiotError onAction(ActionInfo actionInfo) {
        try {
            return this.handler.onAction(actionInfo);
        } catch (RemoteException e) {
            e.printStackTrace();
            return MiotError.IOT_TIMEOUT;
        }
    }

    @Override // com.xiaomi.miot.typedef.handler.OperationHandler
    public MiotError onGet(Property property) {
        try {
            return this.handler.onGet(property);
        } catch (RemoteException e) {
            e.printStackTrace();
            return MiotError.IOT_TIMEOUT;
        }
    }

    @Override // com.xiaomi.miot.typedef.handler.OperationHandler
    public MiotError onSet(Property property) {
        try {
            return this.handler.onSet(property);
        } catch (RemoteException e) {
            e.printStackTrace();
            return MiotError.IOT_TIMEOUT;
        }
    }
}
