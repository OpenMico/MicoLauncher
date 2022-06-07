package com.xiaomi.push;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/* loaded from: classes4.dex */
public final class c {
    private final byte[] a;
    private final int b;
    private int c;
    private final OutputStream d;

    /* loaded from: classes4.dex */
    public static class a extends IOException {
        a() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }
    }

    private c(OutputStream outputStream, byte[] bArr) {
        this.d = outputStream;
        this.a = bArr;
        this.c = 0;
        this.b = bArr.length;
    }

    private c(byte[] bArr, int i, int i2) {
        this.d = null;
        this.a = bArr;
        this.c = i;
        this.b = i + i2;
    }

    public static int a(int i) {
        if (i >= 0) {
            return d(i);
        }
        return 10;
    }

    public static int a(int i, int i2) {
        return c(i) + a(i2);
    }

    public static int a(int i, long j) {
        return c(i) + a(j);
    }

    public static int a(int i, a aVar) {
        return c(i) + a(aVar);
    }

    public static int a(int i, e eVar) {
        return c(i) + a(eVar);
    }

    public static int a(int i, String str) {
        return c(i) + a(str);
    }

    public static int a(int i, boolean z) {
        return c(i) + a(z);
    }

    public static int a(long j) {
        return c(j);
    }

    public static int a(a aVar) {
        return d(aVar.a()) + aVar.a();
    }

    public static int a(e eVar) {
        int b = eVar.mo890b();
        return d(b) + b;
    }

    public static int a(String str) {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            return d(bytes.length) + bytes.length;
        } catch (UnsupportedEncodingException unused) {
            throw new RuntimeException("UTF-8 not supported.");
        }
    }

    public static int a(boolean z) {
        return 1;
    }

    public static c a(OutputStream outputStream) {
        return a(outputStream, 4096);
    }

    public static c a(OutputStream outputStream, int i) {
        return new c(outputStream, new byte[i]);
    }

    public static c a(byte[] bArr, int i, int i2) {
        return new c(bArr, i, i2);
    }

    public static int b(int i) {
        return d(i);
    }

    public static int b(int i, int i2) {
        return c(i) + b(i2);
    }

    public static int b(int i, long j) {
        return c(i) + b(j);
    }

    public static int b(long j) {
        return c(j);
    }

    public static int c(int i) {
        return d(f.a(i, 0));
    }

    public static int c(long j) {
        if (((-128) & j) == 0) {
            return 1;
        }
        if (((-16384) & j) == 0) {
            return 2;
        }
        if (((-2097152) & j) == 0) {
            return 3;
        }
        if (((-268435456) & j) == 0) {
            return 4;
        }
        if (((-34359738368L) & j) == 0) {
            return 5;
        }
        if (((-4398046511104L) & j) == 0) {
            return 6;
        }
        if (((-562949953421312L) & j) == 0) {
            return 7;
        }
        if (((-72057594037927936L) & j) == 0) {
            return 8;
        }
        return (j & Long.MIN_VALUE) == 0 ? 9 : 10;
    }

    private void c() {
        OutputStream outputStream = this.d;
        if (outputStream != null) {
            outputStream.write(this.a, 0, this.c);
            this.c = 0;
            return;
        }
        throw new a();
    }

    public static int d(int i) {
        if ((i & (-128)) == 0) {
            return 1;
        }
        if ((i & (-16384)) == 0) {
            return 2;
        }
        if (((-2097152) & i) == 0) {
            return 3;
        }
        return (i & (-268435456)) == 0 ? 4 : 5;
    }

    public int a() {
        if (this.d == null) {
            return this.b - this.c;
        }
        throw new UnsupportedOperationException("spaceLeft() can only be called on CodedOutputStreams that are writing to a flat array.");
    }

    /* renamed from: a */
    public void m786a() {
        if (this.d != null) {
            c();
        }
    }

    public void a(byte b) {
        if (this.c == this.b) {
            c();
        }
        byte[] bArr = this.a;
        int i = this.c;
        this.c = i + 1;
        bArr[i] = b;
    }

    /* renamed from: a */
    public void m787a(int i) {
        if (i >= 0) {
            m806d(i);
        } else {
            m805c(i);
        }
    }

    /* renamed from: a */
    public void m788a(int i, int i2) {
        c(i, 0);
        m787a(i2);
    }

    /* renamed from: a */
    public void m789a(int i, long j) {
        c(i, 0);
        m794a(j);
    }

    /* renamed from: a */
    public void m790a(int i, a aVar) {
        c(i, 2);
        m795a(aVar);
    }

    /* renamed from: a */
    public void m791a(int i, e eVar) {
        c(i, 2);
        m796a(eVar);
    }

    /* renamed from: a */
    public void m792a(int i, String str) {
        c(i, 2);
        m797a(str);
    }

    /* renamed from: a */
    public void m793a(int i, boolean z) {
        c(i, 0);
        m798a(z);
    }

    /* renamed from: a */
    public void m794a(long j) {
        m805c(j);
    }

    /* renamed from: a */
    public void m795a(a aVar) {
        byte[] a2 = aVar.m752a();
        m806d(a2.length);
        a(a2);
    }

    /* renamed from: a */
    public void m796a(e eVar) {
        m806d(eVar.mo888a());
        eVar.a(this);
    }

    /* renamed from: a */
    public void m797a(String str) {
        byte[] bytes = str.getBytes("UTF-8");
        m806d(bytes.length);
        a(bytes);
    }

    /* renamed from: a */
    public void m798a(boolean z) {
        m804c(z ? 1 : 0);
    }

    public void a(byte[] bArr) {
        m799a(bArr, 0, bArr.length);
    }

    /* renamed from: a */
    public void m799a(byte[] bArr, int i, int i2) {
        int i3 = this.b;
        int i4 = this.c;
        if (i3 - i4 >= i2) {
            System.arraycopy(bArr, i, this.a, i4, i2);
            this.c += i2;
            return;
        }
        int i5 = i3 - i4;
        System.arraycopy(bArr, i, this.a, i4, i5);
        int i6 = i + i5;
        int i7 = i2 - i5;
        this.c = this.b;
        c();
        if (i7 <= this.b) {
            System.arraycopy(bArr, i6, this.a, 0, i7);
            this.c = i7;
            return;
        }
        this.d.write(bArr, i6, i7);
    }

    public void b() {
        if (a() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    /* renamed from: b */
    public void m800b(int i) {
        m806d(i);
    }

    /* renamed from: b */
    public void m801b(int i, int i2) {
        c(i, 0);
        m800b(i2);
    }

    /* renamed from: b */
    public void m802b(int i, long j) {
        c(i, 0);
        m803b(j);
    }

    /* renamed from: b */
    public void m803b(long j) {
        m805c(j);
    }

    /* renamed from: c */
    public void m804c(int i) {
        a((byte) i);
    }

    public void c(int i, int i2) {
        m806d(f.a(i, i2));
    }

    /* renamed from: c */
    public void m805c(long j) {
        while (((-128) & j) != 0) {
            m804c((((int) j) & 127) | 128);
            j >>>= 7;
        }
        m804c((int) j);
    }

    /* renamed from: d */
    public void m806d(int i) {
        while ((i & (-128)) != 0) {
            m804c((i & 127) | 128);
            i >>>= 7;
        }
        m804c(i);
    }
}
