package com.xiaomi.miio.linux.manager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.miio.linux.ILinuxMiioService;
import com.xiaomi.miio.linux.service.LinuxMiioService;
import com.xiaomi.miot.typedef.listener.CompletedListener;
import org.apache.commons.lang3.concurrent.AbstractCircuitBreaker;

/* loaded from: classes3.dex */
public class LinuxMiioManager {
    public static final int SERVICE_BIND_FAIL = -1;
    public static final int SERVICE_BIND_OK = 0;
    public static final int SERVICE_UNBIND_OK = 0;
    private static LinuxMiioManager b;
    private Context a;
    private a c;
    private ILinuxMiioService d;
    private CompletedListener e;
    private boolean f;

    public static LinuxMiioManager getInstance() {
        if (b == null) {
            synchronized (LinuxMiioManager.class) {
                if (b == null) {
                    b = new LinuxMiioManager();
                }
            }
        }
        return b;
    }

    private LinuxMiioManager() {
    }

    public void initialize(Context context) {
        this.a = context;
        this.c = new a(context);
    }

    public boolean isRunning() {
        return this.f;
    }

    public void bindLinuxMiioService(CompletedListener completedListener) {
        synchronized (LinuxMiioManager.class) {
            Log.d("LinuxMiioManager", AbstractCircuitBreaker.PROPERTY_NAME);
            this.e = completedListener;
            this.f = this.c.a();
            if (!this.f) {
                Log.e("LinuxMiioManager", "bind service failed");
            }
        }
    }

    public int start(String str) {
        ILinuxMiioService iLinuxMiioService = this.d;
        if (iLinuxMiioService != null) {
            try {
                return iLinuxMiioService.Start(str);
            } catch (RemoteException e) {
                e.printStackTrace();
                return -1;
            }
        } else {
            Log.e("LinuxMiioManager", "mLinuxMiioService is null!");
            return -1;
        }
    }

    public int stop() {
        this.f = false;
        this.c.b();
        return 0;
    }

    /* loaded from: classes3.dex */
    class a {
        private Context b;
        private ServiceConnection c = new ServiceConnection() { // from class: com.xiaomi.miio.linux.manager.LinuxMiioManager.a.1
            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                LinuxMiioManager.this.d = null;
                Log.d("LinuxMiioManager", "onServiceDisconnected: " + componentName);
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.d("LinuxMiioManager", "onServiceConnected: " + componentName);
                LinuxMiioManager.this.d = ILinuxMiioService.Stub.asInterface(iBinder);
                if (LinuxMiioManager.this.e != null) {
                    LinuxMiioManager.this.e.onSucceed("");
                }
            }
        };

        public a(Context context) {
            this.b = context;
        }

        public synchronized boolean a() {
            return this.b.getApplicationContext().bindService(new Intent(this.b.getApplicationContext(), LinuxMiioService.class), this.c, 1);
        }

        public synchronized void b() {
            this.b.getApplicationContext().unbindService(this.c);
        }
    }
}
