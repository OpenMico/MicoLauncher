package io.netty.handler.codec.compression;

import com.xiaomi.idm.api.conn.EndPoint;
import java.lang.reflect.Array;

/* compiled from: Bzip2HuffmanStageDecoder.java */
/* loaded from: classes4.dex */
final class g {
    byte[] a;
    final int b;
    final int c;
    int e;
    final byte[][] f;
    int g;
    int i;
    boolean j;
    private final a k;
    private final int[] l;
    private final int[][] m;
    private final int[][] n;
    private final int[][] o;
    private int p;
    private int q = -1;
    private int r = -1;
    final j d = new j();
    int h = -1;

    /* JADX INFO: Access modifiers changed from: package-private */
    public g(a aVar, int i, int i2) {
        this.k = aVar;
        this.b = i;
        this.c = i2;
        this.l = new int[i];
        this.m = (int[][]) Array.newInstance(int.class, i, 25);
        this.n = (int[][]) Array.newInstance(int.class, i, 24);
        this.o = (int[][]) Array.newInstance(int.class, i, EndPoint.MIRROR_V1_MC_VERSION);
        this.f = (byte[][]) Array.newInstance(byte.class, i, EndPoint.MIRROR_V1_MC_VERSION);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        int i = this.c;
        int i2 = 0;
        while (true) {
            byte[][] bArr = this.f;
            if (i2 < bArr.length) {
                int[] iArr = this.m[i2];
                int[] iArr2 = this.n[i2];
                int[] iArr3 = this.o[i2];
                byte[] bArr2 = bArr[i2];
                int i3 = 23;
                int i4 = 0;
                for (int i5 = 0; i5 < i; i5++) {
                    byte b = bArr2[i5];
                    i4 = Math.max((int) b, i4);
                    i3 = Math.min((int) b, i3);
                }
                this.l[i2] = i3;
                for (int i6 = 0; i6 < i; i6++) {
                    int i7 = bArr2[i6] + 1;
                    iArr[i7] = iArr[i7] + 1;
                }
                int i8 = iArr[0];
                for (int i9 = 1; i9 < 25; i9++) {
                    i8 += iArr[i9];
                    iArr[i9] = i8;
                }
                int i10 = 0;
                int i11 = i3;
                while (i11 <= i4) {
                    int i12 = i11 + 1;
                    int i13 = (iArr[i12] - iArr[i11]) + i10;
                    iArr[i11] = i10 - iArr[i11];
                    iArr2[i11] = i13 - 1;
                    i10 = i13 << 1;
                    i11 = i12;
                }
                int i14 = 0;
                while (i3 <= i4) {
                    for (int i15 = 0; i15 < i; i15++) {
                        if (bArr2[i15] == i3) {
                            i14++;
                            iArr3[i14] = i15;
                        }
                    }
                    i3++;
                    i14 = i14;
                }
                i2++;
            } else {
                this.p = this.a[0];
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int b() {
        int i = this.r + 1;
        this.r = i;
        if (i % 50 == 0) {
            this.q++;
            int i2 = this.q;
            byte[] bArr = this.a;
            if (i2 != bArr.length) {
                this.p = bArr[i2] & 255;
            } else {
                throw new DecompressionException("error decoding block");
            }
        }
        a aVar = this.k;
        int i3 = this.p;
        int[] iArr = this.n[i3];
        int[] iArr2 = this.m[i3];
        int[] iArr3 = this.o[i3];
        int i4 = this.l[i3];
        int a = aVar.a(i4);
        while (i4 <= 23) {
            if (a <= iArr[i4]) {
                return iArr3[a - iArr2[i4]];
            }
            a = (a << 1) | aVar.a(1);
            i4++;
        }
        throw new DecompressionException("a valid code was not recognised");
    }
}
