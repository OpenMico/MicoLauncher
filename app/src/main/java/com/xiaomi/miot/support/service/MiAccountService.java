package com.xiaomi.miot.support.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.xiaomi.miot.support.MiotManager;
import com.xiaomi.miot.support.account.IAccountService;
import com.xiaomi.miot.support.account.ICallback;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes3.dex */
public class MiAccountService extends Service {
    private static final ExecutorService THREAD_POOL_EXECUTOR = Executors.newCachedThreadPool();
    private IBinder mBinder = new IAccountService.Stub() { // from class: com.xiaomi.miot.support.service.MiAccountService.1
        @Override // com.xiaomi.miot.support.account.IAccountService
        public void getAuthToken(final String str, final ICallback iCallback) throws RemoteException {
            MiAccountService.THREAD_POOL_EXECUTOR.execute(new Runnable() { // from class: com.xiaomi.miot.support.service.MiAccountService.1.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        iCallback.onResult(MiotManager.getAuthToken(str));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }
}
