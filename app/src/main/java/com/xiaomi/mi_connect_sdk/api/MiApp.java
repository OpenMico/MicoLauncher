package com.xiaomi.mi_connect_sdk.api;

import com.xiaomi.mi_connect_service.IApStateCallback;
import com.xiaomi.mi_connect_service.IDeviceScannerCallback;
import com.xiaomi.mi_connect_service.ISoundBoxWhiteNameCallBack;

/* loaded from: classes3.dex */
public interface MiApp {
    void acceptConnection(ConnectionConfig connectionConfig);

    byte[] deviceInfoIDM();

    void disconnectFromEndPoint(ConnectionConfig connectionConfig);

    byte[] getIdHash();

    int registerSoundBoxWhiteName(ISoundBoxWhiteNameCallBack iSoundBoxWhiteNameCallBack);

    void rejectConnection(ConnectionConfig connectionConfig);

    void requestConnection(ConnectionConfig connectionConfig);

    void sendPayload(PayloadConfig payloadConfig);

    void startAdvertising(AppConfig appConfig);

    long startAp(String str, String str2, int i, boolean z, IApStateCallback iApStateCallback);

    void startDiscovery(AppConfig appConfig);

    int startScannerList(IDeviceScannerCallback iDeviceScannerCallback, String str);

    void stopAdvertising();

    void stopAp(long j);

    void stopDiscovery();
}
