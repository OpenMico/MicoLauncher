package com.xiaomi.micolauncher.module.setting.bluetooth.lib;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import kotlin.jvm.internal.ShortCompanionObject;

/* loaded from: classes3.dex */
public class BluetoothEventManager {
    private final LocalBluetoothAdapter a;
    private Context e;
    private Handler g;
    private final Collection<BluetoothCallback> f = new ArrayList();
    private final BroadcastReceiver h = new BroadcastReceiver() { // from class: com.xiaomi.micolauncher.module.setting.bluetooth.lib.BluetoothEventManager.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            d dVar = (d) BluetoothEventManager.this.d.get(action);
            if (dVar != null) {
                dVar.a(context, intent, bluetoothDevice);
            }
        }
    };
    private final IntentFilter b = new IntentFilter();
    private final IntentFilter c = new IntentFilter();
    private final Map<String, d> d = new HashMap();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public interface d {
        void a(Context context, Intent intent, BluetoothDevice bluetoothDevice);
    }

    private void a(String str, d dVar) {
        this.d.put(str, dVar);
        this.b.addAction(str);
    }

    public BluetoothEventManager(LocalBluetoothAdapter localBluetoothAdapter, Object obj, Context context) {
        this.a = localBluetoothAdapter;
        this.e = context;
        a("android.bluetooth.adapter.action.STATE_CHANGED", new a());
        a("android.bluetooth.adapter.action.DISCOVERY_STARTED", new e(true));
        a("android.bluetooth.adapter.action.DISCOVERY_FINISHED", new e(false));
        a("android.bluetooth.device.action.FOUND", new c());
        a("android.bluetooth.device.action.BOND_STATE_CHANGED", new b());
        this.e.registerReceiver(this.h, this.b, null, this.g);
    }

    void a() {
        this.e.registerReceiver(this.h, this.c, null, this.g);
    }

    public void setReceiverHandler(Handler handler) {
        this.e.unregisterReceiver(this.h);
        this.g = handler;
        this.e.registerReceiver(this.h, this.b, null, this.g);
        a();
    }

    public void registerCallback(BluetoothCallback bluetoothCallback) {
        synchronized (this.f) {
            this.f.add(bluetoothCallback);
        }
    }

    public void unregisterCallback(BluetoothCallback bluetoothCallback) {
        synchronized (this.f) {
            this.f.remove(bluetoothCallback);
        }
    }

    /* loaded from: classes3.dex */
    private class a implements d {
        private a() {
        }

        @Override // com.xiaomi.micolauncher.module.setting.bluetooth.lib.BluetoothEventManager.d
        public void a(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
            BluetoothEventManager.this.a.a(intExtra);
            synchronized (BluetoothEventManager.this.f) {
                for (BluetoothCallback bluetoothCallback : BluetoothEventManager.this.f) {
                    bluetoothCallback.onBluetoothStateChanged(intExtra);
                }
            }
        }
    }

    /* loaded from: classes3.dex */
    private class e implements d {
        private final boolean b;

        e(boolean z) {
            this.b = z;
        }

        @Override // com.xiaomi.micolauncher.module.setting.bluetooth.lib.BluetoothEventManager.d
        public void a(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            synchronized (BluetoothEventManager.this.f) {
                for (BluetoothCallback bluetoothCallback : BluetoothEventManager.this.f) {
                    bluetoothCallback.onScanningStateChanged(this.b);
                }
            }
        }
    }

    /* loaded from: classes3.dex */
    private class c implements d {
        private c() {
        }

        @Override // com.xiaomi.micolauncher.module.setting.bluetooth.lib.BluetoothEventManager.d
        public void a(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            intent.getShortExtra("android.bluetooth.device.extra.RSSI", ShortCompanionObject.MIN_VALUE);
            BluetoothClass bluetoothClass = (BluetoothClass) intent.getParcelableExtra("android.bluetooth.device.extra.CLASS");
            intent.getStringExtra("android.bluetooth.device.extra.NAME");
            synchronized (BluetoothEventManager.this.f) {
                for (BluetoothCallback bluetoothCallback : BluetoothEventManager.this.f) {
                    bluetoothCallback.onScanedBluetoothDevice(bluetoothDevice);
                }
            }
        }
    }

    /* loaded from: classes3.dex */
    private class b implements d {
        private b() {
        }

        @Override // com.xiaomi.micolauncher.module.setting.bluetooth.lib.BluetoothEventManager.d
        public void a(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            if (bluetoothDevice == null) {
                Log.e("BluetoothEventManager", "ACTION_BOND_STATE_CHANGED with no EXTRA_DEVICE");
                return;
            }
            int intExtra = intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", Integer.MIN_VALUE);
            synchronized (BluetoothEventManager.this.f) {
                for (BluetoothCallback bluetoothCallback : BluetoothEventManager.this.f) {
                    bluetoothCallback.onDeviceBondStateChanged(bluetoothDevice, intExtra);
                }
            }
        }
    }
}
