package com.xiaomi.smarthome.library.crypto.rc4coder;

/* compiled from: RC4.java */
/* loaded from: classes4.dex */
final class a {
    private int a;
    private int b;
    private final byte[] c = new byte[256];

    public a(byte[] bArr) {
        int length = bArr.length;
        for (int i = 0; i < 256; i++) {
            this.c[i] = (byte) i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < 256; i3++) {
            int i4 = i2 + bArr[i3 % length];
            byte[] bArr2 = this.c;
            i2 = (i4 + bArr2[i3]) & 255;
            byte b = bArr2[i3];
            bArr2[i3] = bArr2[i2];
            bArr2[i2] = b;
        }
        this.a = 0;
        this.b = 0;
    }

    public byte a() {
        this.a = (this.a + 1) & 255;
        int i = this.b;
        byte[] bArr = this.c;
        int i2 = this.a;
        this.b = (i + bArr[i2]) & 255;
        byte b = bArr[i2];
        int i3 = this.b;
        bArr[i2] = bArr[i3];
        bArr[i3] = b;
        return bArr[(bArr[i2] + bArr[i3]) & 255];
    }

    public void a(byte[] bArr) {
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = (byte) (bArr[i] ^ a());
        }
    }
}
