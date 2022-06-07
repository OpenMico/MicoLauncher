package com.xiaomi.mico.libyuvwrapper;

/* loaded from: classes3.dex */
public class YuvUtil {
    public static native void yuvI420ToNV21(byte[] bArr, byte[] bArr2, int i, int i2);

    static {
        System.loadLibrary("yuvutil");
    }
}
