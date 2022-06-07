package com.xiaomi.miot.support.account;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import com.xiaomi.miot.support.account.IAccountService;
import com.xiaomi.miot.support.account.ICallback;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes2.dex */
public class AccountTask implements ServiceConnection {
    private static final ExecutorService THREAD_POOL_EXECUTOR = Executors.newCachedThreadPool();
    private final Context mContext;
    private Handler mHandler;
    private final AccountResponse mResponse;
    private IAccountService mService;
    private final String mSid;

    public AccountTask(Context context, String str, AccountResponse accountResponse) {
        this.mContext = context;
        this.mSid = str;
        this.mResponse = accountResponse;
    }

    public void start() {
        this.mHandler = new Handler(Looper.myLooper());
        Intent intent = new Intent("com.xiaomi.miot.support.ACCOUNT");
        intent.setPackage("com.xiaomi.micolauncher");
        if (!this.mContext.bindService(intent, this, 1)) {
            this.mHandler.post(new Runnable() { // from class: com.xiaomi.miot.support.account.AccountTask.1
                @Override // java.lang.Runnable
                public void run() {
                    AccountTask.this.mResponse.onFailure(1, "bind service failed");
                }
            });
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.mService = IAccountService.Stub.asInterface(iBinder);
        THREAD_POOL_EXECUTOR.execute(new Runnable() { // from class: com.xiaomi.miot.support.account.AccountTask.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    AccountTask.this.mService.getAuthToken(AccountTask.this.mSid, new ICallback.Stub() { // from class: com.xiaomi.miot.support.account.AccountTask.2.1
                        @Override // com.xiaomi.miot.support.account.ICallback
                        public void onResult(Bundle bundle) throws RemoteException {
                            AccountTask.this.parseResult(bundle);
                        }
                    });
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        this.mHandler.post(new Runnable() { // from class: com.xiaomi.miot.support.account.AccountTask.3
            @Override // java.lang.Runnable
            public void run() {
                AccountTask.this.mResponse.onFailure(2, "service disconnected");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseResult(final Bundle bundle) {
        bundle.setClassLoader(AccountInfo.class.getClassLoader());
        final int i = bundle.getInt("error_code");
        if (i != 0) {
            this.mHandler.post(new Runnable() { // from class: com.xiaomi.miot.support.account.AccountTask.4
                @Override // java.lang.Runnable
                public void run() {
                    AccountTask.this.mResponse.onFailure(i, bundle.getString("error_msg"));
                }
            });
        } else {
            final AccountInfo accountInfo = (AccountInfo) bundle.getParcelable("account_info");
            this.mHandler.post(new Runnable() { // from class: com.xiaomi.miot.support.account.AccountTask.5
                @Override // java.lang.Runnable
                public void run() {
                    AccountTask.this.mResponse.onSuccess(accountInfo);
                }
            });
        }
        this.mContext.unbindService(this);
    }
}
