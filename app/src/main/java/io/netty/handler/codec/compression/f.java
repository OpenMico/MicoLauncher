package io.netty.handler.codec.compression;

/* compiled from: Bzip2HuffmanAllocator.java */
/* loaded from: classes4.dex */
final class f {
    private static int a(int[] iArr, int i, int i2) {
        int length = iArr.length;
        int length2 = iArr.length - 2;
        int i3 = i;
        while (i3 >= i2 && iArr[i3] % length > i) {
            i3 -= (i - i3) + 1;
            length2 = i3;
        }
        int max = Math.max(i2 - 1, i3);
        while (length2 > max + 1) {
            int i4 = (max + length2) >>> 1;
            if (iArr[i4] % length > i) {
                length2 = i4;
            } else {
                max = i4;
            }
        }
        return length2;
    }

    private static void a(int[] iArr) {
        int i;
        int i2;
        int length = iArr.length;
        int i3 = 0;
        iArr[0] = iArr[0] + iArr[1];
        int i4 = 2;
        for (int i5 = 1; i5 < length - 1; i5++) {
            if (i4 >= length || iArr[i3] < iArr[i4]) {
                i = iArr[i3];
                i3++;
                iArr[i3] = i5;
            } else {
                i4++;
                i = iArr[i4];
            }
            if (i4 >= length || (i3 < i5 && iArr[i3] < iArr[i4])) {
                i2 = i + iArr[i3];
                i3++;
                iArr[i3] = i5 + length;
            } else {
                i4++;
                i2 = i + iArr[i4];
            }
            iArr[i5] = i2;
        }
    }

    private static int b(int[] iArr, int i) {
        int length = iArr.length - 2;
        for (int i2 = 1; i2 < i - 1 && length > 1; i2++) {
            length = a(iArr, length - 1, 0);
        }
        return length;
    }

    private static void b(int[] iArr) {
        int i = 2;
        int length = iArr.length - 2;
        int i2 = 1;
        int length2 = iArr.length - 1;
        while (i > 0) {
            int a = a(iArr, length - 1, 0);
            int i3 = length - a;
            for (int i4 = i - i3; i4 > 0; i4--) {
                length2--;
                iArr[length2] = i2;
            }
            i = i3 << 1;
            i2++;
            length = a;
        }
    }

    private static void b(int[] iArr, int i, int i2) {
        int i3 = 2;
        int length = iArr.length - 2;
        int length2 = iArr.length - 1;
        if (i2 != 1) {
            i3 = 1;
        }
        int i4 = i2 == 1 ? i - 2 : i;
        int i5 = i3 << 1;
        while (i5 > 0) {
            int a = length <= i ? length : a(iArr, length - 1, i);
            int i6 = 0;
            if (i3 >= i2) {
                i6 = Math.min(i4, 1 << (i3 - i2));
            } else if (i3 == i2 - 1) {
                if (iArr[a] == length) {
                    a++;
                    i6 = 1;
                } else {
                    i6 = 1;
                }
            }
            int i7 = (length - a) + i6;
            for (int i8 = i5 - i7; i8 > 0; i8--) {
                length2--;
                iArr[length2] = i3;
            }
            i4 -= i6;
            i5 = i7 << 1;
            i3++;
            length = a;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(int[] iArr, int i) {
        switch (iArr.length) {
            case 1:
                break;
            default:
                a(iArr);
                int b = b(iArr, i);
                if (iArr[0] % iArr.length >= b) {
                    b(iArr);
                    return;
                } else {
                    b(iArr, b, i - (32 - Integer.numberOfLeadingZeros(b - 1)));
                    return;
                }
            case 2:
                iArr[1] = 1;
                break;
        }
        iArr[0] = 1;
    }
}
