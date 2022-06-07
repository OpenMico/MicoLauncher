package com.xiaomi.idm.api;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.xiaomi.idm.handoff_sdk.HandoffApi;
import com.xiaomi.mi_connect_sdk.api.MiAppCallback;
import com.xiaomi.mi_connect_sdk.api.ResultCode;
import com.xiaomi.mi_connect_sdk.util.LogUtil;
import com.xiaomi.mi_connect_service.IMiConnect;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public abstract class IDMBinderBase {
    private static final String TAG = "IDMBinderBase";
    private static IMiConnect sService = null;
    protected static int sServiceApiVersion = -1;
    private Context mContext;
    private volatile boolean mIsBound = false;
    protected MiAppCallback mMiAppCallback;
    protected IMiConnect mService;
    private static final Object sServiceLock = new Object();
    private static volatile boolean sPendingUnbind = false;
    private static volatile boolean sIsBinding = false;
    private static final Object sSetLock = new Object();
    private static final Set<IDMBinderBase> sMiConnectApiSet = Collections.newSetFromMap(new ConcurrentHashMap());
    private static ServiceConnection mConnection = new ServiceConnection() { // from class: com.xiaomi.idm.api.IDMBinderBase.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            int i;
            if (IDMBinderBase.sPendingUnbind) {
                IDMBinderBase.unbindAll();
                return;
            }
            synchronized (IDMBinderBase.sServiceLock) {
                IMiConnect unused = IDMBinderBase.sService = IMiConnect.Stub.asInterface(iBinder);
            }
            try {
                IDMBinderBase.sServiceApiVersion = IDMBinderBase.sService.getServiceApiVersion();
                LogUtil.d(IDMBinderBase.TAG, "get service api version: " + IDMBinderBase.sServiceApiVersion, new Object[0]);
                i = 0;
            } catch (RemoteException e) {
                LogUtil.e(IDMBinderBase.TAG, e.getMessage(), e);
                i = ResultCode.SERVICE_ERROR.getCode();
            }
            if (IDMBinderBase.sServiceApiVersion == -1) {
                i = ResultCode.PERMISSION_DENNY.getCode();
            }
            for (IDMBinderBase iDMBinderBase : IDMBinderBase.sMiConnectApiSet) {
                if (i != 0) {
                    iDMBinderBase.mMiAppCallback.onServiceError(i);
                } else if (iDMBinderBase.isVersionSatisfied()) {
                    iDMBinderBase.mService = IDMBinderBase.sService;
                    iDMBinderBase.onServiceConnected();
                    iDMBinderBase.mMiAppCallback.onServiceBind();
                    iDMBinderBase.mIsBound = true;
                } else {
                    IDMBinderBase.sMiConnectApiSet.remove(iDMBinderBase);
                    iDMBinderBase.mMiAppCallback.onServiceError(ResultCode.SERVICE_API_VERSION_TOO_LOW.getCode());
                }
            }
            boolean unused2 = IDMBinderBase.sIsBinding = false;
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtil.d(IDMBinderBase.TAG, "onServiceDisconnected", new Object[0]);
            IDMBinderBase.unbindAll();
        }
    };

    protected void doDestroy() {
    }

    protected abstract int getMinVersion();

    /* JADX INFO: Access modifiers changed from: protected */
    public void onServiceConnected() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public IDMBinderBase(Context context, MiAppCallback miAppCallback) {
        LogUtil.d(TAG, "Build Version: 2.0.156", new Object[0]);
        if (context != null) {
            this.mContext = context.getApplicationContext();
            this.mMiAppCallback = miAppCallback;
            return;
        }
        throw new IllegalArgumentException("context should not be null");
    }

    public void init() {
        bindService();
    }

    public void destroy() {
        doDestroy();
        unbindService();
    }

    protected void bindService() {
        synchronized (sSetLock) {
            sMiConnectApiSet.add(this);
        }
        LogUtil.d(TAG, "bindService: sIsBinding = " + sIsBinding + ", sIsPendingUnbind = " + sPendingUnbind, new Object[0]);
        if (serviceAvailableInner()) {
            if (isVersionSatisfied()) {
                this.mService = sService;
                onServiceConnected();
                this.mMiAppCallback.onServiceBind();
                this.mIsBound = true;
                return;
            }
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_API_VERSION_TOO_LOW.getCode());
        } else if (!sIsBinding) {
            sIsBinding = true;
            Intent intent = new Intent();
            intent.setAction("com.xiaomi.mi_connect_service.MiConnectService");
            intent.setComponent(new ComponentName(HandoffApi.MI_CONNECT_SERVICE_PKG, "com.xiaomi.mi_connect_service.MiConnectService"));
            try {
                this.mContext.startService(intent);
            } catch (SecurityException e) {
                LogUtil.e(TAG, e.getMessage(), e);
                this.mMiAppCallback.onServiceError(ResultCode.SERVICE_NOT_INIT_YET.getCode());
            }
            if (!this.mContext.bindService(intent, mConnection, 1)) {
                LogUtil.e(TAG, "bindServiceFailed", new Object[0]);
                this.mMiAppCallback.onServiceError(ResultCode.BIND_SERVICE_FAILED.getCode());
                sIsBinding = false;
            }
        }
    }

    protected boolean isVersionSatisfied() {
        boolean z = sServiceApiVersion >= getMinVersion();
        if (!z) {
            LogUtil.e(TAG, "Service Api version too low:\n   required min version: " + getMinVersion() + "\n   current version: " + sServiceApiVersion, new Object[0]);
        }
        return z;
    }

    protected void unbindService() {
        if (this.mIsBound) {
            LogUtil.d(TAG, "Do unbind service", new Object[0]);
            unbindService(this);
            this.mIsBound = false;
        }
    }

    private static void unbindService(IDMBinderBase iDMBinderBase) {
        synchronized (sSetLock) {
            sMiConnectApiSet.remove(iDMBinderBase);
            iDMBinderBase.mService = null;
            if (sMiConnectApiSet.isEmpty()) {
                if (serviceAvailableInner()) {
                    sPendingUnbind = true;
                    iDMBinderBase.mContext.unbindService(mConnection);
                }
                synchronized (sServiceLock) {
                    sService = null;
                }
                sIsBinding = false;
                sPendingUnbind = false;
                LogUtil.d(TAG, "all connection unbind, unbind the binder", new Object[0]);
            }
        }
        iDMBinderBase.mMiAppCallback.onServiceUnbind();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void unbindAll() {
        HashSet<IDMBinderBase> hashSet;
        sPendingUnbind = true;
        synchronized (sSetLock) {
            hashSet = new HashSet(sMiConnectApiSet);
            Context context = hashSet.iterator().hasNext() ? ((IDMBinderBase) hashSet.iterator().next()).mContext : null;
            sMiConnectApiSet.clear();
            if (serviceAvailableInner() && context != null) {
                context.unbindService(mConnection);
            }
            synchronized (sServiceLock) {
                sService = null;
            }
            sIsBinding = false;
            sPendingUnbind = false;
        }
        for (IDMBinderBase iDMBinderBase : hashSet) {
            iDMBinderBase.mMiAppCallback.onServiceUnbind();
        }
    }

    public boolean serviceAvailable() {
        return serviceAvailableInner() && this.mService != null;
    }

    private static boolean serviceAvailableInner() {
        boolean z;
        synchronized (sServiceLock) {
            IMiConnect iMiConnect = sService;
            z = iMiConnect != null && iMiConnect.asBinder().isBinderAlive();
        }
        return z;
    }
}
