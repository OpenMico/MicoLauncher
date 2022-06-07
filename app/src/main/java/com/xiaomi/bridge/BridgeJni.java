package com.xiaomi.bridge;

/* loaded from: classes3.dex */
public class BridgeJni {
    public static native byte[] decode(byte[] bArr);

    public static native byte[] encode(byte[] bArr);

    public static native void setKey(byte[] bArr);

    static {
        System.loadLibrary("DeviceRpcBridge");
    }
}
