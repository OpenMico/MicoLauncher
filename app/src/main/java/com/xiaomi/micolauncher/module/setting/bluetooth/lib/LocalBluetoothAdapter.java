package com.xiaomi.micolauncher.module.setting.bluetooth.lib;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import java.util.Set;

/* loaded from: classes3.dex */
public class LocalBluetoothAdapter {
    private static LocalBluetoothAdapter b;
    private final BluetoothAdapter a;
    private int c = Integer.MIN_VALUE;
    private long d;

    private LocalBluetoothAdapter(BluetoothAdapter bluetoothAdapter) {
        this.a = bluetoothAdapter;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static synchronized LocalBluetoothAdapter a() {
        LocalBluetoothAdapter localBluetoothAdapter;
        BluetoothAdapter defaultAdapter;
        synchronized (LocalBluetoothAdapter.class) {
            if (b == null && (defaultAdapter = BluetoothAdapter.getDefaultAdapter()) != null) {
                b = new LocalBluetoothAdapter(defaultAdapter);
            }
            localBluetoothAdapter = b;
        }
        return localBluetoothAdapter;
    }

    public void cancelDiscovery() {
        this.a.cancelDiscovery();
    }

    public boolean enable() {
        return this.a.enable();
    }

    public boolean disable() {
        return this.a.disable();
    }

    public String getAddress() {
        return this.a.getAddress();
    }

    public Set<BluetoothDevice> getBondedDevices() {
        return this.a.getBondedDevices();
    }

    public String getName() {
        return this.a.getName();
    }

    public int getScanMode() {
        return this.a.getScanMode();
    }

    public BluetoothLeScanner getBluetoothLeScanner() {
        return this.a.getBluetoothLeScanner();
    }

    public int getState() {
        return this.a.getState();
    }

    public boolean isDiscovering() {
        return this.a.isDiscovering();
    }

    public boolean isEnabled() {
        return this.a.isEnabled();
    }

    public void setName(String str) {
        this.a.setName(str);
    }

    public void startScanning(boolean z) {
        if (this.a.isDiscovering()) {
            return;
        }
        if ((z || this.d + 300000 <= System.currentTimeMillis()) && this.a.startDiscovery()) {
            this.d = System.currentTimeMillis();
        }
    }

    public void stopScanning() {
        if (this.a.isDiscovering()) {
            this.a.cancelDiscovery();
        }
    }

    public synchronized int getBluetoothState() {
        b();
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void a(int i) {
        this.c = i;
    }

    boolean b() {
        if (this.a.getState() == this.c) {
            return false;
        }
        a(this.a.getState());
        return true;
    }

    public boolean setBluetoothEnabled(boolean z) {
        boolean z2;
        if (z) {
            z2 = this.a.enable();
        } else {
            z2 = this.a.disable();
        }
        if (z2) {
            a(z ? 11 : 13);
        } else {
            b();
        }
        return z2;
    }

    public BluetoothDevice getRemoteDevice(String str) {
        return this.a.getRemoteDevice(str);
    }
}
