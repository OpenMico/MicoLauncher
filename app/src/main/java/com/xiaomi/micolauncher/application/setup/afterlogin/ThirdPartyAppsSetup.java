package com.xiaomi.micolauncher.application.setup.afterlogin;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.xiaomi.micolauncher.application.setup.ISetupRule;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public class ThirdPartyAppsSetup implements ISetupRule {
    private AtomicBoolean a;
    private AtomicBoolean b;
    private WeakReference<Context> c;
    private final Object d;
    private ServiceConnection e;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class a {
        static final ThirdPartyAppsSetup a = new ThirdPartyAppsSetup();
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public boolean mustBeSync(Context context) {
        return false;
    }

    private ThirdPartyAppsSetup() {
        this.a = new AtomicBoolean(false);
        this.b = new AtomicBoolean(false);
        this.d = new Object();
        this.e = new ServiceConnection() { // from class: com.xiaomi.micolauncher.application.setup.afterlogin.ThirdPartyAppsSetup.1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                L.video.i("long video RPC setup OK");
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                L.video.i("long video RPC disconnected %s", componentName);
            }

            @Override // android.content.ServiceConnection
            public void onBindingDied(ComponentName componentName) {
                L.video.w("long video RPC died %s", componentName);
                ThirdPartyAppsSetup.this.a.set(false);
            }
        };
    }

    public static ThirdPartyAppsSetup getInstance() {
        return a.a;
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        this.c = new WeakReference<>(context);
        a(context);
    }

    private void a(Context context) {
        if (!this.a.get() && !this.b.get()) {
            this.b.set(true);
            ThirdPartyAppProxy.getInstance().initialize(context);
            Intent intent = new Intent("android.intent.action.MI_SOUND_BOX_COMMAND_SERVICE");
            intent.setPackage(context.getPackageName());
            boolean bindService = context.bindService(intent, this.e, 1);
            this.a.set(bindService);
            this.b.set(false);
            L.video.i("ThirdPartyAppsSetup bind result %s", Boolean.valueOf(bindService));
        }
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void onDestroy() {
        synchronized (this.d) {
            if (!(this.c == null || this.c.get() == null)) {
                this.c.get().unbindService(this.e);
            }
        }
    }
}
