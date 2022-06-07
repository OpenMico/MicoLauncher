package com.zlw.main.recorderlib.recorder.mp3;

/* loaded from: classes4.dex */
public class Mp3Encoder {
    public static native void close();

    public static native int encode(short[] sArr, short[] sArr2, int i, byte[] bArr);

    public static native int flush(byte[] bArr);

    public static native void init(int i, int i2, int i3, int i4, int i5);

    static {
        System.loadLibrary("mp3lame");
    }

    public static void init(int i, int i2, int i3, int i4) {
        init(i, i2, i3, i4, 7);
    }
}
