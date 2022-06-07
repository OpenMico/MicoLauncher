package com.xiaomi.micolauncher.module.setting.bluetooth.lib;

import android.content.Context;
import android.util.Log;

/* loaded from: classes3.dex */
public class LocalBluetoothManager {
    private static LocalBluetoothManager a;
    private final Context b;
    private Context c;
    private final LocalBluetoothAdapter d;
    private final BluetoothEventManager e;

    /* loaded from: classes3.dex */
    public interface BluetoothManagerCallback {
        void onBluetoothManagerInitialized(Context context, LocalBluetoothManager localBluetoothManager);
    }

    public static synchronized LocalBluetoothManager getInstance(Context context, BluetoothManagerCallback bluetoothManagerCallback) {
        synchronized (LocalBluetoothManager.class) {
            if (a == null) {
                LocalBluetoothAdapter a2 = LocalBluetoothAdapter.a();
                if (a2 == null) {
                    return null;
                }
                Context applicationContext = context.getApplicationContext();
                a = new LocalBluetoothManager(a2, applicationContext);
                if (bluetoothManagerCallback != null) {
                    bluetoothManagerCallback.onBluetoothManagerInitialized(applicationContext, a);
                }
            }
            return a;
        }
    }

    private LocalBluetoothManager(LocalBluetoothAdapter localBluetoothAdapter, Context context) {
        this.b = context;
        this.d = localBluetoothAdapter;
        this.e = new BluetoothEventManager(this.d, null, context);
    }

    public LocalBluetoothAdapter getBluetoothAdapter() {
        return this.d;
    }

    public Context getContext() {
        return this.b;
    }

    public Context getForegroundActivity() {
        return this.c;
    }

    public boolean isForegroundActivity() {
        return this.c != null;
    }

    public synchronized void setForegroundActivity(Context context) {
        if (context != null) {
            Log.d("LocalBluetoothManager", "setting foreground activity to non-null context");
            this.c = context;
        } else if (this.c != null) {
            Log.d("LocalBluetoothManager", "setting foreground activity to null");
            this.c = null;
        }
    }

    public BluetoothEventManager getEventManager() {
        return this.e;
    }
}
