package com.xiaomi.push;

/* loaded from: classes4.dex */
public class jl extends jn {
    private iv a;
    private int b;

    public jl(int i) {
        this.a = new iv(i);
    }

    @Override // com.xiaomi.push.jn
    /* renamed from: a */
    public int mo1109a(byte[] bArr, int i, int i2) {
        byte[] a = this.a.m1084a();
        if (i2 > this.a.a() - this.b) {
            i2 = this.a.a() - this.b;
        }
        if (i2 > 0) {
            System.arraycopy(a, this.b, bArr, i, i2);
            this.b += i2;
        }
        return i2;
    }

    @Override // com.xiaomi.push.jn
    /* renamed from: a  reason: collision with other method in class */
    public void mo1109a(byte[] bArr, int i, int i2) {
        this.a.write(bArr, i, i2);
    }

    public int a_() {
        return this.a.size();
    }
}
