package io.netty.handler.codec.http2.internal.hpack;

import io.netty.handler.codec.http2.internal.hpack.c;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

/* loaded from: classes4.dex */
public final class Encoder {
    private static final byte[] a = new byte[0];
    private final boolean b;
    private final boolean c;
    private final boolean d;
    private final a[] e;
    private final a f;
    private int g;
    private int h;

    public Encoder(int i) {
        this(i, true, false, false);
    }

    Encoder(int i, boolean z, boolean z2, boolean z3) {
        this.e = new a[17];
        byte[] bArr = a;
        this.f = new a(-1, bArr, bArr, Integer.MAX_VALUE, null);
        if (i >= 0) {
            this.b = z;
            this.c = z2;
            this.d = z3;
            this.h = i;
            a aVar = this.f;
            aVar.b = aVar;
            aVar.a = aVar;
            return;
        }
        throw new IllegalArgumentException("Illegal Capacity: " + i);
    }

    public void encodeHeader(OutputStream outputStream, byte[] bArr, byte[] bArr2, boolean z) throws IOException {
        if (z) {
            a(outputStream, bArr, bArr2, c.a.NEVER, a(bArr));
        } else if (this.h == 0) {
            int a2 = f.a(bArr, bArr2);
            if (a2 == -1) {
                a(outputStream, bArr, bArr2, c.a.NONE, f.a(bArr));
            } else {
                a(outputStream, 128, 7, a2);
            }
        } else {
            int a3 = b.a(bArr, bArr2);
            if (a3 > this.h) {
                a(outputStream, bArr, bArr2, c.a.NONE, a(bArr));
                return;
            }
            a a4 = a(bArr, bArr2);
            if (a4 != null) {
                a(outputStream, 128, 7, b(a4.e) + f.a);
                return;
            }
            int a5 = f.a(bArr, bArr2);
            if (a5 != -1) {
                a(outputStream, 128, 7, a5);
                return;
            }
            int a6 = a(bArr);
            if (this.b) {
                a(a3);
            }
            a(outputStream, bArr, bArr2, this.b ? c.a.INCREMENTAL : c.a.NONE, a6);
            if (this.b) {
                b(bArr, bArr2);
            }
        }
    }

    public void setMaxHeaderTableSize(OutputStream outputStream, int i) throws IOException {
        if (i < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + i);
        } else if (this.h != i) {
            this.h = i;
            a(0);
            a(outputStream, 32, 5, i);
        }
    }

    public int getMaxHeaderTableSize() {
        return this.h;
    }

    private static void a(OutputStream outputStream, int i, int i2, int i3) throws IOException {
        if (i2 < 0 || i2 > 8) {
            throw new IllegalArgumentException("N: " + i2);
        }
        int i4 = 255 >>> (8 - i2);
        if (i3 < i4) {
            outputStream.write(i | i3);
            return;
        }
        outputStream.write(i | i4);
        int i5 = i3 - i4;
        while ((i5 & (-128)) != 0) {
            outputStream.write((i5 & 127) | 128);
            i5 >>>= 7;
        }
        outputStream.write(i5);
    }

    private void a(OutputStream outputStream, byte[] bArr) throws IOException {
        int a2 = Huffman.ENCODER.a(bArr);
        if ((a2 >= bArr.length || this.d) && !this.c) {
            a(outputStream, 0, 7, bArr.length);
            outputStream.write(bArr, 0, bArr.length);
            return;
        }
        a(outputStream, 128, 7, a2);
        Huffman.ENCODER.a(outputStream, bArr);
    }

    private void a(OutputStream outputStream, byte[] bArr, byte[] bArr2, c.a aVar, int i) throws IOException {
        int i2;
        int i3 = 4;
        int i4 = 0;
        switch (aVar) {
            case INCREMENTAL:
                i2 = 64;
                i3 = 6;
                break;
            case NONE:
                i2 = 0;
                break;
            case NEVER:
                i2 = 16;
                break;
            default:
                throw new IllegalStateException("should not reach here");
        }
        if (i != -1) {
            i4 = i;
        }
        a(outputStream, i2, i3, i4);
        if (i == -1) {
            a(outputStream, bArr);
        }
        a(outputStream, bArr2);
    }

    private int a(byte[] bArr) {
        int a2 = f.a(bArr);
        if (a2 != -1) {
            return a2;
        }
        int b = b(bArr);
        return b >= 0 ? b + f.a : b;
    }

    private void a(int i) throws IOException {
        while (this.g + i > this.h && a() != 0) {
            b();
        }
    }

    int a() {
        if (this.g == 0) {
            return 0;
        }
        return (this.f.b.e - this.f.a.e) + 1;
    }

    private a a(byte[] bArr, byte[] bArr2) {
        if (a() == 0 || bArr == null || bArr2 == null) {
            return null;
        }
        int c = c(bArr);
        for (a aVar = this.e[c(c)]; aVar != null; aVar = aVar.c) {
            if (aVar.d == c && c.a(bArr, aVar.f) && c.a(bArr2, aVar.g)) {
                return aVar;
            }
        }
        return null;
    }

    private int b(byte[] bArr) {
        int i = -1;
        if (a() == 0 || bArr == null) {
            return -1;
        }
        int c = c(bArr);
        a aVar = this.e[c(c)];
        while (true) {
            if (aVar != null) {
                if (aVar.d == c && c.a(bArr, aVar.f)) {
                    i = aVar.e;
                    break;
                }
                aVar = aVar.c;
            } else {
                break;
            }
        }
        return b(i);
    }

    private int b(int i) {
        return i == -1 ? i : (i - this.f.a.e) + 1;
    }

    private void b(byte[] bArr, byte[] bArr2) {
        int a2 = b.a(bArr, bArr2);
        if (a2 > this.h) {
            c();
            return;
        }
        while (this.g + a2 > this.h) {
            b();
        }
        byte[] copyOf = Arrays.copyOf(bArr, bArr.length);
        byte[] copyOf2 = Arrays.copyOf(bArr2, bArr2.length);
        int c = c(copyOf);
        int c2 = c(c);
        a aVar = new a(c, copyOf, copyOf2, this.f.a.e - 1, this.e[c2]);
        this.e[c2] = aVar;
        aVar.b(this.f);
        this.g += a2;
    }

    private b b() {
        if (this.g == 0) {
            return null;
        }
        a aVar = this.f.b;
        int c = c(aVar.d);
        a aVar2 = this.e[c];
        a aVar3 = aVar2;
        while (aVar2 != null) {
            a aVar4 = aVar2.c;
            if (aVar2 == aVar) {
                if (aVar3 == aVar) {
                    this.e[c] = aVar4;
                } else {
                    aVar3.c = aVar4;
                }
                aVar.b();
                this.g -= aVar.a();
                return aVar;
            }
            aVar3 = aVar2;
            aVar2 = aVar4;
        }
        return null;
    }

    private void c() {
        Arrays.fill(this.e, (Object) null);
        a aVar = this.f;
        aVar.b = aVar;
        aVar.a = aVar;
        this.g = 0;
    }

    private static int c(byte[] bArr) {
        int i = 0;
        for (byte b : bArr) {
            i = (i * 31) + b;
        }
        if (i > 0) {
            return i;
        }
        if (i == Integer.MIN_VALUE) {
            return Integer.MAX_VALUE;
        }
        return -i;
    }

    private static int c(int i) {
        return i % 17;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class a extends b {
        a a;
        a b;
        a c;
        int d;
        int e;

        a(int i, byte[] bArr, byte[] bArr2, int i2, a aVar) {
            super(bArr, bArr2);
            this.e = i2;
            this.d = i;
            this.c = aVar;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b() {
            a aVar = this.a;
            aVar.b = this.b;
            this.b.a = aVar;
            this.a = null;
            this.b = null;
            this.c = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(a aVar) {
            this.b = aVar;
            this.a = aVar.a;
            this.a.b = this;
            this.b.a = this;
        }
    }
}
