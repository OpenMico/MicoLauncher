package com.mijiasdk.bleserver.protocol.utils;

/* loaded from: classes2.dex */
public final class AuthenticatedCipherText {
    private final byte[] a;
    private final byte[] b;

    public AuthenticatedCipherText(byte[] bArr, byte[] bArr2) {
        if (bArr != null) {
            this.a = bArr;
            if (bArr2 != null) {
                this.b = bArr2;
                return;
            }
            throw new IllegalArgumentException("The authentication tag must not be null");
        }
        throw new IllegalArgumentException("The cipher text must not be null");
    }

    public byte[] getCipherText() {
        return this.a;
    }

    public byte[] getAuthenticationTag() {
        return this.b;
    }

    public byte[] getTotalData() {
        byte[] bArr = this.a;
        byte[] bArr2 = new byte[bArr.length + this.b.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        byte[] bArr3 = this.b;
        System.arraycopy(bArr3, 0, bArr2, this.a.length, bArr3.length);
        return bArr2;
    }
}
