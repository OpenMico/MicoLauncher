package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.asm.Opcodes;
import com.fasterxml.jackson.core.JsonPointer;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.xiaomi.micolauncher.BuildConfig;
import com.xiaomi.passport.ui.internal.Constants;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Properties;
import okio.Utf8;

/* loaded from: classes.dex */
public class IOUtils {
    public static final char[] ASCII_CHARS;
    public static final char[] CA;
    static final char[] DigitOnes;
    static final char[] DigitTens;
    public static final String FASTJSON_COMPATIBLEWITHFIELDNAME = "fastjson.compatibleWithFieldName";
    public static final String FASTJSON_COMPATIBLEWITHJAVABEAN = "fastjson.compatibleWithJavaBean";
    public static final String FASTJSON_PROPERTIES = "fastjson.properties";
    public static final int[] IA;
    static final char[] digits;
    public static final char[] replaceChars;
    static final int[] sizeTable;
    public static final byte[] specicalFlags_doubleQuotes;
    public static final boolean[] specicalFlags_doubleQuotesFlags;
    public static final byte[] specicalFlags_singleQuotes;
    public static final boolean[] specicalFlags_singleQuotesFlags;
    public static final Properties DEFAULT_PROPERTIES = new Properties();
    public static final Charset UTF8 = Charset.forName("UTF-8");
    public static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static final boolean[] firstIdentifierFlags = new boolean[256];
    public static final boolean[] identifierFlags = new boolean[256];

    public static int stringSize(long j) {
        long j2 = 10;
        for (int i = 1; i < 19; i++) {
            if (j < j2) {
                return i;
            }
            j2 *= 10;
        }
        return 19;
    }

    static {
        char c = 0;
        while (true) {
            boolean[] zArr = firstIdentifierFlags;
            if (c >= zArr.length) {
                break;
            }
            if (c >= 'A' && c <= 'Z') {
                zArr[c] = true;
            } else if (c >= 'a' && c <= 'z') {
                firstIdentifierFlags[c] = true;
            } else if (c == '_' || c == '$') {
                firstIdentifierFlags[c] = true;
            }
            c = (char) (c + 1);
        }
        char c2 = 0;
        while (true) {
            boolean[] zArr2 = identifierFlags;
            if (c2 < zArr2.length) {
                if (c2 >= 'A' && c2 <= 'Z') {
                    zArr2[c2] = true;
                } else if (c2 >= 'a' && c2 <= 'z') {
                    identifierFlags[c2] = true;
                } else if (c2 == '_') {
                    identifierFlags[c2] = true;
                } else if (c2 >= '0' && c2 <= '9') {
                    identifierFlags[c2] = true;
                }
                c2 = (char) (c2 + 1);
            } else {
                try {
                    break;
                } catch (Throwable unused) {
                }
            }
        }
        loadPropertiesFromFile();
        specicalFlags_doubleQuotes = new byte[Opcodes.IF_ICMPLT];
        specicalFlags_singleQuotes = new byte[Opcodes.IF_ICMPLT];
        specicalFlags_doubleQuotesFlags = new boolean[Opcodes.IF_ICMPLT];
        specicalFlags_singleQuotesFlags = new boolean[Opcodes.IF_ICMPLT];
        replaceChars = new char[93];
        byte[] bArr = specicalFlags_doubleQuotes;
        bArr[0] = 4;
        bArr[1] = 4;
        bArr[2] = 4;
        bArr[3] = 4;
        bArr[4] = 4;
        bArr[5] = 4;
        bArr[6] = 4;
        bArr[7] = 4;
        bArr[8] = 1;
        bArr[9] = 1;
        bArr[10] = 1;
        bArr[11] = 4;
        bArr[12] = 1;
        bArr[13] = 1;
        bArr[34] = 1;
        bArr[92] = 1;
        byte[] bArr2 = specicalFlags_singleQuotes;
        bArr2[0] = 4;
        bArr2[1] = 4;
        bArr2[2] = 4;
        bArr2[3] = 4;
        bArr2[4] = 4;
        bArr2[5] = 4;
        bArr2[6] = 4;
        bArr2[7] = 4;
        bArr2[8] = 1;
        bArr2[9] = 1;
        bArr2[10] = 1;
        bArr2[11] = 4;
        bArr2[12] = 1;
        bArr2[13] = 1;
        bArr2[92] = 1;
        bArr2[39] = 1;
        for (int i = 14; i <= 31; i++) {
            specicalFlags_doubleQuotes[i] = 4;
            specicalFlags_singleQuotes[i] = 4;
        }
        for (int i2 = 127; i2 < 160; i2++) {
            specicalFlags_doubleQuotes[i2] = 4;
            specicalFlags_singleQuotes[i2] = 4;
        }
        for (int i3 = 0; i3 < 161; i3++) {
            specicalFlags_doubleQuotesFlags[i3] = specicalFlags_doubleQuotes[i3] != 0;
            specicalFlags_singleQuotesFlags[i3] = specicalFlags_singleQuotes[i3] != 0;
        }
        char[] cArr = replaceChars;
        cArr[0] = '0';
        cArr[1] = '1';
        cArr[2] = '2';
        cArr[3] = '3';
        cArr[4] = '4';
        cArr[5] = '5';
        cArr[6] = '6';
        cArr[7] = '7';
        cArr[8] = 'b';
        cArr[9] = 't';
        cArr[10] = 'n';
        cArr[11] = 'v';
        cArr[12] = 'f';
        cArr[13] = 'r';
        cArr[34] = '\"';
        cArr[39] = '\'';
        cArr[47] = JsonPointer.SEPARATOR;
        cArr[92] = '\\';
        ASCII_CHARS = new char[]{'0', '0', '0', '1', '0', '2', '0', '3', '0', '4', '0', '5', '0', '6', '0', '7', '0', '8', '0', '9', '0', 'A', '0', 'B', '0', 'C', '0', 'D', '0', 'E', '0', 'F', '1', '0', '1', '1', '1', '2', '1', '3', '1', '4', '1', '5', '1', '6', '1', '7', '1', '8', '1', '9', '1', 'A', '1', 'B', '1', 'C', '1', 'D', '1', 'E', '1', 'F', '2', '0', '2', '1', '2', '2', '2', '3', '2', '4', '2', '5', '2', '6', '2', '7', '2', '8', '2', '9', '2', 'A', '2', 'B', '2', 'C', '2', 'D', '2', 'E', '2', 'F'};
        digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        DigitTens = new char[]{'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9'};
        DigitOnes = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        sizeTable = new int[]{9, 99, 999, Constants.RESULT_RESTART_BINDING_EMAIL, 99999, 999999, BuildConfig.VERSION_CODE, 99999999, 999999999, Integer.MAX_VALUE};
        CA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
        IA = new int[256];
        Arrays.fill(IA, -1);
        int length = CA.length;
        for (int i4 = 0; i4 < length; i4++) {
            IA[CA[i4]] = i4;
        }
        IA[61] = 0;
    }

    public static String getStringProperty(String str) {
        String str2;
        try {
            str2 = System.getProperty(str);
        } catch (SecurityException unused) {
            str2 = null;
        }
        return str2 == null ? DEFAULT_PROPERTIES.getProperty(str) : str2;
    }

    public static void loadPropertiesFromFile() {
        InputStream inputStream = (InputStream) AccessController.doPrivileged(new PrivilegedAction<InputStream>() { // from class: com.alibaba.fastjson.util.IOUtils.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.security.PrivilegedAction
            public InputStream run() {
                ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                if (contextClassLoader != null) {
                    return contextClassLoader.getResourceAsStream(IOUtils.FASTJSON_PROPERTIES);
                }
                return ClassLoader.getSystemResourceAsStream(IOUtils.FASTJSON_PROPERTIES);
            }
        });
        if (inputStream != null) {
            try {
                DEFAULT_PROPERTIES.load(inputStream);
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception unused) {
            }
        }
    }

    public static void getChars(long j, int i, char[] cArr) {
        char c;
        if (j < 0) {
            c = '-';
            j = -j;
        } else {
            c = 0;
        }
        while (j > 2147483647L) {
            long j2 = j / 100;
            int i2 = (int) (j - (((j2 << 6) + (j2 << 5)) + (j2 << 2)));
            int i3 = i - 1;
            cArr[i3] = DigitOnes[i2];
            i = i3 - 1;
            cArr[i] = DigitTens[i2];
            j = j2;
        }
        int i4 = (int) j;
        while (i4 >= 65536) {
            int i5 = i4 / 100;
            int i6 = i4 - (((i5 << 6) + (i5 << 5)) + (i5 << 2));
            int i7 = i - 1;
            cArr[i7] = DigitOnes[i6];
            i = i7 - 1;
            cArr[i] = DigitTens[i6];
            i4 = i5;
        }
        while (true) {
            int i8 = (52429 * i4) >>> 19;
            i--;
            cArr[i] = digits[i4 - ((i8 << 3) + (i8 << 1))];
            if (i8 == 0) {
                break;
            }
            i4 = i8;
        }
        if (c != 0) {
            cArr[i - 1] = c;
        }
    }

    public static void getChars(int i, int i2, char[] cArr) {
        char c;
        if (i < 0) {
            c = '-';
            i = -i;
        } else {
            c = 0;
        }
        while (i >= 65536) {
            int i3 = i / 100;
            int i4 = i - (((i3 << 6) + (i3 << 5)) + (i3 << 2));
            int i5 = i2 - 1;
            cArr[i5] = DigitOnes[i4];
            i2 = i5 - 1;
            cArr[i2] = DigitTens[i4];
            i = i3;
        }
        while (true) {
            int i6 = (52429 * i) >>> 19;
            i2--;
            cArr[i2] = digits[i - ((i6 << 3) + (i6 << 1))];
            if (i6 == 0) {
                break;
            }
            i = i6;
        }
        if (c != 0) {
            cArr[i2 - 1] = c;
        }
    }

    public static void getChars(byte b, int i, char[] cArr) {
        int i2;
        char c;
        if (b < 0) {
            c = '-';
            i2 = -b;
        } else {
            c = 0;
            i2 = b;
        }
        while (true) {
            int i3 = i2 == 1 ? 1 : 0;
            int i4 = i2 == 1 ? 1 : 0;
            int i5 = (52429 * i3) >>> 19;
            i--;
            cArr[i] = digits[i2 - ((i5 << 3) + (i5 << 1))];
            if (i5 == 0) {
                break;
            }
            i2 = i5;
        }
        if (c != 0) {
            cArr[i - 1] = c;
        }
    }

    public static int stringSize(int i) {
        int i2 = 0;
        while (i > sizeTable[i2]) {
            i2++;
        }
        return i2 + 1;
    }

    public static void decode(CharsetDecoder charsetDecoder, ByteBuffer byteBuffer, CharBuffer charBuffer) {
        try {
            CoderResult decode = charsetDecoder.decode(byteBuffer, charBuffer, true);
            if (!decode.isUnderflow()) {
                decode.throwException();
            }
            CoderResult flush = charsetDecoder.flush(charBuffer);
            if (!flush.isUnderflow()) {
                flush.throwException();
            }
        } catch (CharacterCodingException e) {
            throw new JSONException("utf8 decode error, " + e.getMessage(), e);
        }
    }

    public static boolean firstIdentifier(char c) {
        boolean[] zArr = firstIdentifierFlags;
        return c < zArr.length && zArr[c];
    }

    public static boolean isIdent(char c) {
        boolean[] zArr = identifierFlags;
        return c < zArr.length && zArr[c];
    }

    public static byte[] decodeBase64(char[] cArr, int i, int i2) {
        int i3;
        int i4;
        int i5 = 0;
        if (i2 == 0) {
            return new byte[0];
        }
        int i6 = (i + i2) - 1;
        while (i < i6 && IA[cArr[i]] < 0) {
            i++;
        }
        while (i6 > 0 && IA[cArr[i6]] < 0) {
            i6--;
        }
        if (cArr[i6] == '=') {
            i3 = cArr[i6 + (-1)] == '=' ? 2 : 1;
        } else {
            i3 = 0;
        }
        int i7 = (i6 - i) + 1;
        if (i2 > 76) {
            i4 = (cArr[76] == '\r' ? i7 / 78 : 0) << 1;
        } else {
            i4 = 0;
        }
        int i8 = (((i7 - i4) * 6) >> 3) - i3;
        byte[] bArr = new byte[i8];
        int i9 = (i8 / 3) * 3;
        int i10 = i;
        int i11 = 0;
        int i12 = 0;
        while (i11 < i9) {
            int[] iArr = IA;
            int i13 = i10 + 1;
            int i14 = i13 + 1;
            int i15 = (iArr[cArr[i10]] << 18) | (iArr[cArr[i13]] << 12);
            int i16 = i14 + 1;
            int i17 = i15 | (iArr[cArr[i14]] << 6);
            i10 = i16 + 1;
            int i18 = i17 | iArr[cArr[i16]];
            int i19 = i11 + 1;
            bArr[i11] = (byte) (i18 >> 16);
            int i20 = i19 + 1;
            bArr[i19] = (byte) (i18 >> 8);
            i11 = i20 + 1;
            bArr[i20] = (byte) i18;
            if (i4 > 0 && (i12 = i12 + 1) == 19) {
                i10 += 2;
                i12 = 0;
            }
        }
        if (i11 < i8) {
            int i21 = 0;
            while (i10 <= i6 - i3) {
                i10++;
                i5 |= IA[cArr[i10]] << (18 - (i21 * 6));
                i21++;
            }
            int i22 = 16;
            while (i11 < i8) {
                i11++;
                bArr[i11] = (byte) (i5 >> i22);
                i22 -= 8;
            }
        }
        return bArr;
    }

    public static byte[] decodeBase64(String str, int i, int i2) {
        int i3;
        int i4;
        int i5 = 0;
        if (i2 == 0) {
            return new byte[0];
        }
        int i6 = (i + i2) - 1;
        while (i < i6 && IA[str.charAt(i)] < 0) {
            i++;
        }
        while (i6 > 0 && IA[str.charAt(i6)] < 0) {
            i6--;
        }
        if (str.charAt(i6) == '=') {
            i3 = str.charAt(i6 + (-1)) == '=' ? 2 : 1;
        } else {
            i3 = 0;
        }
        int i7 = (i6 - i) + 1;
        if (i2 > 76) {
            i4 = (str.charAt(76) == '\r' ? i7 / 78 : 0) << 1;
        } else {
            i4 = 0;
        }
        int i8 = (((i7 - i4) * 6) >> 3) - i3;
        byte[] bArr = new byte[i8];
        int i9 = (i8 / 3) * 3;
        int i10 = i;
        int i11 = 0;
        int i12 = 0;
        while (i11 < i9) {
            int i13 = i10 + 1;
            int i14 = i13 + 1;
            int i15 = i14 + 1;
            i10 = i15 + 1;
            int i16 = (IA[str.charAt(i10)] << 18) | (IA[str.charAt(i13)] << 12) | (IA[str.charAt(i14)] << 6) | IA[str.charAt(i15)];
            int i17 = i11 + 1;
            bArr[i11] = (byte) (i16 >> 16);
            int i18 = i17 + 1;
            bArr[i17] = (byte) (i16 >> 8);
            i11 = i18 + 1;
            bArr[i18] = (byte) i16;
            if (i4 > 0 && (i12 = i12 + 1) == 19) {
                i10 += 2;
                i12 = 0;
            }
        }
        if (i11 < i8) {
            int i19 = 0;
            while (i10 <= i6 - i3) {
                i10++;
                i5 |= IA[str.charAt(i10)] << (18 - (i19 * 6));
                i19++;
            }
            int i20 = 16;
            while (i11 < i8) {
                i11++;
                bArr[i11] = (byte) (i5 >> i20);
                i20 -= 8;
            }
        }
        return bArr;
    }

    public static byte[] decodeBase64(String str) {
        int i;
        int length = str.length();
        int i2 = 0;
        if (length == 0) {
            return new byte[0];
        }
        int i3 = length - 1;
        int i4 = 0;
        while (i4 < i3 && IA[str.charAt(i4) & 255] < 0) {
            i4++;
        }
        while (i3 > 0 && IA[str.charAt(i3) & 255] < 0) {
            i3--;
        }
        int i5 = str.charAt(i3) == '=' ? str.charAt(i3 + (-1)) == '=' ? 2 : 1 : 0;
        int i6 = (i3 - i4) + 1;
        if (length > 76) {
            i = (str.charAt(76) == '\r' ? i6 / 78 : 0) << 1;
        } else {
            i = 0;
        }
        int i7 = (((i6 - i) * 6) >> 3) - i5;
        byte[] bArr = new byte[i7];
        int i8 = (i7 / 3) * 3;
        int i9 = 0;
        int i10 = i4;
        int i11 = 0;
        while (i11 < i8) {
            int i12 = i10 + 1;
            int i13 = i12 + 1;
            int i14 = i13 + 1;
            i10 = i14 + 1;
            int i15 = (IA[str.charAt(i10)] << 18) | (IA[str.charAt(i12)] << 12) | (IA[str.charAt(i13)] << 6) | IA[str.charAt(i14)];
            int i16 = i11 + 1;
            bArr[i11] = (byte) (i15 >> 16);
            int i17 = i16 + 1;
            bArr[i16] = (byte) (i15 >> 8);
            i11 = i17 + 1;
            bArr[i17] = (byte) i15;
            if (i > 0 && (i9 = i9 + 1) == 19) {
                i10 += 2;
                i9 = 0;
            }
        }
        if (i11 < i7) {
            int i18 = 0;
            while (i10 <= i3 - i5) {
                i10++;
                i2 |= IA[str.charAt(i10)] << (18 - (i18 * 6));
                i18++;
            }
            int i19 = 16;
            while (i11 < i7) {
                i11++;
                bArr[i11] = (byte) (i2 >> i19);
                i19 -= 8;
            }
        }
        return bArr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0089  */
    /* JADX WARN: Type inference failed for: r10v15, types: [int] */
    /* JADX WARN: Type inference failed for: r10v25 */
    /* JADX WARN: Type inference failed for: r10v3, types: [int, char] */
    /* JADX WARN: Type inference failed for: r10v31, types: [int, char] */
    /* JADX WARN: Type inference failed for: r3v11, types: [char] */
    /* JADX WARN: Type inference failed for: r3v9, types: [int, char] */
    /* JADX WARN: Type inference failed for: r9v0, types: [char[]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int encodeUTF8(char[] r9, int r10, int r11, byte[] r12) {
        /*
            Method dump skipped, instructions count: 210
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.IOUtils.encodeUTF8(char[], int, int, byte[]):int");
    }

    public static int decodeUTF8(byte[] bArr, int i, int i2, char[] cArr) {
        int i3 = i + i2;
        int min = Math.min(i2, cArr.length);
        int i4 = i;
        int i5 = 0;
        while (i5 < min && bArr[i4] >= 0) {
            i5++;
            i4++;
            cArr[i5] = (char) bArr[i4];
        }
        while (i4 < i3) {
            int i6 = i4 + 1;
            byte b = bArr[i4];
            if (b >= 0) {
                i5++;
                cArr[i5] = (char) b;
                i4 = i6;
            } else if ((b >> 5) != -2 || (b & 30) == 0) {
                if ((b >> 4) == -2) {
                    int i7 = i6 + 1;
                    if (i7 >= i3) {
                        return -1;
                    }
                    byte b2 = bArr[i6];
                    i4 = i7 + 1;
                    byte b3 = bArr[i7];
                    if ((b == -32 && (b2 & 224) == 128) || (b2 & 192) != 128 || (b3 & 192) != 128) {
                        return -1;
                    }
                    char c = (char) (((b2 << 6) ^ (b << 12)) ^ ((-123008) ^ b3));
                    if (c >= 55296 && c < 57344) {
                        return -1;
                    }
                    i5++;
                    cArr[i5] = c;
                } else if ((b >> 3) != -2 || i6 + 2 >= i3) {
                    return -1;
                } else {
                    int i8 = i6 + 1;
                    byte b4 = bArr[i6];
                    int i9 = i8 + 1;
                    byte b5 = bArr[i8];
                    i4 = i9 + 1;
                    byte b6 = bArr[i9];
                    int i10 = (((b << 18) ^ (b4 << 12)) ^ (b5 << 6)) ^ (3678080 ^ b6);
                    if ((b4 & 192) != 128 || (b5 & 192) != 128 || (b6 & 192) != 128 || i10 < 65536 || i10 >= 1114112) {
                        return -1;
                    }
                    int i11 = i5 + 1;
                    cArr[i5] = (char) ((i10 >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                    i5 = i11 + 1;
                    cArr[i11] = (char) ((i10 & AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES) + 56320);
                }
            } else if (i6 >= i3) {
                return -1;
            } else {
                i4 = i6 + 1;
                byte b7 = bArr[i6];
                if ((b7 & 192) != 128) {
                    return -1;
                }
                i5++;
                cArr[i5] = (char) ((b7 ^ (b << 6)) ^ Utf8.MASK_2BYTES);
            }
        }
        return i5;
    }

    public static String readAll(Reader reader) {
        StringBuilder sb = new StringBuilder();
        try {
            char[] cArr = new char[2048];
            while (true) {
                int read = reader.read(cArr, 0, cArr.length);
                if (read < 0) {
                    return sb.toString();
                }
                sb.append(cArr, 0, read);
            }
        } catch (Exception e) {
            throw new JSONException("read string from reader error", e);
        }
    }

    public static boolean isValidJsonpQueryParam(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (!(charAt == '.' || isIdent(charAt))) {
                return false;
            }
        }
        return true;
    }
}
