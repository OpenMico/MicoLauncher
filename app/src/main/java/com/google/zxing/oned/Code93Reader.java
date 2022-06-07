package com.google.zxing.oned;

import com.google.common.base.Ascii;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.rd.animation.type.BaseAnimation;
import java.util.Arrays;
import java.util.Map;
import okhttp3.internal.http.StatusLine;
import org.eclipse.jetty.http.HttpStatus;

/* loaded from: classes2.dex */
public final class Code93Reader extends OneDReader {
    static final int[] a;
    private static final char[] b = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".toCharArray();
    private static final int c;
    private final StringBuilder d = new StringBuilder(20);
    private final int[] e = new int[6];

    static {
        int[] iArr = {276, 328, 324, 322, 296, 292, 290, 336, 274, 266, HttpStatus.FAILED_DEPENDENCY_424, 420, 418, 404, 402, 394, 360, 356, 354, StatusLine.HTTP_PERM_REDIRECT, 282, 344, 332, 326, 300, 278, 436, 434, 428, HttpStatus.UNPROCESSABLE_ENTITY_422, 406, 410, 364, 358, 310, 314, 302, 468, 466, 458, 366, 374, 430, 294, 474, 470, 306, BaseAnimation.DEFAULT_ANIMATION_TIME};
        a = iArr;
        c = iArr[47];
    }

    @Override // com.google.zxing.oned.OneDReader
    public Result decodeRow(int i, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        int[] a2 = a(bitArray);
        int nextSet = bitArray.getNextSet(a2[1]);
        int size = bitArray.getSize();
        int[] iArr = this.e;
        Arrays.fill(iArr, 0);
        StringBuilder sb = this.d;
        sb.setLength(0);
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
                    sb.deleteCharAt(sb.length() - 1);
                    int i4 = 0;
                    for (int i5 : iArr) {
                        i4 += i5;
                    }
                    if (nextSet2 == size || !bitArray.get(nextSet2)) {
                        throw NotFoundException.getNotFoundInstance();
                    } else if (sb.length() >= 2) {
                        b(sb);
                        sb.setLength(sb.length() - 2);
                        float f = i;
                        return new Result(a(sb), null, new ResultPoint[]{new ResultPoint((a2[1] + a2[0]) / 2.0f, f), new ResultPoint(nextSet + (i4 / 2.0f), f)}, BarcodeFormat.CODE_93);
                    } else {
                        throw NotFoundException.getNotFoundInstance();
                    }
                } else {
                    nextSet = nextSet2;
                }
            } else {
                throw NotFoundException.getNotFoundInstance();
            }
        }
    }

    private int[] a(BitArray bitArray) throws NotFoundException {
        int size = bitArray.getSize();
        int nextSet = bitArray.getNextSet(0);
        Arrays.fill(this.e, 0);
        int[] iArr = this.e;
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
                } else if (a(iArr) == c) {
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

    private static int a(int[] iArr) {
        int i = 0;
        for (int i2 : iArr) {
            i += i2;
        }
        int length = iArr.length;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            int round = Math.round((iArr[i4] * 9.0f) / i);
            if (round <= 0 || round > 4) {
                return -1;
            }
            if ((i4 & 1) == 0) {
                int i5 = i3;
                for (int i6 = 0; i6 < round; i6++) {
                    i5 = (i5 << 1) | 1;
                }
                i3 = i5;
            } else {
                i3 <<= round;
            }
        }
        return i3;
    }

    private static char a(int i) throws NotFoundException {
        int i2 = 0;
        while (true) {
            int[] iArr = a;
            if (i2 >= iArr.length) {
                throw NotFoundException.getNotFoundInstance();
            } else if (iArr[i2] == i) {
                return b[i2];
            } else {
                i2++;
            }
        }
    }

    private static String a(CharSequence charSequence) throws FormatException {
        char c2;
        int length = charSequence.length();
        StringBuilder sb = new StringBuilder(length);
        int i = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt < 'a' || charAt > 'd') {
                sb.append(charAt);
            } else if (i < length - 1) {
                i++;
                char charAt2 = charSequence.charAt(i);
                switch (charAt) {
                    case 'a':
                        if (charAt2 >= 'A' && charAt2 <= 'Z') {
                            c2 = (char) (charAt2 - '@');
                            break;
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                        break;
                    case 'b':
                        if (charAt2 < 'A' || charAt2 > 'E') {
                            if (charAt2 < 'F' || charAt2 > 'J') {
                                if (charAt2 < 'K' || charAt2 > 'O') {
                                    if (charAt2 < 'P' || charAt2 > 'S') {
                                        if (charAt2 >= 'T' && charAt2 <= 'Z') {
                                            c2 = Ascii.MAX;
                                            break;
                                        } else {
                                            throw FormatException.getFormatInstance();
                                        }
                                    } else {
                                        c2 = (char) (charAt2 + '+');
                                        break;
                                    }
                                } else {
                                    c2 = (char) (charAt2 + 16);
                                    break;
                                }
                            } else {
                                c2 = (char) (charAt2 - 11);
                                break;
                            }
                        } else {
                            c2 = (char) (charAt2 - '&');
                            break;
                        }
                        break;
                    case 'c':
                        if (charAt2 >= 'A' && charAt2 <= 'O') {
                            c2 = (char) (charAt2 - ' ');
                            break;
                        } else if (charAt2 == 'Z') {
                            c2 = ':';
                            break;
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                    case 'd':
                        if (charAt2 >= 'A' && charAt2 <= 'Z') {
                            c2 = (char) (charAt2 + ' ');
                            break;
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                    default:
                        c2 = 0;
                        break;
                }
                sb.append(c2);
            } else {
                throw FormatException.getFormatInstance();
            }
            i++;
        }
        return sb.toString();
    }

    private static void b(CharSequence charSequence) throws ChecksumException {
        int length = charSequence.length();
        a(charSequence, length - 2, 20);
        a(charSequence, length - 1, 15);
    }

    private static void a(CharSequence charSequence, int i, int i2) throws ChecksumException {
        int i3 = 0;
        int i4 = 1;
        for (int i5 = i - 1; i5 >= 0; i5--) {
            i3 += "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".indexOf(charSequence.charAt(i5)) * i4;
            i4++;
            if (i4 > i2) {
                i4 = 1;
            }
        }
        if (charSequence.charAt(i) != b[i3 % 47]) {
            throw ChecksumException.getChecksumInstance();
        }
    }
}
