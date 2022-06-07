package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Token.java */
/* loaded from: classes2.dex */
public abstract class d {
    static final d a = new b(null, 0, 0);
    private final d b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void a(BitArray bitArray, byte[] bArr);

    public d(d dVar) {
        this.b = dVar;
    }

    public final d a() {
        return this.b;
    }

    public final d a(int i, int i2) {
        return new b(this, i, i2);
    }

    public final d b(int i, int i2) {
        return new a(this, i, i2);
    }
}
