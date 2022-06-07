package com.xiaomi.micolauncher.common.utils.HeatShrink;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class HsInputStream extends FilterInputStream {
    private final byte[] a;
    private final byte[] b;
    private final int c;
    private final int d;
    private a e;
    private int f;
    private int g;
    private boolean h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private final byte[] n;
    private final Result o;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public enum a {
        TAG_BIT,
        YIELD_LITERAL,
        BACKREF_BOUNDS,
        YIELD_BACKREF,
        BUFFER_EMPTY
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public boolean markSupported() {
        return false;
    }

    public HsInputStream(InputStream inputStream) {
        this(inputStream, 11, 4);
    }

    public HsInputStream(InputStream inputStream, int i, int i2) {
        this(inputStream, a(0, i), i, i2);
    }

    public HsInputStream(InputStream inputStream, int i, int i2, int i3) {
        super(inputStream);
        this.n = new byte[1];
        this.o = new Result();
        this.a = new byte[a(i, i2)];
        this.b = new byte[1 << i2];
        this.c = i2;
        this.d = i3;
        clear();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        if (read(this.n) <= 0) {
            return -1;
        }
        return this.n[0] & 255;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        this.o.a(bArr, i, i2);
        while (true) {
            if (this.o.a < this.o.c) {
                a aVar = this.e;
                switch (aVar) {
                    case TAG_BIT:
                        this.e = a();
                        break;
                    case YIELD_LITERAL:
                        this.e = b(this.o);
                        break;
                    case BACKREF_BOUNDS:
                        this.e = b();
                        break;
                    case YIELD_BACKREF:
                        this.e = a(this.o);
                        break;
                }
                if (this.e == a.BUFFER_EMPTY) {
                    this.e = aVar;
                }
            }
        }
        int i3 = this.o.a - i;
        return i3 > 0 ? i3 : this.h ? -1 : 0;
    }

    private a a() throws IOException {
        int b = b(1);
        if (b == -1) {
            return a.BUFFER_EMPTY;
        }
        if (b != 0) {
            return a.YIELD_LITERAL;
        }
        this.m = 0;
        this.l = 0;
        return a.BACKREF_BOUNDS;
    }

    private a b() throws IOException {
        int b = b(this.c);
        if (b == -1) {
            return a.BUFFER_EMPTY;
        }
        this.m = b + 1;
        int b2 = b(this.d);
        if (b2 == -1) {
            return a.BUFFER_EMPTY;
        }
        this.l = b2 + 1;
        return a.YIELD_BACKREF;
    }

    private a a(Result result) {
        int min = Math.min(result.c - result.a, this.l);
        if (min > 0) {
            int i = (1 << this.c) - 1;
            for (int i2 = 0; i2 < min; i2++) {
                byte b = this.b[(this.k - this.m) & i];
                byte[] bArr = result.d;
                int i3 = result.a;
                result.a = i3 + 1;
                bArr[i3] = b;
                byte[] bArr2 = this.b;
                int i4 = this.k;
                this.k = i4 + 1;
                bArr2[i4 & i] = b;
            }
            this.l -= min;
            if (this.l == 0) {
                return a.TAG_BIT;
            }
        }
        return a.YIELD_BACKREF;
    }

    private a b(Result result) throws IOException {
        if (result.a >= result.c) {
            return a.YIELD_LITERAL;
        }
        int b = b(8);
        if (b == -1) {
            return a.BUFFER_EMPTY;
        }
        byte b2 = (byte) (b & 255);
        byte[] bArr = this.b;
        int i = this.k;
        this.k = i + 1;
        bArr[((1 << this.c) - 1) & i] = b2;
        byte[] bArr2 = result.d;
        int i2 = result.a;
        result.a = i2 + 1;
        bArr2[i2] = b2;
        return a.TAG_BIT;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public long skip(long j) throws IOException {
        long j2 = 0;
        while (j2 < j && read(this.n) > 0) {
            j2++;
        }
        return j2;
    }

    public long skipRaw(long j) throws IOException {
        long min = Math.min(j, this.g - this.f);
        this.i = 0;
        this.f = (int) (this.f + min);
        long j2 = j - min;
        return j2 > 0 ? min + this.in.skip(j2) : min;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int available() throws IOException {
        return (this.g - this.f) + this.in.available();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.in.close();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public void mark(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public void reset() throws IOException {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        this.e = a.TAG_BIT;
        this.l = 0;
        this.m = 0;
        this.f = 0;
        this.g = 0;
        this.h = false;
        this.i = 0;
        this.j = 0;
        this.k = 0;
        Arrays.fill(this.b, (byte) 0);
        Arrays.fill(this.a, (byte) 0);
    }

    boolean a(int i) throws IOException {
        int i2 = this.g;
        int i3 = this.f;
        int i4 = i2 - i3;
        int i5 = i4 * 8;
        int i6 = i - this.i;
        if (i6 > i5) {
            if (i4 > 0) {
                byte[] bArr = this.a;
                System.arraycopy(bArr, i3, bArr, 0, i4);
            }
            this.f = 0;
            this.g = i4;
            InputStream inputStream = this.in;
            byte[] bArr2 = this.a;
            int read = inputStream.read(bArr2, i4, bArr2.length - i4);
            if (read > -1) {
                this.g += read;
            } else {
                this.h = true;
            }
            i5 = this.g * 8;
        }
        return i5 >= i6;
    }

    int b(int i) throws IOException {
        if (!a(i)) {
            return -1;
        }
        int i2 = 0;
        while (i > 0) {
            if (this.i == 0) {
                byte[] bArr = this.a;
                int i3 = this.f;
                this.f = i3 + 1;
                this.j = bArr[i3];
                this.i = 8;
            }
            i2 <<= 1;
            if (this.i == 8 && i >= 8) {
                i2 = (i2 << 7) | (this.j & 255);
                i -= 7;
                this.i = 1;
            } else if ((this.j & (1 << (this.i - 1))) != 0) {
                i2 |= 1;
            }
            i--;
            this.i--;
        }
        return i2;
    }

    private static int a(int i, int i2) {
        return Math.max(1 << i2, i);
    }
}
