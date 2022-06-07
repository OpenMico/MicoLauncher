package com.xiaomi.push;

import java.io.IOException;

/* loaded from: classes4.dex */
public abstract class e {
    /* renamed from: a */
    public abstract int mo888a();

    public abstract e a(b bVar);

    public e a(byte[] bArr) {
        return a(bArr, 0, bArr.length);
    }

    public e a(byte[] bArr, int i, int i2) {
        try {
            b a = b.a(bArr, i, i2);
            a(a);
            a.m769a(0);
            return this;
        } catch (d e) {
            throw e;
        } catch (IOException unused) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).");
        }
    }

    public abstract void a(c cVar);

    /* renamed from: a */
    public void m830a(byte[] bArr, int i, int i2) {
        try {
            c a = c.a(bArr, i, i2);
            a(a);
            a.b();
        } catch (IOException unused) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).");
        }
    }

    protected boolean a(b bVar, int i) {
        return bVar.m771a(i);
    }

    /* renamed from: a */
    public byte[] m831a() {
        byte[] bArr = new byte[mo890b()];
        m830a(bArr, 0, bArr.length);
        return bArr;
    }

    /* renamed from: b */
    public abstract int mo890b();
}
