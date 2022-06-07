package com.xiaomi.push;

/* loaded from: classes4.dex */
public final class jm extends jn {
    private byte[] a;
    private int b;
    private int c;

    @Override // com.xiaomi.push.jn
    /* renamed from: a */
    public int mo1110a() {
        return this.b;
    }

    @Override // com.xiaomi.push.jn
    /* renamed from: a */
    public int mo1109a(byte[] bArr, int i, int i2) {
        int b = b();
        if (i2 > b) {
            i2 = b;
        }
        if (i2 > 0) {
            System.arraycopy(this.a, this.b, bArr, i, i2);
            a(i2);
        }
        return i2;
    }

    @Override // com.xiaomi.push.jn
    public void a(int i) {
        this.b += i;
    }

    public void a(byte[] bArr) {
        b(bArr, 0, bArr.length);
    }

    @Override // com.xiaomi.push.jn
    /* renamed from: a  reason: collision with other method in class */
    public void mo1109a(byte[] bArr, int i, int i2) {
        throw new UnsupportedOperationException("No writing allowed!");
    }

    @Override // com.xiaomi.push.jn
    /* renamed from: a  reason: collision with other method in class */
    public byte[] mo1110a() {
        return this.a;
    }

    @Override // com.xiaomi.push.jn
    public int b() {
        return this.c - this.b;
    }

    @Override // com.xiaomi.push.jn
    public void b(byte[] bArr, int i, int i2) {
        this.a = bArr;
        this.b = i;
        this.c = i + i2;
    }
}
