package com.xiaomi.micolauncher.overlay.client;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.mico.settingslib.core.MicoSettingsAction;
import com.xiaomi.micolauncher.overlay.Constants;
import com.xiaomi.micolauncher.overlay.ILauncherOverlay;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class LauncherClient {
    @NonNull
    private final Activity a;
    @NonNull
    private final LauncherClientCallback b;
    @NonNull
    private final String c;
    @NonNull
    private final b d;
    @NonNull
    private final c e;
    @Nullable
    private a f;
    private int g;
    private boolean j;
    @Nullable
    private WindowManager.LayoutParams l;
    @Nullable
    protected ILauncherOverlay mLauncherOverlay;
    private final BroadcastReceiver k = new BroadcastReceiver() { // from class: com.xiaomi.micolauncher.overlay.client.LauncherClient.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Uri data = intent.getData();
            if (data != null && TextUtils.equals(data.getSchemeSpecificPart(), LauncherClient.this.c)) {
                int i = LauncherClient.this.g;
                LauncherClient launcherClient = LauncherClient.this;
                launcherClient.g = LauncherClient.b(launcherClient.a, LauncherClient.this.c);
                Log.d("LauncherClient", "server updated form " + i + " to " + LauncherClient.this.g);
                if (LauncherClient.this.a()) {
                    LauncherClient.this.reconnect();
                }
            }
        }
    };
    private int h = 0;
    private int i = 0;

    public LauncherClient(@NonNull Activity activity, @NonNull String str, @NonNull LauncherClientCallback launcherClientCallback) {
        this.a = activity;
        this.c = str;
        this.b = launcherClientCallback;
        Context applicationContext = activity.getApplicationContext();
        this.d = new b(applicationContext, 65, str);
        this.e = c.a(applicationContext, str);
        this.mLauncherOverlay = this.e.a(this);
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme("package");
        intentFilter.addDataSchemeSpecificPart(str, 0);
        this.a.registerReceiver(this.k, intentFilter);
        this.g = b(activity, str);
        Log.d("LauncherClient", "load server version : " + this.g);
        connect();
    }

    public boolean a() {
        return (this.h & 1) != 0;
    }

    public final void connect() {
        if (!this.j) {
            if (a()) {
                this.d.a();
            }
            this.e.a();
        }
    }

    public final void reconnect() {
        if (this.mLauncherOverlay == null) {
            this.d.b();
            this.e.b();
            connect();
        }
    }

    public final void disconnect() {
        Log.d("LauncherClient", MicoSettingsAction.BLUETOOTH_DISCONNECT);
        a(true);
    }

    public void onStart() {
        if (!this.j) {
            Log.d("LauncherClient", "onStart");
            this.e.a(false);
            this.h |= 1;
            connect();
            ILauncherOverlay iLauncherOverlay = this.mLauncherOverlay;
            if (iLauncherOverlay != null && this.l != null) {
                try {
                    iLauncherOverlay.setActivityState(this.h);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    public void onResume() {
        if (!this.j) {
            Log.d("LauncherClient", "onResume");
            this.h |= 2;
            ILauncherOverlay iLauncherOverlay = this.mLauncherOverlay;
            if (iLauncherOverlay != null && this.l != null) {
                try {
                    iLauncherOverlay.setActivityState(this.h);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    public void onPause() {
        if (!this.j) {
            Log.d("LauncherClient", "onPause");
            this.h &= -3;
            ILauncherOverlay iLauncherOverlay = this.mLauncherOverlay;
            if (iLauncherOverlay != null && this.l != null) {
                try {
                    iLauncherOverlay.setActivityState(this.h);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    public void onStop() {
        if (!this.j) {
            Log.d("LauncherClient", "onStop");
            this.e.a(true);
            this.d.b();
            this.h &= -2;
            ILauncherOverlay iLauncherOverlay = this.mLauncherOverlay;
            if (iLauncherOverlay != null && this.l != null) {
                try {
                    iLauncherOverlay.setActivityState(this.h);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    public void onDestroy() {
        Log.d("LauncherClient", "onDestroy");
        a(!this.a.isChangingConfigurations());
    }

    private void a(boolean z) {
        Log.d("LauncherClient", "destroy:" + z);
        if (!this.j) {
            this.a.unregisterReceiver(this.k);
        }
        this.j = true;
        this.d.b();
        a aVar = this.f;
        if (aVar != null) {
            aVar.a();
            this.f = null;
        }
        this.e.a(this, z);
    }

    public void hideOverlay(boolean z) {
        Log.d("LauncherClient", "hideOverlay:" + z);
        ILauncherOverlay iLauncherOverlay = this.mLauncherOverlay;
        if (iLauncherOverlay != null) {
            try {
                iLauncherOverlay.hideOverlay(z ? 1 : 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void showOverlay(boolean z) {
        Log.d("LauncherClient", "showOverlay:" + z);
        ILauncherOverlay iLauncherOverlay = this.mLauncherOverlay;
        if (iLauncherOverlay != null) {
            try {
                iLauncherOverlay.showOverlay(z ? 1 : 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onAttachedToWindow() {
        onAttachedToWindow(this.a.getWindow().getAttributes());
    }

    public void onAttachedToWindow(WindowManager.LayoutParams layoutParams) {
        if (!this.j) {
            Log.d("LauncherClient", "onAttachedToWindow");
            a(layoutParams);
        }
    }

    public void onDetachedFormWindow() {
        if (!this.j) {
            Log.d("LauncherClient", "onDetachedFormWindow");
            a((WindowManager.LayoutParams) null);
        }
    }

    private void a(WindowManager.LayoutParams layoutParams) {
        if (this.l != layoutParams) {
            this.l = layoutParams;
            if (this.l != null) {
                b(layoutParams);
            } else {
                b();
            }
        }
    }

    private void b(@NonNull WindowManager.LayoutParams layoutParams) {
        Log.d("LauncherClient", "attachOverlayWindow");
        ILauncherOverlay iLauncherOverlay = this.mLauncherOverlay;
        if (iLauncherOverlay != null) {
            if (this.f == null) {
                this.f = new a(this);
            }
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.PARAMS_LAYOUT_PARAMS, layoutParams);
            bundle.putParcelable(Constants.PARAMS_CONFIGURATION, this.a.getResources().getConfiguration());
            try {
                iLauncherOverlay.windowAttached(bundle, this.f);
            } catch (RemoteException unused) {
            }
            try {
                iLauncherOverlay.setActivityState(this.h);
            } catch (RemoteException unused2) {
            }
        }
    }

    private void b() {
        Log.d("LauncherClient", "detachOverlayWindow");
        ILauncherOverlay iLauncherOverlay = this.mLauncherOverlay;
        if (iLauncherOverlay != null) {
            try {
                iLauncherOverlay.windowDetached(this.a.isChangingConfigurations());
            } catch (RemoteException unused) {
            }
        }
    }

    public static Intent getServerIntent(Context context, String str) {
        Uri.Builder scheme = new Uri.Builder().scheme("app");
        Uri build = scheme.encodedAuthority(context.getPackageName() + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + Process.myUid()).appendQueryParameter("sv", Integer.toString(2)).appendQueryParameter(Constants.SERVER_DATA_PARAM_CLIENT_VERSION, Integer.toString(2)).build();
        Intent intent = new Intent(Constants.SERVER_INTENT_ACTION);
        intent.setPackage(str);
        intent.setData(build);
        return intent;
    }

    public String getPackageName() {
        return this.c;
    }

    public void a(ILauncherOverlay iLauncherOverlay) {
        this.mLauncherOverlay = iLauncherOverlay;
        if (this.mLauncherOverlay == null) {
            a(0);
            return;
        }
        WindowManager.LayoutParams layoutParams = this.l;
        if (layoutParams != null) {
            b(layoutParams);
        }
    }

    public final void a(int i) {
        if (this.i != i) {
            Log.d("LauncherClient", "setOverlayState:" + i);
            this.i = i;
            boolean z = true;
            if ((i & 1) != 1) {
                z = false;
            }
            this.b.onOverlayStateChanged(z);
        }
    }

    public static int b(Context context, String str) {
        ResolveInfo resolveService = context.getPackageManager().resolveService(getServerIntent(context, str), 786560);
        if (resolveService == null || resolveService.serviceInfo == null || resolveService.serviceInfo.metaData == null) {
            return 1;
        }
        return resolveService.serviceInfo.metaData.getInt(Constants.SERVER_META_VERSION_KEY, 1);
    }

    public final void onOverlayTransitionStart(float f) {
        this.b.onOverlayTransitionStart(f);
    }

    public final void onOverlayTransitionChanged(float f) {
        this.b.onOverlayTransitionChanged(f);
    }

    public final void onOverlayTransitionEnd(float f) {
        this.b.onOverlayTransitionEnd(f);
    }

    public final void dump(@NonNull String str, @Nullable FileDescriptor fileDescriptor, @NonNull PrintWriter printWriter, @Nullable String[] strArr) {
        printWriter.print(str);
        printWriter.println("LauncherClient, packageName : " + this.c);
        String str2 = str + "  ";
        printWriter.print(str2);
        printWriter.println("BuildServerVersionCode: 2");
        printWriter.print(str2);
        printWriter.println("BuildServerVersionName: 2.0");
        printWriter.print(str2);
        printWriter.println("BuildServerVersionCode: 2");
        printWriter.print(str2);
        printWriter.println("BuildServerVersionName: 2.0");
        printWriter.print(str2);
        printWriter.println("CurrentServerVersion: " + this.g);
        printWriter.print(str2);
        printWriter.println("ActivityState: " + Integer.toHexString(this.h));
        printWriter.print(str2);
        printWriter.println("OverlayState: " + Integer.toHexString(this.i));
        printWriter.print(str2);
        StringBuilder sb = new StringBuilder();
        sb.append("isConnected: ");
        sb.append(this.mLauncherOverlay != null);
        printWriter.println(sb.toString());
        printWriter.print(str2);
        printWriter.println("isDestroyed: " + this.j);
    }

    public void onNewIntent(Intent intent) {
        if (this.g < 2) {
            Log.d("LauncherClient", "not support OnNewIntent interface");
            return;
        }
        ILauncherOverlay iLauncherOverlay = this.mLauncherOverlay;
        if (iLauncherOverlay != null) {
            try {
                iLauncherOverlay.onNewIntent(intent);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
