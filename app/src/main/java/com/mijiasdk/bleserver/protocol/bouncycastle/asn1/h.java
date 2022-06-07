package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import java.io.InputStream;

/* compiled from: LimitedInputStream.java */
/* loaded from: classes2.dex */
public abstract class h extends InputStream {
    protected final InputStream a;
    private int b;

    public h(InputStream inputStream, int i) {
        this.a = inputStream;
        this.b = i;
    }

    public int a() {
        return this.b;
    }

    protected void b(boolean z) {
        InputStream inputStream = this.a;
        if (inputStream instanceof e) {
            ((e) inputStream).a(z);
        }
    }
}
