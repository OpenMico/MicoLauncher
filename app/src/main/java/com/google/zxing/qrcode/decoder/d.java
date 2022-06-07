package com.google.zxing.qrcode.decoder;

import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitSource;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.StringUtils;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/* compiled from: DecodedBitStreamParser.java */
/* loaded from: classes2.dex */
final class d {
    private static final char[] a = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ $%*+-./:".toCharArray();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static DecoderResult a(byte[] bArr, Version version, ErrorCorrectionLevel errorCorrectionLevel, Map<DecodeHintType, ?> map) throws FormatException {
        Mode mode;
        Mode mode2;
        BitSource bitSource = new BitSource(bArr);
        StringBuilder sb = new StringBuilder(50);
        boolean z = true;
        ArrayList arrayList = new ArrayList(1);
        boolean z2 = false;
        int i = -1;
        int i2 = -1;
        CharacterSetECI characterSetECI = null;
        while (true) {
            try {
                if (bitSource.available() < 4) {
                    mode = Mode.TERMINATOR;
                } else {
                    mode = Mode.forBits(bitSource.readBits(4));
                }
                switch (mode) {
                    case TERMINATOR:
                        mode2 = mode;
                        break;
                    case FNC1_FIRST_POSITION:
                    case FNC1_SECOND_POSITION:
                        z2 = z;
                        mode2 = mode;
                        break;
                    case STRUCTURED_APPEND:
                        if (bitSource.available() >= 16) {
                            int readBits = bitSource.readBits(8);
                            i2 = bitSource.readBits(8);
                            i = readBits;
                            mode2 = mode;
                            break;
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                    case ECI:
                        characterSetECI = CharacterSetECI.getCharacterSetECIByValue(a(bitSource));
                        if (characterSetECI != null) {
                            mode2 = mode;
                            break;
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                    case HANZI:
                        int readBits2 = bitSource.readBits(4);
                        int readBits3 = bitSource.readBits(mode.getCharacterCountBits(version));
                        if (readBits2 != z) {
                            mode2 = mode;
                            break;
                        } else {
                            a(bitSource, sb, readBits3);
                            mode2 = mode;
                            break;
                        }
                    default:
                        int readBits4 = bitSource.readBits(mode.getCharacterCountBits(version));
                        switch (mode) {
                            case NUMERIC:
                                mode2 = mode;
                                c(bitSource, sb, readBits4);
                                break;
                            case ALPHANUMERIC:
                                mode2 = mode;
                                a(bitSource, sb, readBits4, z2);
                                break;
                            case BYTE:
                                mode2 = mode;
                                a(bitSource, sb, readBits4, characterSetECI, arrayList, map);
                                break;
                            case KANJI:
                                b(bitSource, sb, readBits4);
                                mode2 = mode;
                                break;
                            default:
                                throw FormatException.getFormatInstance();
                        }
                }
                if (mode2 == Mode.TERMINATOR) {
                    return new DecoderResult(bArr, sb.toString(), arrayList.isEmpty() ? null : arrayList, errorCorrectionLevel == null ? null : errorCorrectionLevel.toString(), i, i2);
                }
                z = true;
            } catch (IllegalArgumentException unused) {
                throw FormatException.getFormatInstance();
            }
        }
    }

    private static void a(BitSource bitSource, StringBuilder sb, int i) throws FormatException {
        if (i * 13 <= bitSource.available()) {
            byte[] bArr = new byte[i * 2];
            int i2 = 0;
            while (i > 0) {
                int readBits = bitSource.readBits(13);
                int i3 = (readBits % 96) | ((readBits / 96) << 8);
                int i4 = i3 + (i3 < 959 ? 41377 : 42657);
                bArr[i2] = (byte) (i4 >> 8);
                bArr[i2 + 1] = (byte) i4;
                i2 += 2;
                i--;
            }
            try {
                sb.append(new String(bArr, StringUtils.GB2312));
            } catch (UnsupportedEncodingException unused) {
                throw FormatException.getFormatInstance();
            }
        } else {
            throw FormatException.getFormatInstance();
        }
    }

    private static void b(BitSource bitSource, StringBuilder sb, int i) throws FormatException {
        if (i * 13 <= bitSource.available()) {
            byte[] bArr = new byte[i * 2];
            int i2 = 0;
            while (i > 0) {
                int readBits = bitSource.readBits(13);
                int i3 = (readBits % 192) | ((readBits / 192) << 8);
                int i4 = i3 + (i3 < 7936 ? 33088 : 49472);
                bArr[i2] = (byte) (i4 >> 8);
                bArr[i2 + 1] = (byte) i4;
                i2 += 2;
                i--;
            }
            try {
                sb.append(new String(bArr, StringUtils.SHIFT_JIS));
            } catch (UnsupportedEncodingException unused) {
                throw FormatException.getFormatInstance();
            }
        } else {
            throw FormatException.getFormatInstance();
        }
    }

    private static void a(BitSource bitSource, StringBuilder sb, int i, CharacterSetECI characterSetECI, Collection<byte[]> collection, Map<DecodeHintType, ?> map) throws FormatException {
        String str;
        if ((i << 3) <= bitSource.available()) {
            byte[] bArr = new byte[i];
            for (int i2 = 0; i2 < i; i2++) {
                bArr[i2] = (byte) bitSource.readBits(8);
            }
            if (characterSetECI == null) {
                str = StringUtils.guessEncoding(bArr, map);
            } else {
                str = characterSetECI.name();
            }
            try {
                sb.append(new String(bArr, str));
                collection.add(bArr);
            } catch (UnsupportedEncodingException unused) {
                throw FormatException.getFormatInstance();
            }
        } else {
            throw FormatException.getFormatInstance();
        }
    }

    private static char a(int i) throws FormatException {
        char[] cArr = a;
        if (i < cArr.length) {
            return cArr[i];
        }
        throw FormatException.getFormatInstance();
    }

    private static void a(BitSource bitSource, StringBuilder sb, int i, boolean z) throws FormatException {
        while (i > 1) {
            if (bitSource.available() >= 11) {
                int readBits = bitSource.readBits(11);
                sb.append(a(readBits / 45));
                sb.append(a(readBits % 45));
                i -= 2;
            } else {
                throw FormatException.getFormatInstance();
            }
        }
        if (i == 1) {
            if (bitSource.available() >= 6) {
                sb.append(a(bitSource.readBits(6)));
            } else {
                throw FormatException.getFormatInstance();
            }
        }
        if (z) {
            for (int length = sb.length(); length < sb.length(); length++) {
                if (sb.charAt(length) == '%') {
                    if (length < sb.length() - 1) {
                        int i2 = length + 1;
                        if (sb.charAt(i2) == '%') {
                            sb.deleteCharAt(i2);
                        }
                    }
                    sb.setCharAt(length, (char) 29);
                }
            }
        }
    }

    private static void c(BitSource bitSource, StringBuilder sb, int i) throws FormatException {
        while (i >= 3) {
            if (bitSource.available() >= 10) {
                int readBits = bitSource.readBits(10);
                if (readBits < 1000) {
                    sb.append(a(readBits / 100));
                    sb.append(a((readBits / 10) % 10));
                    sb.append(a(readBits % 10));
                    i -= 3;
                } else {
                    throw FormatException.getFormatInstance();
                }
            } else {
                throw FormatException.getFormatInstance();
            }
        }
        if (i == 2) {
            if (bitSource.available() >= 7) {
                int readBits2 = bitSource.readBits(7);
                if (readBits2 < 100) {
                    sb.append(a(readBits2 / 10));
                    sb.append(a(readBits2 % 10));
                    return;
                }
                throw FormatException.getFormatInstance();
            }
            throw FormatException.getFormatInstance();
        } else if (i != 1) {
        } else {
            if (bitSource.available() >= 4) {
                int readBits3 = bitSource.readBits(4);
                if (readBits3 < 10) {
                    sb.append(a(readBits3));
                    return;
                }
                throw FormatException.getFormatInstance();
            }
            throw FormatException.getFormatInstance();
        }
    }

    private static int a(BitSource bitSource) throws FormatException {
        int readBits = bitSource.readBits(8);
        if ((readBits & 128) == 0) {
            return readBits & 127;
        }
        if ((readBits & 192) == 128) {
            return bitSource.readBits(8) | ((readBits & 63) << 8);
        }
        if ((readBits & 224) == 192) {
            return bitSource.readBits(16) | ((readBits & 31) << 16);
        }
        throw FormatException.getFormatInstance();
    }
}
