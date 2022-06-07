package com.xiaomi.idm.api;

/* loaded from: classes3.dex */
public abstract class IIDMService {
    public abstract int enableEvent(int i, boolean z);

    public abstract String getName();

    public abstract String getType();

    public abstract String getUUID();

    protected abstract void notifyEvent(int i, byte[] bArr);

    public abstract void onAdvertisingResult(int i);

    public abstract void onServiceChanged(byte[] bArr);

    public abstract boolean onServiceConnectStatus(byte[] bArr);

    public abstract byte[] request(byte[] bArr);
}
