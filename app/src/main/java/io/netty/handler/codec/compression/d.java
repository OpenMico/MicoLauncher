package io.netty.handler.codec.compression;

/* compiled from: Bzip2BlockDecompressor.java */
/* loaded from: classes4.dex */
final class d {
    int a;
    int b;
    private final a d;
    private final int f;
    private final boolean g;
    private final byte[] i;
    private final int j;
    private int[] k;
    private int l;
    private int m;
    private int n;
    private int p;
    private int q;
    private int r;
    private int u;
    private int w;
    private final l e = new l();
    final byte[] c = new byte[256];
    private final int[] h = new int[256];
    private int o = -1;
    private int s = k.a(0) - 1;
    private final j t = new j();
    private int v = 1;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(int i, int i2, boolean z, int i3, a aVar) {
        this.i = new byte[i];
        this.f = i2;
        this.g = z;
        this.j = i3;
        this.d = aVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(g gVar) {
        a aVar = this.d;
        byte[] bArr = this.i;
        byte[] bArr2 = this.c;
        int length = bArr.length;
        int i = this.a;
        int[] iArr = this.h;
        j jVar = this.t;
        int i2 = this.m;
        int i3 = this.u;
        int i4 = this.v;
        int i5 = this.w;
        while (aVar.b(23)) {
            int b = gVar.b();
            if (b == 0) {
                i3 += i4;
                i4 <<= 1;
            } else if (b == 1) {
                i4 <<= 1;
                i3 += i4;
            } else {
                if (i3 > 0) {
                    if (i2 + i3 <= length) {
                        byte b2 = bArr2[i5];
                        int i6 = b2 & 255;
                        iArr[i6] = iArr[i6] + i3;
                        while (true) {
                            i3--;
                            if (i3 < 0) {
                                break;
                            }
                            i2++;
                            bArr[i2] = b2;
                        }
                        i3 = 0;
                        i4 = 1;
                    } else {
                        throw new DecompressionException("block exceeds declared block size");
                    }
                }
                if (b == i) {
                    this.m = i2;
                    d();
                    return true;
                } else if (i2 < length) {
                    i5 = jVar.a(b - 1) & 255;
                    byte b3 = bArr2[i5];
                    int i7 = b3 & 255;
                    iArr[i7] = iArr[i7] + 1;
                    i2++;
                    bArr[i2] = b3;
                } else {
                    throw new DecompressionException("block exceeds declared block size");
                }
            }
        }
        this.m = i2;
        this.u = i3;
        this.v = i4;
        this.w = i5;
        return false;
    }

    private void d() {
        int i = this.j;
        byte[] bArr = this.i;
        int i2 = this.m;
        int[] iArr = new int[i2];
        int[] iArr2 = new int[256];
        if (i < 0 || i >= i2) {
            throw new DecompressionException("start pointer invalid");
        }
        System.arraycopy(this.h, 0, iArr2, 1, 255);
        for (int i3 = 2; i3 <= 255; i3++) {
            iArr2[i3] = iArr2[i3] + iArr2[i3 - 1];
        }
        for (int i4 = 0; i4 < this.m; i4++) {
            int i5 = bArr[i4] & 255;
            int i6 = iArr2[i5];
            iArr2[i5] = i6 + 1;
            iArr[i6] = (i4 << 8) + i5;
        }
        this.k = iArr;
        this.l = iArr[i];
    }

    public int a() {
        while (true) {
            int i = this.q;
            if (i >= 1) {
                this.q = i - 1;
                return this.o;
            } else if (this.n == this.m) {
                return -1;
            } else {
                int e = e();
                if (e != this.o) {
                    this.o = e;
                    this.q = 1;
                    this.p = 1;
                    this.e.a(e);
                } else {
                    int i2 = this.p + 1;
                    this.p = i2;
                    if (i2 == 4) {
                        int e2 = e() + 1;
                        this.q = e2;
                        this.p = 0;
                        this.e.a(e, e2);
                    } else {
                        this.q = 1;
                        this.e.a(e);
                    }
                }
            }
        }
    }

    private int e() {
        int i = this.l;
        int i2 = i & 255;
        this.l = this.k[i >>> 8];
        if (this.g) {
            int i3 = this.s - 1;
            this.s = i3;
            if (i3 == 0) {
                i2 ^= 1;
                this.r = (this.r + 1) % 512;
                this.s = k.a(this.r);
            }
        }
        this.n++;
        return i2;
    }

    public int b() {
        return this.m;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int c() {
        int a = this.e.a();
        if (this.f == a) {
            return a;
        }
        throw new DecompressionException("block CRC error");
    }
}
