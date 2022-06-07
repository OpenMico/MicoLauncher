package com.google.zxing.oned;

import com.alibaba.fastjson.asm.Opcodes;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.google.common.base.Ascii;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.Map;

/* loaded from: classes2.dex */
public final class Code39Reader extends OneDReader {
    static final int[] a = {52, 289, 97, 352, 49, 304, 112, 37, 292, 100, 265, 73, 328, 25, 280, 88, 13, 268, 76, 28, 259, 67, 322, 19, 274, 82, 7, 262, 70, 22, 385, Opcodes.INSTANCEOF, 448, 145, 400, 208, 133, 388, 196, 168, Opcodes.IF_ICMPGE, TsExtractor.TS_STREAM_TYPE_DTS, 42};
    private final boolean b;
    private final boolean c;
    private final StringBuilder d;
    private final int[] e;

    public Code39Reader() {
        this(false);
    }

    public Code39Reader(boolean z) {
        this(z, false);
    }

    public Code39Reader(boolean z, boolean z2) {
        this.b = z;
        this.c = z2;
        this.d = new StringBuilder(20);
        this.e = new int[9];
    }

    @Override // com.google.zxing.oned.OneDReader
    public Result decodeRow(int i, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        String str;
        int[] iArr = this.e;
        Arrays.fill(iArr, 0);
        StringBuilder sb = this.d;
        sb.setLength(0);
        int[] a2 = a(bitArray, iArr);
        int nextSet = bitArray.getNextSet(a2[1]);
        int size = bitArray.getSize();
        while (true) {
            recordPattern(bitArray, nextSet, iArr);
            int a3 = a(iArr);
            if (a3 >= 0) {
                char a4 = a(a3);
                sb.append(a4);
                int i2 = nextSet;
                for (int i3 : iArr) {
                    i2 += i3;
                }
                int nextSet2 = bitArray.getNextSet(i2);
                if (a4 == '*') {
                    sb.setLength(sb.length() - 1);
                    int i4 = 0;
                    for (int i5 : iArr) {
                        i4 += i5;
                    }
                    int i6 = (nextSet2 - nextSet) - i4;
                    if (nextSet2 == size || (i6 << 1) >= i4) {
                        if (this.b) {
                            int length = sb.length() - 1;
                            int i7 = 0;
                            for (int i8 = 0; i8 < length; i8++) {
                                i7 += "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%".indexOf(this.d.charAt(i8));
                            }
                            if (sb.charAt(length) == "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%".charAt(i7 % 43)) {
                                sb.setLength(length);
                            } else {
                                throw ChecksumException.getChecksumInstance();
                            }
                        }
                        if (sb.length() != 0) {
                            if (this.c) {
                                str = a(sb);
                            } else {
                                str = sb.toString();
                            }
                            float f = i;
                            return new Result(str, null, new ResultPoint[]{new ResultPoint((a2[1] + a2[0]) / 2.0f, f), new ResultPoint(nextSet + (i4 / 2.0f), f)}, BarcodeFormat.CODE_39);
                        }
                        throw NotFoundException.getNotFoundInstance();
                    }
                    throw NotFoundException.getNotFoundInstance();
                }
                nextSet = nextSet2;
            } else {
                throw NotFoundException.getNotFoundInstance();
            }
        }
    }

    private static int[] a(BitArray bitArray, int[] iArr) throws NotFoundException {
        int size = bitArray.getSize();
        int nextSet = bitArray.getNextSet(0);
        int length = iArr.length;
        boolean z = false;
        int i = 0;
        int i2 = nextSet;
        while (nextSet < size) {
            if (bitArray.get(nextSet) != z) {
                iArr[i] = iArr[i] + 1;
            } else {
                if (i != length - 1) {
                    i++;
                } else if (a(iArr) == 148 && bitArray.isRange(Math.max(0, i2 - ((nextSet - i2) / 2)), i2, false)) {
                    return new int[]{i2, nextSet};
                } else {
                    i2 += iArr[0] + iArr[1];
                    int i3 = i - 1;
                    System.arraycopy(iArr, 2, iArr, 0, i3);
                    iArr[i3] = 0;
                    iArr[i] = 0;
                    i--;
                }
                iArr[i] = 1;
                z = !z;
            }
            nextSet++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0030, code lost:
        if (r1 >= r0) goto L_0x0042;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0032, code lost:
        if (r3 <= 0) goto L_0x0042;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0034, code lost:
        r2 = r10[r1];
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0036, code lost:
        if (r2 <= r5) goto L_0x003f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0038, code lost:
        r3 = r3 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x003c, code lost:
        if ((r2 << 1) < r6) goto L_0x003f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x003e, code lost:
        return -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x003f, code lost:
        r1 = r1 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0042, code lost:
        return r4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int a(int[] r10) {
        /*
            int r0 = r10.length
            r1 = 0
            r2 = r1
        L_0x0003:
            r3 = 2147483647(0x7fffffff, float:NaN)
            int r4 = r10.length
            r5 = r3
            r3 = r1
        L_0x0009:
            if (r3 >= r4) goto L_0x0015
            r6 = r10[r3]
            if (r6 >= r5) goto L_0x0012
            if (r6 <= r2) goto L_0x0012
            r5 = r6
        L_0x0012:
            int r3 = r3 + 1
            goto L_0x0009
        L_0x0015:
            r2 = r1
            r3 = r2
            r4 = r3
            r6 = r4
        L_0x0019:
            if (r2 >= r0) goto L_0x002c
            r7 = r10[r2]
            if (r7 <= r5) goto L_0x0029
            int r8 = r0 + (-1)
            int r8 = r8 - r2
            r9 = 1
            int r8 = r9 << r8
            r4 = r4 | r8
            int r3 = r3 + 1
            int r6 = r6 + r7
        L_0x0029:
            int r2 = r2 + 1
            goto L_0x0019
        L_0x002c:
            r2 = 3
            r7 = -1
            if (r3 != r2) goto L_0x0043
        L_0x0030:
            if (r1 >= r0) goto L_0x0042
            if (r3 <= 0) goto L_0x0042
            r2 = r10[r1]
            if (r2 <= r5) goto L_0x003f
            int r3 = r3 + (-1)
            int r2 = r2 << 1
            if (r2 < r6) goto L_0x003f
            return r7
        L_0x003f:
            int r1 = r1 + 1
            goto L_0x0030
        L_0x0042:
            return r4
        L_0x0043:
            if (r3 > r2) goto L_0x0046
            return r7
        L_0x0046:
            r2 = r5
            goto L_0x0003
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.Code39Reader.a(int[]):int");
    }

    private static char a(int i) throws NotFoundException {
        int i2 = 0;
        while (true) {
            int[] iArr = a;
            if (i2 < iArr.length) {
                if (iArr[i2] == i) {
                    return "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%".charAt(i2);
                }
                i2++;
            } else if (i == 148) {
                return '*';
            } else {
                throw NotFoundException.getNotFoundInstance();
            }
        }
    }

    private static String a(CharSequence charSequence) throws FormatException {
        char c;
        int length = charSequence.length();
        StringBuilder sb = new StringBuilder(length);
        int i = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt == '+' || charAt == '$' || charAt == '%' || charAt == '/') {
                i++;
                char charAt2 = charSequence.charAt(i);
                if (charAt != '+') {
                    if (charAt != '/') {
                        switch (charAt) {
                            case '$':
                                if (charAt2 >= 'A' && charAt2 <= 'Z') {
                                    c = (char) (charAt2 - '@');
                                    break;
                                } else {
                                    throw FormatException.getFormatInstance();
                                }
                                break;
                            case '%':
                                if (charAt2 < 'A' || charAt2 > 'E') {
                                    if (charAt2 < 'F' || charAt2 > 'J') {
                                        if (charAt2 < 'K' || charAt2 > 'O') {
                                            if (charAt2 >= 'P' && charAt2 <= 'T') {
                                                c = (char) (charAt2 + '+');
                                                break;
                                            } else if (charAt2 == 'U') {
                                                c = 0;
                                                break;
                                            } else if (charAt2 == 'V') {
                                                c = '@';
                                                break;
                                            } else if (charAt2 == 'W') {
                                                c = '`';
                                                break;
                                            } else if (charAt2 == 'X' || charAt2 == 'Y' || charAt2 == 'Z') {
                                                c = Ascii.MAX;
                                                break;
                                            } else {
                                                throw FormatException.getFormatInstance();
                                            }
                                        } else {
                                            c = (char) (charAt2 + 16);
                                            break;
                                        }
                                    } else {
                                        c = (char) (charAt2 - 11);
                                        break;
                                    }
                                } else {
                                    c = (char) (charAt2 - '&');
                                    break;
                                }
                                break;
                            default:
                                c = 0;
                                break;
                        }
                    } else if (charAt2 >= 'A' && charAt2 <= 'O') {
                        c = (char) (charAt2 - ' ');
                    } else if (charAt2 == 'Z') {
                        c = ':';
                    } else {
                        throw FormatException.getFormatInstance();
                    }
                } else if (charAt2 < 'A' || charAt2 > 'Z') {
                    throw FormatException.getFormatInstance();
                } else {
                    c = (char) (charAt2 + ' ');
                }
                sb.append(c);
            } else {
                sb.append(charAt);
            }
            i++;
        }
        return sb.toString();
    }
}
