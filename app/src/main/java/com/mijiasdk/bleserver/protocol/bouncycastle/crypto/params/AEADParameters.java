package com.mijiasdk.bleserver.protocol.bouncycastle.crypto.params;

import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.CipherParameters;

/* loaded from: classes2.dex */
public class AEADParameters implements CipherParameters {
    private byte[] a;
    private byte[] b;
    private KeyParameter c;
    private int d;

    public AEADParameters(KeyParameter keyParameter, int i, byte[] bArr) {
        this(keyParameter, i, bArr, null);
    }

    public AEADParameters(KeyParameter keyParameter, int i, byte[] bArr, byte[] bArr2) {
        this.c = keyParameter;
        this.b = bArr;
        this.d = i;
        this.a = bArr2;
    }

    public KeyParameter getKey() {
        return this.c;
    }

    public int getMacSize() {
        return this.d;
    }

    public byte[] getAssociatedText() {
        return this.a;
    }

    public byte[] getNonce() {
        return this.b;
    }
}
