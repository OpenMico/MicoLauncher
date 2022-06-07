package com.google.zxing.datamatrix.decoder;

import com.alibaba.fastjson.asm.Opcodes;
import com.fasterxml.jackson.core.JsonPointer;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.common.base.Ascii;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitSource;
import io.netty.util.internal.StringUtil;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import kotlin.text.Typography;

/* compiled from: DecodedBitStreamParser.java */
/* loaded from: classes2.dex */
final class c {
    private static final char[] a = {'*', '*', '*', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final char[] b = {'!', '\"', '#', '$', '%', Typography.amp, '\'', '(', ')', '*', '+', StringUtil.COMMA, '-', '.', JsonPointer.SEPARATOR, ':', ';', Typography.less, '=', Typography.greater, '?', '@', '[', '\\', ']', '^', '_'};
    private static final char[] c = {'*', '*', '*', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final char[] d = b;
    private static final char[] e = {'`', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '{', '|', '}', '~', Ascii.MAX};

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DecodedBitStreamParser.java */
    /* loaded from: classes2.dex */
    public enum a {
        PAD_ENCODE,
        ASCII_ENCODE,
        C40_ENCODE,
        TEXT_ENCODE,
        ANSIX12_ENCODE,
        EDIFACT_ENCODE,
        BASE256_ENCODE
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0068  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.zxing.common.DecoderResult a(byte[] r6) throws com.google.zxing.FormatException {
        /*
            com.google.zxing.common.BitSource r0 = new com.google.zxing.common.BitSource
            r0.<init>(r6)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r2 = 100
            r1.<init>(r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r3 = 0
            r2.<init>(r3)
            java.util.ArrayList r3 = new java.util.ArrayList
            r4 = 1
            r3.<init>(r4)
            com.google.zxing.datamatrix.decoder.c$a r4 = com.google.zxing.datamatrix.decoder.c.a.ASCII_ENCODE
        L_0x001a:
            com.google.zxing.datamatrix.decoder.c$a r5 = com.google.zxing.datamatrix.decoder.c.a.ASCII_ENCODE
            if (r4 != r5) goto L_0x0023
            com.google.zxing.datamatrix.decoder.c$a r4 = a(r0, r1, r2)
            goto L_0x0048
        L_0x0023:
            int[] r5 = com.google.zxing.datamatrix.decoder.c.AnonymousClass1.a
            int r4 = r4.ordinal()
            r4 = r5[r4]
            switch(r4) {
                case 1: goto L_0x0043;
                case 2: goto L_0x003f;
                case 3: goto L_0x003b;
                case 4: goto L_0x0037;
                case 5: goto L_0x0033;
                default: goto L_0x002e;
            }
        L_0x002e:
            com.google.zxing.FormatException r6 = com.google.zxing.FormatException.getFormatInstance()
            throw r6
        L_0x0033:
            a(r0, r1, r3)
            goto L_0x0046
        L_0x0037:
            d(r0, r1)
            goto L_0x0046
        L_0x003b:
            c(r0, r1)
            goto L_0x0046
        L_0x003f:
            b(r0, r1)
            goto L_0x0046
        L_0x0043:
            a(r0, r1)
        L_0x0046:
            com.google.zxing.datamatrix.decoder.c$a r4 = com.google.zxing.datamatrix.decoder.c.a.ASCII_ENCODE
        L_0x0048:
            com.google.zxing.datamatrix.decoder.c$a r5 = com.google.zxing.datamatrix.decoder.c.a.PAD_ENCODE
            if (r4 == r5) goto L_0x0052
            int r5 = r0.available()
            if (r5 > 0) goto L_0x001a
        L_0x0052:
            int r0 = r2.length()
            if (r0 <= 0) goto L_0x005b
            r1.append(r2)
        L_0x005b:
            com.google.zxing.common.DecoderResult r0 = new com.google.zxing.common.DecoderResult
            java.lang.String r1 = r1.toString()
            boolean r2 = r3.isEmpty()
            r4 = 0
            if (r2 == 0) goto L_0x0069
            r3 = r4
        L_0x0069:
            r0.<init>(r6, r1, r3, r4)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.datamatrix.decoder.c.a(byte[]):com.google.zxing.common.DecoderResult");
    }

    private static a a(BitSource bitSource, StringBuilder sb, StringBuilder sb2) throws FormatException {
        boolean z = false;
        do {
            int readBits = bitSource.readBits(8);
            if (readBits != 0) {
                if (readBits > 128) {
                    if (readBits != 129) {
                        if (readBits > 229) {
                            switch (readBits) {
                                case 230:
                                    return a.C40_ENCODE;
                                case 231:
                                    return a.BASE256_ENCODE;
                                case 232:
                                    sb.append((char) 29);
                                    break;
                                case 233:
                                case 234:
                                case 241:
                                    break;
                                case 235:
                                    z = true;
                                    break;
                                case 236:
                                    sb.append("[)>\u001e05\u001d");
                                    sb2.insert(0, "\u001e\u0004");
                                    break;
                                case 237:
                                    sb.append("[)>\u001e06\u001d");
                                    sb2.insert(0, "\u001e\u0004");
                                    break;
                                case 238:
                                    return a.ANSIX12_ENCODE;
                                case 239:
                                    return a.TEXT_ENCODE;
                                case PsExtractor.VIDEO_STREAM_MASK /* 240 */:
                                    return a.EDIFACT_ENCODE;
                                default:
                                    if (readBits >= 242 && !(readBits == 254 && bitSource.available() == 0)) {
                                        throw FormatException.getFormatInstance();
                                    }
                                    break;
                            }
                        } else {
                            int i = readBits - 130;
                            if (i < 10) {
                                sb.append('0');
                            }
                            sb.append(i);
                        }
                    } else {
                        return a.PAD_ENCODE;
                    }
                } else {
                    if (z) {
                        readBits += 128;
                    }
                    sb.append((char) (readBits - 1));
                    return a.ASCII_ENCODE;
                }
            } else {
                throw FormatException.getFormatInstance();
            }
        } while (bitSource.available() > 0);
        return a.ASCII_ENCODE;
    }

    private static void a(BitSource bitSource, StringBuilder sb) throws FormatException {
        int readBits;
        int[] iArr = new int[3];
        boolean z = false;
        int i = 0;
        while (bitSource.available() != 8 && (readBits = bitSource.readBits(8)) != 254) {
            a(readBits, bitSource.readBits(8), iArr);
            for (int i2 = 0; i2 < 3; i2++) {
                int i3 = iArr[i2];
                switch (i) {
                    case 0:
                        if (i3 < 3) {
                            i = i3 + 1;
                            break;
                        } else {
                            char[] cArr = a;
                            if (i3 < cArr.length) {
                                char c2 = cArr[i3];
                                if (z) {
                                    sb.append((char) (c2 + 128));
                                    z = false;
                                    break;
                                } else {
                                    sb.append(c2);
                                    break;
                                }
                            } else {
                                throw FormatException.getFormatInstance();
                            }
                        }
                    case 1:
                        if (z) {
                            sb.append((char) (i3 + 128));
                            z = false;
                        } else {
                            sb.append((char) i3);
                        }
                        i = 0;
                        break;
                    case 2:
                        char[] cArr2 = b;
                        if (i3 < cArr2.length) {
                            char c3 = cArr2[i3];
                            if (z) {
                                sb.append((char) (c3 + 128));
                                z = false;
                                i = 0;
                                break;
                            } else {
                                sb.append(c3);
                                z = z;
                                i = 0;
                            }
                        } else if (i3 == 27) {
                            sb.append((char) 29);
                            z = z;
                            i = 0;
                        } else if (i3 == 30) {
                            z = true;
                            i = 0;
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                    case 3:
                        if (z) {
                            sb.append((char) (i3 + 224));
                            z = false;
                        } else {
                            sb.append((char) (i3 + 96));
                        }
                        i = 0;
                        break;
                    default:
                        throw FormatException.getFormatInstance();
                }
            }
            if (bitSource.available() > 0) {
                z = z;
            } else {
                return;
            }
        }
    }

    private static void b(BitSource bitSource, StringBuilder sb) throws FormatException {
        int readBits;
        int[] iArr = new int[3];
        boolean z = false;
        int i = 0;
        while (bitSource.available() != 8 && (readBits = bitSource.readBits(8)) != 254) {
            a(readBits, bitSource.readBits(8), iArr);
            for (int i2 = 0; i2 < 3; i2++) {
                int i3 = iArr[i2];
                switch (i) {
                    case 0:
                        if (i3 < 3) {
                            i = i3 + 1;
                            break;
                        } else {
                            char[] cArr = c;
                            if (i3 < cArr.length) {
                                char c2 = cArr[i3];
                                if (z) {
                                    sb.append((char) (c2 + 128));
                                    z = false;
                                    break;
                                } else {
                                    sb.append(c2);
                                    break;
                                }
                            } else {
                                throw FormatException.getFormatInstance();
                            }
                        }
                    case 1:
                        if (z) {
                            sb.append((char) (i3 + 128));
                            z = false;
                        } else {
                            sb.append((char) i3);
                        }
                        i = 0;
                        break;
                    case 2:
                        char[] cArr2 = d;
                        if (i3 < cArr2.length) {
                            char c3 = cArr2[i3];
                            if (z) {
                                sb.append((char) (c3 + 128));
                                z = false;
                                i = 0;
                                break;
                            } else {
                                sb.append(c3);
                                z = z;
                                i = 0;
                            }
                        } else if (i3 == 27) {
                            sb.append((char) 29);
                            z = z;
                            i = 0;
                        } else if (i3 == 30) {
                            z = true;
                            i = 0;
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                    case 3:
                        char[] cArr3 = e;
                        if (i3 < cArr3.length) {
                            char c4 = cArr3[i3];
                            if (z) {
                                sb.append((char) (c4 + 128));
                                z = false;
                            } else {
                                sb.append(c4);
                            }
                            i = 0;
                            break;
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                    default:
                        throw FormatException.getFormatInstance();
                }
            }
            if (bitSource.available() > 0) {
                z = z;
            } else {
                return;
            }
        }
    }

    private static void c(BitSource bitSource, StringBuilder sb) throws FormatException {
        int readBits;
        int[] iArr = new int[3];
        while (bitSource.available() != 8 && (readBits = bitSource.readBits(8)) != 254) {
            a(readBits, bitSource.readBits(8), iArr);
            for (int i = 0; i < 3; i++) {
                int i2 = iArr[i];
                switch (i2) {
                    case 0:
                        sb.append('\r');
                        break;
                    case 1:
                        sb.append('*');
                        break;
                    case 2:
                        sb.append(Typography.greater);
                        break;
                    case 3:
                        sb.append(' ');
                        break;
                    default:
                        if (i2 < 14) {
                            sb.append((char) (i2 + 44));
                            break;
                        } else if (i2 < 40) {
                            sb.append((char) (i2 + 51));
                            break;
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                }
            }
            if (bitSource.available() <= 0) {
                return;
            }
        }
    }

    private static void a(int i, int i2, int[] iArr) {
        int i3 = ((i << 8) + i2) - 1;
        int i4 = i3 / 1600;
        iArr[0] = i4;
        int i5 = i3 - (i4 * 1600);
        int i6 = i5 / 40;
        iArr[1] = i6;
        iArr[2] = i5 - (i6 * 40);
    }

    private static void d(BitSource bitSource, StringBuilder sb) {
        while (bitSource.available() > 16) {
            for (int i = 0; i < 4; i++) {
                int readBits = bitSource.readBits(6);
                if (readBits == 31) {
                    int bitOffset = 8 - bitSource.getBitOffset();
                    if (bitOffset != 8) {
                        bitSource.readBits(bitOffset);
                        return;
                    }
                    return;
                }
                if ((readBits & 32) == 0) {
                    readBits |= 64;
                }
                sb.append((char) readBits);
            }
            if (bitSource.available() <= 0) {
                return;
            }
        }
    }

    private static void a(BitSource bitSource, StringBuilder sb, Collection<byte[]> collection) throws FormatException {
        int byteOffset = bitSource.getByteOffset() + 1;
        int i = byteOffset + 1;
        int a2 = a(bitSource.readBits(8), byteOffset);
        if (a2 == 0) {
            a2 = bitSource.available() / 8;
        } else if (a2 >= 250) {
            i++;
            a2 = ((a2 - 249) * 250) + a(bitSource.readBits(8), i);
        }
        if (a2 >= 0) {
            byte[] bArr = new byte[a2];
            for (int i2 = 0; i2 < a2; i2++) {
                if (bitSource.available() >= 8) {
                    i++;
                    bArr[i2] = (byte) a(bitSource.readBits(8), i);
                } else {
                    throw FormatException.getFormatInstance();
                }
            }
            collection.add(bArr);
            try {
                sb.append(new String(bArr, "ISO8859_1"));
            } catch (UnsupportedEncodingException e2) {
                throw new IllegalStateException("Platform does not support required encoding: " + e2);
            }
        } else {
            throw FormatException.getFormatInstance();
        }
    }

    private static int a(int i, int i2) {
        int i3 = i - (((i2 * Opcodes.FCMPL) % 255) + 1);
        return i3 >= 0 ? i3 : i3 + 256;
    }
}
