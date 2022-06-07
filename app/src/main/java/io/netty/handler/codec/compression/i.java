package io.netty.handler.codec.compression;

import com.xiaomi.idm.api.conn.EndPoint;

/* compiled from: Bzip2MTFAndRLE2StageEncoder.java */
/* loaded from: classes4.dex */
final class i {
    private final int[] a;
    private final int b;
    private final boolean[] c;
    private final char[] d;
    private int e;
    private final int[] f = new int[EndPoint.MIRROR_V1_MC_VERSION];
    private int g;

    /* JADX INFO: Access modifiers changed from: package-private */
    public i(int[] iArr, int i, boolean[] zArr) {
        this.a = iArr;
        this.b = i;
        this.c = zArr;
        this.d = new char[i + 1];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        int i = this.b;
        boolean[] zArr = this.c;
        int[] iArr = this.a;
        char[] cArr = this.d;
        int[] iArr2 = this.f;
        byte[] bArr = new byte[256];
        j jVar = new j();
        char c = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < bArr.length; i3++) {
            if (zArr[i3]) {
                i2++;
                bArr[i3] = (byte) i2;
            }
        }
        int i4 = i2 + 1;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        while (i5 < i) {
            int a = jVar.a(bArr[iArr[i5] & 255]);
            if (a == 0) {
                i6++;
            } else {
                if (i6 > 0) {
                    int i10 = i6 - 1;
                    while (true) {
                        if ((i10 & 1) == 0) {
                            i7++;
                            cArr[i7] = c;
                            i8++;
                        } else {
                            i7++;
                            cArr[i7] = 1;
                            i9++;
                        }
                        if (i10 <= 1) {
                            break;
                        }
                        i10 = (i10 - 2) >>> 1;
                    }
                    i6 = c;
                }
                i7++;
                int i11 = a + 1;
                cArr[i7] = (char) i11;
                iArr2[i11] = iArr2[i11] + 1;
            }
            i5++;
            c = 0;
        }
        if (i6 > 0) {
            int i12 = i6 - 1;
            while (true) {
                if ((i12 & 1) == 0) {
                    i7++;
                    cArr[i7] = 0;
                    i8++;
                } else {
                    i7++;
                    cArr[i7] = 1;
                    i9++;
                }
                if (i12 <= 1) {
                    break;
                }
                i12 = (i12 - 2) >>> 1;
            }
        }
        cArr[i7] = (char) i4;
        iArr2[i4] = iArr2[i4] + 1;
        iArr2[0] = iArr2[0] + i8;
        iArr2[1] = iArr2[1] + i9;
        this.e = i7 + 1;
        this.g = i4 + 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public char[] b() {
        return this.d;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int c() {
        return this.e;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int d() {
        return this.g;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int[] e() {
        return this.f;
    }
}
