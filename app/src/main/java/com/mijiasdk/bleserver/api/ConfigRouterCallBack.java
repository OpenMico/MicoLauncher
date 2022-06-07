package com.mijiasdk.bleserver.api;

import com.mijiasdk.bleserver.protocol.BleComboWifiConfig;

/* loaded from: classes2.dex */
public interface ConfigRouterCallBack {
    void onBleConnected();

    void onBleDisconnected();

    void onReceiveWifiConfig(BleComboWifiConfig bleComboWifiConfig);
}
