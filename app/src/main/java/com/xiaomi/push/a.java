package com.xiaomi.push;

/* loaded from: classes4.dex */
public final class a {
    public static final a a = new a(new byte[0]);
    private final byte[] b;
    private volatile int c = 0;

    private a(byte[] bArr) {
        this.b = bArr;
    }

    public static a a(byte[] bArr) {
        return a(bArr, 0, bArr.length);
    }

    public static a a(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return new a(bArr2);
    }

    public int a() {
        return this.b.length;
    }

    /* renamed from: a  reason: collision with other method in class */
    public byte[] m752a() {
        byte[] bArr = this.b;
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        return bArr2;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        byte[] bArr = this.b;
        int length = bArr.length;
        byte[] bArr2 = ((a) obj).b;
        if (length != bArr2.length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int i = this.c;
        if (i == 0) {
            byte[] bArr = this.b;
            int length = bArr.length;
            int i2 = length;
            for (byte b : bArr) {
                i2 = (i2 * 31) + b;
            }
            i = i2 == 0 ? 1 : i2;
            this.c = i;
        }
        return i;
    }
}
