package com.xiaomi.mi_connect_sdk.api;

/* loaded from: classes3.dex */
public interface MiAppCallback {
    void onAdvertingResult(int i, int i2);

    void onConnectionInitiated(int i, int i2, String str, byte[] bArr, byte[] bArr2);

    void onConnectionResult(int i, int i2, String str, int i3);

    void onDisconnection(int i, int i2);

    void onDiscoveryResult(int i, int i2);

    void onEndpointFound(int i, int i2, String str, byte[] bArr);

    void onEndpointLost(int i, int i2, String str);

    void onPayloadReceived(int i, int i2, byte[] bArr);

    void onPayloadSentResult(int i, int i2, int i3);

    void onServiceBind();

    void onServiceError(int i);

    void onServiceUnbind();
}
