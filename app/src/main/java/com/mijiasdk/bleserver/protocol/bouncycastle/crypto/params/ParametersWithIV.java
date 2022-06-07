package com.mijiasdk.bleserver.protocol.bouncycastle.crypto.params;

import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.CipherParameters;

/* loaded from: classes2.dex */
public class ParametersWithIV implements CipherParameters {
    private byte[] a;
    private CipherParameters b;

    public ParametersWithIV(CipherParameters cipherParameters, byte[] bArr) {
        this(cipherParameters, bArr, 0, bArr.length);
    }

    public ParametersWithIV(CipherParameters cipherParameters, byte[] bArr, int i, int i2) {
        this.a = new byte[i2];
        this.b = cipherParameters;
        System.arraycopy(bArr, i, this.a, 0, i2);
    }

    public byte[] getIV() {
        return this.a;
    }

    public CipherParameters getParameters() {
        return this.b;
    }
}
