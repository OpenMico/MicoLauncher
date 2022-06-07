package io.netty.handler.codec.compression;

import androidx.core.app.FrameMetricsAggregator;
import io.netty.buffer.ByteBuf;
import java.lang.reflect.Array;
import java.util.Arrays;

/* compiled from: Bzip2HuffmanStageEncoder.java */
/* loaded from: classes4.dex */
final class h {
    private final b a;
    private final char[] b;
    private final int c;
    private final int d;
    private final int[] e;
    private final int[][] f;
    private final int[][] g;
    private final byte[] h;

    private static int a(int i) {
        if (i >= 2400) {
            return 6;
        }
        if (i >= 1200) {
            return 5;
        }
        if (i >= 600) {
            return 4;
        }
        return i >= 200 ? 3 : 2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public h(b bVar, char[] cArr, int i, int i2, int[] iArr) {
        this.a = bVar;
        this.b = cArr;
        this.c = i;
        this.d = i2;
        this.e = iArr;
        int a = a(i);
        this.f = (int[][]) Array.newInstance(int.class, a, i2);
        this.g = (int[][]) Array.newInstance(int.class, a, i2);
        this.h = new byte[((i + 50) - 1) / 50];
    }

    private static void a(int i, int[] iArr, int[] iArr2) {
        int[] iArr3 = new int[i];
        int[] iArr4 = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            iArr3[i2] = (iArr[i2] << 9) | i2;
        }
        Arrays.sort(iArr3);
        for (int i3 = 0; i3 < i; i3++) {
            iArr4[i3] = iArr3[i3] >>> 9;
        }
        f.a(iArr4, 20);
        for (int i4 = 0; i4 < i; i4++) {
            iArr2[iArr3[i4] & FrameMetricsAggregator.EVERY_DURATION] = iArr4[i4];
        }
    }

    private void a() {
        int i;
        int[][] iArr = this.f;
        int[] iArr2 = this.e;
        int i2 = this.d;
        int length = iArr.length;
        int i3 = -1;
        int i4 = this.c;
        for (int i5 = 0; i5 < length; i5++) {
            int i6 = length - i5;
            int i7 = i4 / i6;
            int i8 = i3 + 1;
            int i9 = i3;
            int i10 = 0;
            while (i10 < i7 && i9 < i2 - 1) {
                i9++;
                i10 += iArr2[i9];
            }
            if (i9 <= i8 || i5 == 0 || i5 == length - 1 || (i6 & 1) != 0) {
                i = i10;
                i3 = i9;
            } else {
                i3 = i9 - 1;
                i = i10 - iArr2[i9];
            }
            int[] iArr3 = iArr[i5];
            for (int i11 = 0; i11 < i2; i11++) {
                if (i11 < i8 || i11 > i3) {
                    iArr3[i11] = 15;
                }
            }
            i4 -= i;
        }
    }

    private void a(boolean z) {
        char[] cArr = this.b;
        byte[] bArr = this.h;
        int[][] iArr = this.f;
        int i = this.c;
        int i2 = this.d;
        int length = iArr.length;
        int[][] iArr2 = (int[][]) Array.newInstance(int.class, length, i2);
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            int min = Math.min(i3 + 50, i) - 1;
            short[] sArr = new short[length];
            for (int i5 = i3; i5 <= min; i5++) {
                char c = cArr[i5];
                for (int i6 = 0; i6 < length; i6++) {
                    sArr[i6] = (short) (sArr[i6] + iArr[i6][c]);
                }
            }
            byte b = 0;
            short s = sArr[0];
            for (byte b2 = 1; b2 < length; b2 = (byte) (b2 + 1)) {
                short s2 = sArr[b2];
                if (s2 < s) {
                    s = s2;
                    b = b2;
                }
            }
            int[] iArr3 = iArr2[b];
            while (i3 <= min) {
                char c2 = cArr[i3];
                iArr3[c2] = iArr3[c2] + 1;
                i3++;
            }
            if (z) {
                i4++;
                bArr[i4] = b;
            }
            i3 = min + 1;
        }
        for (int i7 = 0; i7 < length; i7++) {
            a(i2, iArr2[i7], iArr[i7]);
        }
    }

    private void b() {
        int[][] iArr = this.g;
        int[][] iArr2 = this.f;
        int i = this.d;
        int length = iArr2.length;
        for (int i2 = 0; i2 < length; i2++) {
            int[] iArr3 = iArr2[i2];
            int i3 = 32;
            int i4 = 0;
            for (int i5 = 0; i5 < i; i5++) {
                int i6 = iArr3[i5];
                if (i6 > i4) {
                    i4 = i6;
                }
                if (i6 < i3) {
                    i3 = i6;
                }
            }
            int i7 = 0;
            while (i3 <= i4) {
                for (int i8 = 0; i8 < i; i8++) {
                    if ((iArr2[i2][i8] & 255) == i3) {
                        iArr[i2][i8] = (i3 << 24) | i7;
                        i7++;
                    }
                }
                i7 <<= 1;
                i3++;
            }
        }
    }

    private void b(ByteBuf byteBuf) {
        b bVar = this.a;
        byte[] bArr = this.h;
        int length = bArr.length;
        int[][] iArr = this.f;
        int length2 = iArr.length;
        int i = this.d;
        long j = length2;
        int i2 = 3;
        bVar.a(byteBuf, 3, j);
        bVar.a(byteBuf, 15, length);
        j jVar = new j();
        for (byte b : bArr) {
            bVar.a(byteBuf, jVar.a(b));
        }
        int length3 = iArr.length;
        int i3 = 0;
        while (i3 < length3) {
            int[] iArr2 = iArr[i3];
            int i4 = iArr2[0];
            bVar.a(byteBuf, 5, i4);
            int i5 = i4;
            int i6 = 0;
            while (i6 < i) {
                int i7 = iArr2[i6];
                if (i5 < i7) {
                    i2 = 2;
                }
                int abs = Math.abs(i7 - i5);
                while (true) {
                    abs--;
                    if (abs > 0) {
                        bVar.a(byteBuf, 2, i2);
                        i = i;
                    }
                }
                bVar.a(byteBuf, false);
                i6++;
                i5 = i7;
                i2 = 3;
            }
            i3++;
            i2 = 3;
        }
    }

    private void c(ByteBuf byteBuf) {
        b bVar = this.a;
        int[][] iArr = this.g;
        byte[] bArr = this.h;
        char[] cArr = this.b;
        int i = this.c;
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            int min = Math.min(i2 + 50, i) - 1;
            i3++;
            int[] iArr2 = iArr[bArr[i3]];
            while (i2 <= min) {
                i2++;
                int i4 = iArr2[cArr[i2]];
                bVar.a(byteBuf, i4 >>> 24, i4);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(ByteBuf byteBuf) {
        a();
        int i = 3;
        while (i >= 0) {
            a(i == 0);
            i--;
        }
        b();
        b(byteBuf);
        c(byteBuf);
    }
}
