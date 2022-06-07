package com.mijiasdk.bleserver.api;

import android.content.Context;
import android.util.Log;
import com.mijiasdk.bleserver.service.BleServerImpl;

/* loaded from: classes2.dex */
public class BleServerManager {
    private static volatile BleServerManager b;
    private Context a;

    private BleServerManager(Context context) {
        this.a = context.getApplicationContext();
    }

    public static BleServerManager getInstance(Context context) {
        if (b == null) {
            synchronized (BleServerManager.class) {
                if (b == null) {
                    b = new BleServerManager(context);
                }
            }
        }
        return b;
    }

    public boolean init(String str, String str2, ConfigRouterCallBack configRouterCallBack) {
        Log.d("BleServerManager", "init");
        return BleServerImpl.getInstance().initBluetooth(this.a, str, str2, configRouterCallBack);
    }

    public void sendRouterConnectedResult(boolean z) {
        Log.d("BleServerManager", "sendRouterConnectedResult, isConnect = " + z);
        BleServerImpl.getInstance().sendRouterConnectedResult(z);
    }

    public void notifyPassportAuthSuccess() {
        Log.d("BleServerManager", "notifyPassportAuthSuccess");
        BleServerImpl.getInstance().notifyPassportAuthSuccess();
    }

    public void notifyOTServerConnected() {
        Log.d("BleServerManager", "notifyOTServerConnected");
        BleServerImpl.getInstance().notifyOTServerConnected();
    }

    public void deInit() {
        Log.d("BleServerManager", "deInit");
        BleServerImpl.getInstance().destroy();
    }

    public static String bytesToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder("");
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append(hexString);
        }
        return sb.toString();
    }
}
