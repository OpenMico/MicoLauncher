package com.xiaomi.smarthome.devicelibrary.bluetooth.utils;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import com.xiaomi.smarthome.devicelibrary.bluetooth.BluetoothContextManager;

/* loaded from: classes4.dex */
public class BluetoothUtils extends BluetoothContextManager {
    public static void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        a(broadcastReceiver, intentFilter);
    }

    public static void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        try {
            a(broadcastReceiver);
        } catch (Throwable th) {
            BluetoothLog.e(th);
        }
    }

    public static void sendBroadcast(Intent intent) {
        a(intent);
    }

    public static void sendBroadcast(String str) {
        a(new Intent(str));
    }

    public static void sendBroadcastDelayed(final String str, long j) {
        postDelayed(new Runnable() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.utils.BluetoothUtils.1
            @Override // java.lang.Runnable
            public void run() {
                BluetoothUtils.sendBroadcast(str);
            }
        }, j);
    }

    private static void a(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        getContext().registerReceiver(broadcastReceiver, intentFilter);
    }

    private static void a(BroadcastReceiver broadcastReceiver) {
        getContext().unregisterReceiver(broadcastReceiver);
    }

    private static void a(Intent intent) {
        getContext().sendBroadcast(intent);
    }
}
