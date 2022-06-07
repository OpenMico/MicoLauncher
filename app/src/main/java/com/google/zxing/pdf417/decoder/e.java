package com.google.zxing.pdf417.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.pdf417.PDF417ResultMetadata;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;

/* compiled from: DecodedBitStreamParser.java */
/* loaded from: classes2.dex */
final class e {
    private static final char[] a = ";<>@[\\]_`~!\r\t,:\n-.$/\"|*()?{}'".toCharArray();
    private static final char[] b = "0123456789&\r\t,:#-.$/+%*=^".toCharArray();
    private static final BigInteger[] c;

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DecodedBitStreamParser.java */
    /* loaded from: classes2.dex */
    public enum a {
        ALPHA,
        LOWER,
        MIXED,
        PUNCT,
        ALPHA_SHIFT,
        PUNCT_SHIFT
    }

    static {
        BigInteger[] bigIntegerArr = new BigInteger[16];
        c = bigIntegerArr;
        bigIntegerArr[0] = BigInteger.ONE;
        BigInteger valueOf = BigInteger.valueOf(900L);
        c[1] = valueOf;
        int i = 2;
        while (true) {
            BigInteger[] bigIntegerArr2 = c;
            if (i < bigIntegerArr2.length) {
                bigIntegerArr2[i] = bigIntegerArr2[i - 1].multiply(valueOf);
                i++;
            } else {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:17:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.zxing.common.DecoderResult a(int[] r6, java.lang.String r7) throws com.google.zxing.FormatException {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            int r1 = r6.length
            r2 = 1
            int r1 = r1 << r2
            r0.<init>(r1)
            java.nio.charset.Charset r1 = java.nio.charset.StandardCharsets.ISO_8859_1
            r2 = r6[r2]
            com.google.zxing.pdf417.PDF417ResultMetadata r3 = new com.google.zxing.pdf417.PDF417ResultMetadata
            r3.<init>()
            r4 = 2
        L_0x0012:
            r5 = 0
            r5 = r6[r5]
            if (r4 >= r5) goto L_0x006d
            r5 = 913(0x391, float:1.28E-42)
            if (r2 == r5) goto L_0x0058
            switch(r2) {
                case 900: goto L_0x0053;
                case 901: goto L_0x004e;
                case 902: goto L_0x0049;
                default: goto L_0x001e;
            }
        L_0x001e:
            switch(r2) {
                case 922: goto L_0x0044;
                case 923: goto L_0x0044;
                case 924: goto L_0x004e;
                case 925: goto L_0x0041;
                case 926: goto L_0x003e;
                case 927: goto L_0x002d;
                case 928: goto L_0x0028;
                default: goto L_0x0021;
            }
        L_0x0021:
            int r4 = r4 + (-1)
            int r2 = a(r6, r4, r0)
            goto L_0x0060
        L_0x0028:
            int r2 = a(r6, r4, r3)
            goto L_0x0060
        L_0x002d:
            int r2 = r4 + 1
            r1 = r6[r4]
            com.google.zxing.common.CharacterSetECI r1 = com.google.zxing.common.CharacterSetECI.getCharacterSetECIByValue(r1)
            java.lang.String r1 = r1.name()
            java.nio.charset.Charset r1 = java.nio.charset.Charset.forName(r1)
            goto L_0x0060
        L_0x003e:
            int r2 = r4 + 2
            goto L_0x0060
        L_0x0041:
            int r2 = r4 + 1
            goto L_0x0060
        L_0x0044:
            com.google.zxing.FormatException r6 = com.google.zxing.FormatException.getFormatInstance()
            throw r6
        L_0x0049:
            int r2 = b(r6, r4, r0)
            goto L_0x0060
        L_0x004e:
            int r2 = a(r2, r6, r1, r4, r0)
            goto L_0x0060
        L_0x0053:
            int r2 = a(r6, r4, r0)
            goto L_0x0060
        L_0x0058:
            int r2 = r4 + 1
            r4 = r6[r4]
            char r4 = (char) r4
            r0.append(r4)
        L_0x0060:
            int r4 = r6.length
            if (r2 >= r4) goto L_0x0068
            int r4 = r2 + 1
            r2 = r6[r2]
            goto L_0x0012
        L_0x0068:
            com.google.zxing.FormatException r6 = com.google.zxing.FormatException.getFormatInstance()
            throw r6
        L_0x006d:
            int r6 = r0.length()
            if (r6 == 0) goto L_0x0081
            com.google.zxing.common.DecoderResult r6 = new com.google.zxing.common.DecoderResult
            java.lang.String r0 = r0.toString()
            r1 = 0
            r6.<init>(r1, r0, r1, r7)
            r6.setOther(r3)
            return r6
        L_0x0081:
            com.google.zxing.FormatException r6 = com.google.zxing.FormatException.getFormatInstance()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.decoder.e.a(int[], java.lang.String):com.google.zxing.common.DecoderResult");
    }

    private static int a(int[] iArr, int i, PDF417ResultMetadata pDF417ResultMetadata) throws FormatException {
        if (i + 2 <= iArr[0]) {
            int[] iArr2 = new int[2];
            int i2 = i;
            int i3 = 0;
            while (i3 < 2) {
                iArr2[i3] = iArr[i2];
                i3++;
                i2++;
            }
            pDF417ResultMetadata.setSegmentIndex(Integer.parseInt(a(iArr2, 2)));
            StringBuilder sb = new StringBuilder();
            int a2 = a(iArr, i2, sb);
            pDF417ResultMetadata.setFileId(sb.toString());
            switch (iArr[a2]) {
                case 922:
                    pDF417ResultMetadata.setLastSegment(true);
                    return a2 + 1;
                case 923:
                    int i4 = a2 + 1;
                    int[] iArr3 = new int[iArr[0] - i4];
                    boolean z = false;
                    int i5 = 0;
                    while (i4 < iArr[0] && !z) {
                        int i6 = i4 + 1;
                        int i7 = iArr[i4];
                        if (i7 < 900) {
                            i5++;
                            iArr3[i5] = i7;
                            i4 = i6;
                        } else if (i7 == 922) {
                            pDF417ResultMetadata.setLastSegment(true);
                            i4 = i6 + 1;
                            z = true;
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                    }
                    pDF417ResultMetadata.setOptionalData(Arrays.copyOf(iArr3, i5));
                    return i4;
                default:
                    return a2;
            }
        } else {
            throw FormatException.getFormatInstance();
        }
    }

    private static int a(int[] iArr, int i, StringBuilder sb) {
        int[] iArr2 = new int[(iArr[0] - i) << 1];
        int[] iArr3 = new int[(iArr[0] - i) << 1];
        boolean z = false;
        int i2 = 0;
        while (i < iArr[0] && !z) {
            int i3 = i + 1;
            int i4 = iArr[i];
            if (i4 < 900) {
                iArr2[i2] = i4 / 30;
                iArr2[i2 + 1] = i4 % 30;
                i2 += 2;
                i = i3;
            } else if (i4 != 913) {
                if (i4 != 928) {
                    switch (i4) {
                        case 900:
                            i2++;
                            iArr2[i2] = 900;
                            i = i3;
                            break;
                        case 901:
                        case 902:
                            break;
                        default:
                            switch (i4) {
                                case 922:
                                case 923:
                                case 924:
                                    break;
                                default:
                                    i = i3;
                                    break;
                            }
                    }
                }
                i = i3 - 1;
                z = true;
            } else {
                iArr2[i2] = 913;
                i = i3 + 1;
                iArr3[i2] = iArr[i3];
                i2++;
            }
        }
        a(iArr2, iArr3, i2, sb);
        return i;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static void a(int[] iArr, int[] iArr2, int i, StringBuilder sb) {
        char c2;
        a aVar = a.ALPHA;
        a aVar2 = a.ALPHA;
        a aVar3 = aVar;
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = iArr[i2];
            switch (aVar3) {
                case ALPHA:
                    if (i3 < 26) {
                        c2 = (char) (i3 + 65);
                        break;
                    } else {
                        if (i3 != 900) {
                            if (i3 != 913) {
                                switch (i3) {
                                    case 26:
                                        c2 = ' ';
                                        break;
                                    case 27:
                                        aVar3 = a.LOWER;
                                        c2 = 0;
                                        break;
                                    case 28:
                                        aVar3 = a.MIXED;
                                        c2 = 0;
                                        break;
                                    case 29:
                                        c2 = 0;
                                        aVar2 = aVar3;
                                        aVar3 = a.PUNCT_SHIFT;
                                        break;
                                }
                            } else {
                                sb.append((char) iArr2[i2]);
                                c2 = 0;
                                break;
                            }
                        } else {
                            aVar3 = a.ALPHA;
                        }
                        c2 = 0;
                        break;
                    }
                case LOWER:
                    if (i3 < 26) {
                        c2 = (char) (i3 + 97);
                        break;
                    } else {
                        if (i3 != 900) {
                            if (i3 != 913) {
                                switch (i3) {
                                    case 26:
                                        c2 = ' ';
                                        break;
                                    case 27:
                                        c2 = 0;
                                        aVar2 = aVar3;
                                        aVar3 = a.ALPHA_SHIFT;
                                        break;
                                    case 28:
                                        aVar3 = a.MIXED;
                                        c2 = 0;
                                        break;
                                    case 29:
                                        c2 = 0;
                                        aVar2 = aVar3;
                                        aVar3 = a.PUNCT_SHIFT;
                                        break;
                                }
                            } else {
                                sb.append((char) iArr2[i2]);
                                c2 = 0;
                                break;
                            }
                        } else {
                            aVar3 = a.ALPHA;
                        }
                        c2 = 0;
                        break;
                    }
                case MIXED:
                    if (i3 < 25) {
                        c2 = b[i3];
                        break;
                    } else {
                        if (i3 != 900) {
                            if (i3 != 913) {
                                switch (i3) {
                                    case 25:
                                        aVar3 = a.PUNCT;
                                        c2 = 0;
                                        break;
                                    case 26:
                                        c2 = ' ';
                                        break;
                                    case 27:
                                        aVar3 = a.LOWER;
                                        c2 = 0;
                                        break;
                                    case 28:
                                        aVar3 = a.ALPHA;
                                        c2 = 0;
                                        break;
                                    case 29:
                                        c2 = 0;
                                        aVar2 = aVar3;
                                        aVar3 = a.PUNCT_SHIFT;
                                        break;
                                }
                            } else {
                                sb.append((char) iArr2[i2]);
                                c2 = 0;
                                break;
                            }
                        } else {
                            aVar3 = a.ALPHA;
                        }
                        c2 = 0;
                        break;
                    }
                case PUNCT:
                    if (i3 >= 29) {
                        if (i3 != 29) {
                            if (i3 != 900) {
                                if (i3 == 913) {
                                    sb.append((char) iArr2[i2]);
                                    c2 = 0;
                                    break;
                                }
                            } else {
                                aVar3 = a.ALPHA;
                            }
                            c2 = 0;
                            break;
                        } else {
                            aVar3 = a.ALPHA;
                            c2 = 0;
                            break;
                        }
                    } else {
                        c2 = a[i3];
                        break;
                    }
                case ALPHA_SHIFT:
                    if (i3 >= 26) {
                        if (i3 != 26) {
                            aVar3 = i3 != 900 ? aVar2 : a.ALPHA;
                            c2 = 0;
                            break;
                        } else {
                            aVar3 = aVar2;
                            c2 = ' ';
                            break;
                        }
                    } else {
                        c2 = (char) (i3 + 65);
                        aVar3 = aVar2;
                        break;
                    }
                case PUNCT_SHIFT:
                    if (i3 >= 29) {
                        if (i3 != 29) {
                            if (i3 != 900) {
                                if (i3 == 913) {
                                    sb.append((char) iArr2[i2]);
                                }
                                c2 = 0;
                                aVar3 = aVar2;
                                break;
                            } else {
                                aVar3 = a.ALPHA;
                                c2 = 0;
                                break;
                            }
                        } else {
                            aVar3 = a.ALPHA;
                            c2 = 0;
                            break;
                        }
                    } else {
                        c2 = a[i3];
                        aVar3 = aVar2;
                        break;
                    }
                default:
                    c2 = 0;
                    break;
            }
            if (c2 != 0) {
                sb.append(c2);
            }
        }
    }

    private static int a(int i, int[] iArr, Charset charset, int i2, StringBuilder sb) {
        int i3;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        long j = 900;
        int i4 = 6;
        if (i == 901) {
            int[] iArr2 = new int[6];
            int i5 = i2 + 1;
            int i6 = iArr[i2];
            boolean z = false;
            int i7 = 0;
            long j2 = 0;
            while (i5 < iArr[0] && !z) {
                int i8 = i7 + 1;
                iArr2[i7] = i6;
                j2 = (j2 * j) + i6;
                int i9 = i5 + 1;
                i6 = iArr[i5];
                if (i6 != 928) {
                    switch (i6) {
                        case 900:
                        case 901:
                        case 902:
                            break;
                        default:
                            switch (i6) {
                                case 922:
                                case 923:
                                case 924:
                                    break;
                                default:
                                    if (i8 % 5 != 0 || i8 <= 0) {
                                        z = z;
                                        i5 = i9;
                                        i7 = i8;
                                        j = 900;
                                        i4 = 6;
                                    } else {
                                        int i10 = 0;
                                        while (i10 < i4) {
                                            byteArrayOutputStream.write((byte) (j2 >> ((5 - i10) * 8)));
                                            i10++;
                                            i4 = 6;
                                            z = z;
                                        }
                                        i5 = i9;
                                        i7 = 0;
                                        j = 900;
                                        j2 = 0;
                                        continue;
                                        continue;
                                    }
                                    break;
                            }
                    }
                }
                i5 = i9 - 1;
                i7 = i8;
                j = 900;
                i4 = 6;
                z = true;
            }
            if (i5 == iArr[0] && i6 < 900) {
                i7++;
                iArr2[i7] = i6;
            }
            for (int i11 = 0; i11 < i7; i11++) {
                byteArrayOutputStream.write((byte) iArr2[i11]);
            }
            i3 = i5;
        } else if (i != 924) {
            i3 = i2;
        } else {
            i3 = i2;
            boolean z2 = false;
            int i12 = 0;
            long j3 = 0;
            while (i3 < iArr[0] && !z2) {
                int i13 = i3 + 1;
                int i14 = iArr[i3];
                if (i14 < 900) {
                    i12++;
                    j3 = (j3 * 900) + i14;
                    i3 = i13;
                } else {
                    if (i14 != 928) {
                        switch (i14) {
                            default:
                                switch (i14) {
                                    case 922:
                                    case 923:
                                    case 924:
                                        break;
                                    default:
                                        i3 = i13;
                                        break;
                                }
                            case 900:
                            case 901:
                            case 902:
                                i3 = i13 - 1;
                                z2 = true;
                                break;
                        }
                    }
                    i3 = i13 - 1;
                    z2 = true;
                }
                if (i12 % 5 == 0 && i12 > 0) {
                    for (int i15 = 0; i15 < 6; i15++) {
                        byteArrayOutputStream.write((byte) (j3 >> ((5 - i15) * 8)));
                    }
                    i12 = 0;
                    j3 = 0;
                }
            }
        }
        sb.append(new String(byteArrayOutputStream.toByteArray(), charset));
        return i3;
    }

    private static int b(int[] iArr, int i, StringBuilder sb) throws FormatException {
        int[] iArr2 = new int[15];
        boolean z = false;
        int i2 = 0;
        while (i < iArr[0] && !z) {
            i++;
            int i3 = iArr[i];
            if (i == iArr[0]) {
                z = true;
            }
            if (i3 < 900) {
                iArr2[i2] = i3;
                i2++;
            } else {
                if (i3 != 928) {
                    switch (i3) {
                        default:
                            switch (i3) {
                            }
                        case 900:
                        case 901:
                            i--;
                            z = true;
                            break;
                    }
                }
                i--;
                z = true;
            }
            if ((i2 % 15 == 0 || i3 == 902 || z) && i2 > 0) {
                sb.append(a(iArr2, i2));
                i2 = 0;
            }
        }
        return i;
    }

    private static String a(int[] iArr, int i) throws FormatException {
        BigInteger bigInteger = BigInteger.ZERO;
        for (int i2 = 0; i2 < i; i2++) {
            bigInteger = bigInteger.add(c[(i - i2) - 1].multiply(BigInteger.valueOf(iArr[i2])));
        }
        String bigInteger2 = bigInteger.toString();
        if (bigInteger2.charAt(0) == '1') {
            return bigInteger2.substring(1);
        }
        throw FormatException.getFormatInstance();
    }
}
