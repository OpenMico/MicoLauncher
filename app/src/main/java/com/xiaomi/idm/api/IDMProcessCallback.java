package com.xiaomi.idm.api;

import com.xiaomi.mi_connect_sdk.api.MiAppCallback;

/* loaded from: classes3.dex */
public abstract class IDMProcessCallback implements MiAppCallback {
    @Override // com.xiaomi.mi_connect_sdk.api.MiAppCallback
    public final void onAdvertingResult(int i, int i2) {
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiAppCallback
    public final void onConnectionInitiated(int i, int i2, String str, byte[] bArr, byte[] bArr2) {
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiAppCallback
    public final void onConnectionResult(int i, int i2, String str, int i3) {
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiAppCallback
    public final void onDisconnection(int i, int i2) {
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiAppCallback
    public final void onDiscoveryResult(int i, int i2) {
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiAppCallback
    public final void onEndpointFound(int i, int i2, String str, byte[] bArr) {
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiAppCallback
    public final void onEndpointLost(int i, int i2, String str) {
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiAppCallback
    public final void onPayloadReceived(int i, int i2, byte[] bArr) {
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiAppCallback
    public final void onPayloadSentResult(int i, int i2, int i3) {
    }

    public abstract void onProcessConnected();

    public abstract void onProcessConnectionError();

    public abstract void onProcessDisconnected();

    @Override // com.xiaomi.mi_connect_sdk.api.MiAppCallback
    public final void onServiceBind() {
        onProcessConnected();
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiAppCallback
    public final void onServiceUnbind() {
        onProcessDisconnected();
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiAppCallback
    public final void onServiceError(int i) {
        onProcessConnectionError();
    }
}
