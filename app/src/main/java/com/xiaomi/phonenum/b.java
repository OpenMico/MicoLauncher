package com.xiaomi.phonenum;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.xiaomi.phonenum.PhoneNumKeeper;
import com.xiaomi.phonenum.bean.Error;
import com.xiaomi.phonenum.bean.PhoneNum;
import com.xiaomi.phonenum.utils.Logger;
import com.xiaomi.phonenum.utils.LoggerManager;
import com.xiaomi.simactivate.service.IPhoneNumService;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MiuiPhoneNumServiceProxy.java */
/* loaded from: classes4.dex */
public class b {
    private Context b;
    private ServiceConnection c;
    private IPhoneNumService d;
    private Logger a = LoggerManager.getLogger();
    private boolean e = false;
    private boolean f = false;
    private String g = "";
    private CountDownLatch h = new CountDownLatch(1);

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(Context context) {
        this.b = context.getApplicationContext();
    }

    public void a(final PhoneNumKeeper.SetupFinishedListener setupFinishedListener) {
        if (this.e) {
            setupFinishedListener.onSetupFinished(Error.NONE);
            return;
        }
        Intent intent = new Intent(Constant.ACTION_BIND_SERVICE);
        intent.setPackage("com.xiaomi.simactivate.service");
        this.c = new ServiceConnection() { // from class: com.xiaomi.phonenum.b.1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                b.this.d = IPhoneNumService.Stub.asInterface(iBinder);
                b.this.e = true;
                b.this.h.countDown();
                setupFinishedListener.onSetupFinished(Error.NONE);
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                b.this.a.e("MiuiPhoneNumKeeper", "onServiceDisconnected");
                b.this.d = null;
            }
        };
        if (!this.b.bindService(intent, this.c, 1)) {
            setupFinishedListener.onSetupFinished(Error.UNKNOW);
        }
    }

    public PhoneNum a(int i, boolean z) throws IOException, RemoteException {
        c();
        return new PhoneNum.Builder().bundle(this.d.blockObtainPhoneNum(1, this.g, i, z)).build();
    }

    public boolean a(int i) throws RemoteException {
        c();
        return this.d.invalidatePhoneNum(1, this.g, i);
    }

    private void b() throws InterruptedException {
        this.h.await();
    }

    private void c() {
        try {
            b();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!this.e || this.f || this.d == null) {
            throw new IllegalStateException("MpHelper is not setup.");
        }
    }

    public void a() {
        Context context;
        this.h = new CountDownLatch(1);
        this.e = false;
        ServiceConnection serviceConnection = this.c;
        if (!(serviceConnection == null || this.d == null || (context = this.b) == null)) {
            context.unbindService(serviceConnection);
        }
        this.f = true;
        this.b = null;
        this.c = null;
        this.d = null;
    }
}
