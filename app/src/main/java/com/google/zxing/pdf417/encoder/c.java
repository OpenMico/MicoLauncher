package com.google.zxing.pdf417.encoder;

import com.alibaba.fastjson.parser.JSONLexer;
import com.google.common.primitives.SignedBytes;
import com.google.zxing.WriterException;
import com.google.zxing.common.CharacterSetECI;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import okio.Utf8;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PDF417HighLevelEncoder.java */
/* loaded from: classes2.dex */
public final class c {
    private static final byte[] a = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 38, 13, 9, HttpConstants.COMMA, 58, BinaryMemcacheOpcodes.GATK, 45, 46, BinaryMemcacheOpcodes.GATKQ, 47, 43, 37, 42, HttpConstants.EQUALS, 94, 0, 32, 0, 0, 0};
    private static final byte[] b = {59, 60, 62, SignedBytes.MAX_POWER_OF_TWO, 91, 92, 93, 95, 96, 126, BinaryMemcacheOpcodes.SASL_AUTH, 13, 9, HttpConstants.COMMA, 58, 10, 45, 46, BinaryMemcacheOpcodes.GATKQ, 47, 34, 124, 42, 40, 41, Utf8.REPLACEMENT_BYTE, 123, 125, 39, 0};
    private static final byte[] c = new byte[128];
    private static final byte[] d = new byte[128];
    private static final Charset e = StandardCharsets.ISO_8859_1;

    private static boolean a(char c2) {
        return c2 >= '0' && c2 <= '9';
    }

    private static boolean b(char c2) {
        if (c2 != ' ') {
            return c2 >= 'A' && c2 <= 'Z';
        }
        return true;
    }

    private static boolean c(char c2) {
        if (c2 != ' ') {
            return c2 >= 'a' && c2 <= 'z';
        }
        return true;
    }

    private static boolean f(char c2) {
        if (c2 == '\t' || c2 == '\n' || c2 == '\r') {
            return true;
        }
        return c2 >= ' ' && c2 <= '~';
    }

    static {
        Arrays.fill(c, (byte) -1);
        int i = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr = a;
            if (i2 >= bArr.length) {
                break;
            }
            byte b2 = bArr[i2];
            if (b2 > 0) {
                c[b2] = (byte) i2;
            }
            i2++;
        }
        Arrays.fill(d, (byte) -1);
        while (true) {
            byte[] bArr2 = b;
            if (i < bArr2.length) {
                byte b3 = bArr2[i];
                if (b3 > 0) {
                    d[b3] = (byte) i;
                }
                i++;
            } else {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(String str, Compaction compaction, Charset charset) throws WriterException {
        CharacterSetECI characterSetECIByName;
        StringBuilder sb = new StringBuilder(str.length());
        if (charset == null) {
            charset = e;
        } else if (!e.equals(charset) && (characterSetECIByName = CharacterSetECI.getCharacterSetECIByName(charset.name())) != null) {
            a(characterSetECIByName.getValue(), sb);
        }
        int length = str.length();
        switch (compaction) {
            case TEXT:
                a(str, 0, length, sb, 0);
                break;
            case BYTE:
                byte[] bytes = str.getBytes(charset);
                a(bytes, 0, bytes.length, 1, sb);
                break;
            case NUMERIC:
                sb.append((char) 902);
                a(str, 0, length, sb);
                break;
            default:
                int i = 0;
                int i2 = 0;
                int i3 = 0;
                while (i < length) {
                    int a2 = a(str, i);
                    if (a2 >= 13) {
                        sb.append((char) 902);
                        i3 = 2;
                        a(str, i, a2, sb);
                        i += a2;
                        i2 = 0;
                    } else {
                        int b2 = b(str, i);
                        if (b2 >= 5 || a2 == length) {
                            if (i3 != 0) {
                                sb.append((char) 900);
                                i2 = 0;
                                i3 = 0;
                            }
                            i2 = a(str, i, b2, sb, i2);
                            i += b2;
                        } else {
                            int a3 = a(str, i, charset);
                            if (a3 == 0) {
                                a3 = 1;
                            }
                            int i4 = a3 + i;
                            byte[] bytes2 = str.substring(i, i4).getBytes(charset);
                            if (bytes2.length == 1 && i3 == 0) {
                                a(bytes2, 0, 1, 0, sb);
                            } else {
                                a(bytes2, 0, bytes2.length, i3, sb);
                                i3 = 1;
                                i2 = 0;
                            }
                            i = i4;
                        }
                    }
                }
                break;
        }
        return sb.toString();
    }

    private static int a(CharSequence charSequence, int i, int i2, StringBuilder sb, int i3) {
        StringBuilder sb2 = new StringBuilder(i2);
        int i4 = i3;
        int i5 = 0;
        while (true) {
            int i6 = i + i5;
            char charAt = charSequence.charAt(i6);
            switch (i4) {
                case 0:
                    if (b(charAt)) {
                        if (charAt == ' ') {
                            sb2.append(JSONLexer.EOI);
                            break;
                        } else {
                            sb2.append((char) (charAt - 'A'));
                            break;
                        }
                    } else if (c(charAt)) {
                        sb2.append((char) 27);
                        i4 = 1;
                        continue;
                    } else if (!d(charAt)) {
                        sb2.append((char) 29);
                        sb2.append((char) d[charAt]);
                        break;
                    } else {
                        sb2.append((char) 28);
                        i4 = 2;
                    }
                case 1:
                    if (c(charAt)) {
                        if (charAt == ' ') {
                            sb2.append(JSONLexer.EOI);
                            break;
                        } else {
                            sb2.append((char) (charAt - 'a'));
                            break;
                        }
                    } else if (b(charAt)) {
                        sb2.append((char) 27);
                        sb2.append((char) (charAt - 'A'));
                        break;
                    } else if (!d(charAt)) {
                        sb2.append((char) 29);
                        sb2.append((char) d[charAt]);
                        break;
                    } else {
                        sb2.append((char) 28);
                        i4 = 2;
                        continue;
                    }
                case 2:
                    if (d(charAt)) {
                        sb2.append((char) c[charAt]);
                        break;
                    } else if (b(charAt)) {
                        sb2.append((char) 28);
                        i4 = 0;
                        continue;
                    } else if (c(charAt)) {
                        sb2.append((char) 27);
                        i4 = 1;
                    } else {
                        int i7 = i6 + 1;
                        if (i7 >= i2 || !e(charSequence.charAt(i7))) {
                            sb2.append((char) 29);
                            sb2.append((char) d[charAt]);
                            break;
                        } else {
                            i4 = 3;
                            sb2.append((char) 25);
                        }
                    }
                    break;
                default:
                    if (e(charAt)) {
                        sb2.append((char) d[charAt]);
                        break;
                    } else {
                        sb2.append((char) 29);
                        i4 = 0;
                        continue;
                    }
            }
            i5++;
            if (i5 >= i2) {
                int length = sb2.length();
                char c2 = 0;
                for (int i8 = 0; i8 < length; i8++) {
                    if (i8 % 2 != 0) {
                        c2 = (char) ((c2 * 30) + sb2.charAt(i8));
                        sb.append(c2);
                    } else {
                        c2 = sb2.charAt(i8);
                    }
                }
                if (length % 2 != 0) {
                    sb.append((char) ((c2 * 30) + 29));
                }
                return i4;
            }
        }
    }

    private static void a(byte[] bArr, int i, int i2, int i3, StringBuilder sb) {
        int i4;
        if (i2 == 1 && i3 == 0) {
            sb.append((char) 913);
        } else if (i2 % 6 == 0) {
            sb.append((char) 924);
        } else {
            sb.append((char) 901);
        }
        if (i2 >= 6) {
            char[] cArr = new char[5];
            i4 = i;
            while ((i + i2) - i4 >= 6) {
                long j = 0;
                for (int i5 = 0; i5 < 6; i5++) {
                    j = (j << 8) + (bArr[i4 + i5] & 255);
                }
                for (int i6 = 0; i6 < 5; i6++) {
                    cArr[i6] = (char) (j % 900);
                    j /= 900;
                }
                for (int i7 = 4; i7 >= 0; i7--) {
                    sb.append(cArr[i7]);
                }
                i4 += 6;
            }
        } else {
            i4 = i;
        }
        while (i4 < i + i2) {
            sb.append((char) (bArr[i4] & 255));
            i4++;
        }
    }

    private static void a(String str, int i, int i2, StringBuilder sb) {
        StringBuilder sb2 = new StringBuilder((i2 / 3) + 1);
        BigInteger valueOf = BigInteger.valueOf(900L);
        BigInteger valueOf2 = BigInteger.valueOf(0L);
        int i3 = 0;
        while (i3 < i2) {
            sb2.setLength(0);
            int min = Math.min(44, i2 - i3);
            StringBuilder sb3 = new StringBuilder("1");
            int i4 = i + i3;
            sb3.append(str.substring(i4, i4 + min));
            BigInteger bigInteger = new BigInteger(sb3.toString());
            do {
                sb2.append((char) bigInteger.mod(valueOf).intValue());
                bigInteger = bigInteger.divide(valueOf);
            } while (!bigInteger.equals(valueOf2));
            for (int length = sb2.length() - 1; length >= 0; length--) {
                sb.append(sb2.charAt(length));
            }
            i3 += min;
        }
    }

    private static boolean d(char c2) {
        return c[c2] != -1;
    }

    private static boolean e(char c2) {
        return d[c2] != -1;
    }

    private static int a(CharSequence charSequence, int i) {
        int length = charSequence.length();
        int i2 = 0;
        if (i < length) {
            char charAt = charSequence.charAt(i);
            while (a(charAt) && i < length) {
                i2++;
                i++;
                if (i < length) {
                    charAt = charSequence.charAt(i);
                }
            }
        }
        return i2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0027, code lost:
        return (r1 - r7) - r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int b(java.lang.CharSequence r6, int r7) {
        /*
            int r0 = r6.length()
            r1 = r7
        L_0x0005:
            if (r1 >= r0) goto L_0x0037
            char r2 = r6.charAt(r1)
            r3 = 0
        L_0x000c:
            r4 = 13
            if (r3 >= r4) goto L_0x0023
            boolean r5 = a(r2)
            if (r5 == 0) goto L_0x0023
            if (r1 >= r0) goto L_0x0023
            int r3 = r3 + 1
            int r1 = r1 + 1
            if (r1 >= r0) goto L_0x000c
            char r2 = r6.charAt(r1)
            goto L_0x000c
        L_0x0023:
            if (r3 < r4) goto L_0x0028
            int r1 = r1 - r7
            int r1 = r1 - r3
            return r1
        L_0x0028:
            if (r3 > 0) goto L_0x0005
            char r2 = r6.charAt(r1)
            boolean r2 = f(r2)
            if (r2 == 0) goto L_0x0037
            int r1 = r1 + 1
            goto L_0x0005
        L_0x0037:
            int r1 = r1 - r7
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.encoder.c.b(java.lang.CharSequence, int):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0028, code lost:
        return r1 - r6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int a(java.lang.String r5, int r6, java.nio.charset.Charset r7) throws com.google.zxing.WriterException {
        /*
            java.nio.charset.CharsetEncoder r7 = r7.newEncoder()
            int r0 = r5.length()
            r1 = r6
        L_0x0009:
            if (r1 >= r0) goto L_0x0057
            char r2 = r5.charAt(r1)
            r3 = 0
        L_0x0010:
            r4 = 13
            if (r3 >= r4) goto L_0x0025
            boolean r2 = a(r2)
            if (r2 == 0) goto L_0x0025
            int r3 = r3 + 1
            int r2 = r1 + r3
            if (r2 >= r0) goto L_0x0025
            char r2 = r5.charAt(r2)
            goto L_0x0010
        L_0x0025:
            if (r3 < r4) goto L_0x0029
            int r1 = r1 - r6
            return r1
        L_0x0029:
            char r2 = r5.charAt(r1)
            boolean r3 = r7.canEncode(r2)
            if (r3 == 0) goto L_0x0036
            int r1 = r1 + 1
            goto L_0x0009
        L_0x0036:
            com.google.zxing.WriterException r5 = new com.google.zxing.WriterException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "Non-encodable character detected: "
            r6.<init>(r7)
            r6.append(r2)
            java.lang.String r7 = " (Unicode: "
            r6.append(r7)
            r6.append(r2)
            r7 = 41
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            throw r5
        L_0x0057:
            int r1 = r1 - r6
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.encoder.c.a(java.lang.String, int, java.nio.charset.Charset):int");
    }

    private static void a(int i, StringBuilder sb) throws WriterException {
        if (i >= 0 && i < 900) {
            sb.append((char) 927);
            sb.append((char) i);
        } else if (i < 810900) {
            sb.append((char) 926);
            sb.append((char) ((i / 900) - 1));
            sb.append((char) (i % 900));
        } else if (i < 811800) {
            sb.append((char) 925);
            sb.append((char) (810900 - i));
        } else {
            throw new WriterException("ECI number not in valid range from 0..811799, but was " + i);
        }
    }
}
