package com.google.zxing.maxicode.decoder;

import com.google.common.base.Ascii;
import com.google.zxing.common.DecoderResult;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;
import java.text.DecimalFormat;

/* compiled from: DecodedBitStreamParser.java */
/* loaded from: classes2.dex */
final class b {
    private static final String[] a = {"\nABCDEFGHIJKLMNOPQRSTUVWXYZ\ufffa\u001c\u001d\u001e\ufffb ￼\"#$%&'()*+,-./0123456789:\ufff1\ufff2\ufff3\ufff4\ufff8", "`abcdefghijklmnopqrstuvwxyz\ufffa\u001c\u001d\u001e\ufffb{￼}~\u007f;<=>?[\\]^_ ,./:@!|￼\ufff5\ufff6￼\ufff0\ufff2\ufff3\ufff4\ufff7", "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚ\ufffa\u001c\u001d\u001eÛÜÝÞßª¬±²³µ¹º¼½¾\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\ufff7 \ufff9\ufff3\ufff4\ufff8", "àáâãäåæçèéêëìíîïðñòóôõö÷øùú\ufffa\u001c\u001d\u001e\ufffbûüýþÿ¡¨«¯°´·¸»¿\u008a\u008b\u008c\u008d\u008e\u008f\u0090\u0091\u0092\u0093\u0094\ufff7 \ufff2\ufff9\ufff4\ufff8", "\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\ufffa￼￼\u001b\ufffb\u001c\u001d\u001e\u001f\u009f ¢£¤¥¦§©\u00ad®¶\u0095\u0096\u0097\u0098\u0099\u009a\u009b\u009c\u009d\u009e\ufff7 \ufff2\ufff3\ufff9\ufff8", "\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f !\"#$%&'()*+,-./0123456789:;<=>?"};

    /* JADX INFO: Access modifiers changed from: package-private */
    public static DecoderResult a(byte[] bArr, int i) {
        String str;
        StringBuilder sb = new StringBuilder(144);
        switch (i) {
            case 2:
            case 3:
                if (i == 2) {
                    str = new DecimalFormat("0000000000".substring(0, c(bArr))).format(d(bArr));
                } else {
                    str = e(bArr);
                }
                DecimalFormat decimalFormat = new DecimalFormat("000");
                String format = decimalFormat.format(a(bArr));
                String format2 = decimalFormat.format(b(bArr));
                sb.append(a(bArr, 10, 84));
                if (!sb.toString().startsWith("[)>\u001e01\u001d")) {
                    sb.insert(0, str + (char) 29 + format + (char) 29 + format2 + (char) 29);
                    break;
                } else {
                    sb.insert(9, str + (char) 29 + format + (char) 29 + format2 + (char) 29);
                    break;
                }
            case 4:
                sb.append(a(bArr, 1, 93));
                break;
            case 5:
                sb.append(a(bArr, 1, 77));
                break;
        }
        return new DecoderResult(bArr, sb.toString(), null, String.valueOf(i));
    }

    private static int a(int i, byte[] bArr) {
        int i2 = i - 1;
        return ((1 << (5 - (i2 % 6))) & bArr[i2 / 6]) == 0 ? 0 : 1;
    }

    private static int a(byte[] bArr, byte[] bArr2) {
        if (bArr2.length != 0) {
            int i = 0;
            for (int i2 = 0; i2 < bArr2.length; i2++) {
                i += a(bArr2[i2], bArr) << ((bArr2.length - i2) - 1);
            }
            return i;
        }
        throw new IllegalArgumentException();
    }

    private static int a(byte[] bArr) {
        return a(bArr, new byte[]{53, 54, 43, HttpConstants.COMMA, 45, 46, 47, 48, 37, 38});
    }

    private static int b(byte[] bArr) {
        return a(bArr, new byte[]{55, 56, 57, 58, 59, 60, 49, 50, 51, 52});
    }

    private static int c(byte[] bArr) {
        return a(bArr, new byte[]{39, 40, 41, 42, Ascii.US, 32});
    }

    private static int d(byte[] bArr) {
        return a(bArr, new byte[]{BinaryMemcacheOpcodes.SASL_AUTH, 34, BinaryMemcacheOpcodes.GATK, BinaryMemcacheOpcodes.GATKQ, 25, 26, Ascii.ESC, 28, 29, 30, 19, 20, 21, 22, 23, 24, 13, 14, 15, 16, 17, 18, 7, 8, 9, 10, 11, 12, 1, 2});
    }

    private static String e(byte[] bArr) {
        return String.valueOf(new char[]{a[0].charAt(a(bArr, new byte[]{39, 40, 41, 42, Ascii.US, 32})), a[0].charAt(a(bArr, new byte[]{BinaryMemcacheOpcodes.SASL_AUTH, 34, BinaryMemcacheOpcodes.GATK, BinaryMemcacheOpcodes.GATKQ, 25, 26})), a[0].charAt(a(bArr, new byte[]{Ascii.ESC, 28, 29, 30, 19, 20})), a[0].charAt(a(bArr, new byte[]{21, 22, 23, 24, 13, 14})), a[0].charAt(a(bArr, new byte[]{15, 16, 17, 18, 7, 8})), a[0].charAt(a(bArr, new byte[]{9, 10, 11, 12, 1, 2}))});
    }

    private static String a(byte[] bArr, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        int i3 = i;
        int i4 = 0;
        int i5 = 0;
        int i6 = -1;
        while (i3 < i + i2) {
            char charAt = a[i4].charAt(bArr[i3]);
            switch (charAt) {
                case 65520:
                case 65521:
                case 65522:
                case 65523:
                case 65524:
                    i4 = charAt - 65520;
                    i5 = i4;
                    i6 = 1;
                    break;
                case 65525:
                    i6 = 2;
                    i5 = i4;
                    i4 = 0;
                    break;
                case 65526:
                    i6 = 3;
                    i5 = i4;
                    i4 = 0;
                    break;
                case 65527:
                    i4 = 0;
                    i6 = -1;
                    break;
                case 65528:
                    i6 = -1;
                    i4 = 1;
                    break;
                case 65529:
                    i6 = -1;
                    break;
                case 65530:
                default:
                    sb.append(charAt);
                    break;
                case 65531:
                    int i7 = i3 + 1;
                    int i8 = i7 + 1;
                    int i9 = i8 + 1;
                    int i10 = i9 + 1;
                    i3 = i10 + 1;
                    sb.append(new DecimalFormat("000000000").format((bArr[i7] << 24) + (bArr[i8] << 18) + (bArr[i9] << 12) + (bArr[i10] << 6) + bArr[i3]));
                    break;
            }
            i6--;
            if (i6 == 0) {
                i4 = i5;
            }
            i3++;
        }
        while (sb.length() > 0 && sb.charAt(sb.length() - 1) == 65532) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }
}
