package com.google.zxing.oned;

import com.alibaba.fastjson.asm.Opcodes;
import com.fasterxml.jackson.core.JsonPointer;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.util.Map;

/* loaded from: classes2.dex */
public final class Code39Writer extends OneDimensionalCodeWriter {
    @Override // com.google.zxing.oned.OneDimensionalCodeWriter, com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.CODE_39) {
            return super.encode(str, barcodeFormat, i, i2, map);
        }
        throw new IllegalArgumentException("Can only encode CODE_39, but got " + barcodeFormat);
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public boolean[] encode(String str) {
        int length = str.length();
        if (length <= 80) {
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                } else if ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%".indexOf(str.charAt(i)) < 0) {
                    str = a(str);
                    length = str.length();
                    if (length > 80) {
                        throw new IllegalArgumentException("Requested contents should be less than 80 digits long, but got " + length + " (extended full ASCII mode)");
                    }
                } else {
                    i++;
                }
            }
            int[] iArr = new int[9];
            int i2 = length + 25;
            int i3 = 0;
            while (i3 < length) {
                a(Code39Reader.a["0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%".indexOf(str.charAt(i3))], iArr);
                int i4 = i2;
                for (int i5 = 0; i5 < 9; i5++) {
                    i4 += iArr[i5];
                }
                i3++;
                i2 = i4;
            }
            boolean[] zArr = new boolean[i2];
            a(Opcodes.LCMP, iArr);
            int appendPattern = appendPattern(zArr, 0, iArr, true);
            int[] iArr2 = {1};
            int appendPattern2 = appendPattern + appendPattern(zArr, appendPattern, iArr2, false);
            for (int i6 = 0; i6 < length; i6++) {
                a(Code39Reader.a["0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%".indexOf(str.charAt(i6))], iArr);
                int appendPattern3 = appendPattern2 + appendPattern(zArr, appendPattern2, iArr, true);
                appendPattern2 = appendPattern3 + appendPattern(zArr, appendPattern3, iArr2, false);
            }
            a(Opcodes.LCMP, iArr);
            appendPattern(zArr, appendPattern2, iArr, true);
            return zArr;
        }
        throw new IllegalArgumentException("Requested contents should be less than 80 digits long, but got " + length);
    }

    private static void a(int i, int[] iArr) {
        for (int i2 = 0; i2 < 9; i2++) {
            int i3 = 1;
            if (((1 << (8 - i2)) & i) != 0) {
                i3 = 2;
            }
            iArr[i2] = i3;
        }
    }

    private static String a(String str) {
        int length = str.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt != 0) {
                if (charAt != ' ') {
                    if (charAt == '@') {
                        sb.append("%V");
                    } else if (charAt != '`') {
                        switch (charAt) {
                            case '-':
                            case '.':
                                break;
                            default:
                                if (charAt <= 0 || charAt >= 27) {
                                    if (charAt <= 26 || charAt >= ' ') {
                                        if ((charAt <= ' ' || charAt >= '-') && charAt != '/' && charAt != ':') {
                                            if (charAt <= '/' || charAt >= ':') {
                                                if (charAt <= ':' || charAt >= '@') {
                                                    if (charAt <= '@' || charAt >= '[') {
                                                        if (charAt <= 'Z' || charAt >= '`') {
                                                            if (charAt > '`' && charAt < '{') {
                                                                sb.append('+');
                                                                sb.append((char) ((charAt - 'a') + 65));
                                                                break;
                                                            } else if (charAt > 'z' && charAt < 128) {
                                                                sb.append('%');
                                                                sb.append((char) ((charAt - '{') + 80));
                                                                break;
                                                            } else {
                                                                throw new IllegalArgumentException("Requested content contains a non-encodable character: '" + str.charAt(i) + LrcRow.SINGLE_QUOTE);
                                                            }
                                                        } else {
                                                            sb.append('%');
                                                            sb.append((char) ((charAt - '[') + 75));
                                                            break;
                                                        }
                                                    } else {
                                                        sb.append((char) ((charAt - 'A') + 65));
                                                        break;
                                                    }
                                                } else {
                                                    sb.append('%');
                                                    sb.append((char) ((charAt - ';') + 70));
                                                    break;
                                                }
                                            } else {
                                                sb.append((char) ((charAt - '0') + 48));
                                                break;
                                            }
                                        } else {
                                            sb.append(JsonPointer.SEPARATOR);
                                            sb.append((char) ((charAt - '!') + 65));
                                            break;
                                        }
                                    } else {
                                        sb.append('%');
                                        sb.append((char) ((charAt - 27) + 65));
                                        break;
                                    }
                                } else {
                                    sb.append('$');
                                    sb.append((char) ((charAt - 1) + 65));
                                    break;
                                }
                        }
                    } else {
                        sb.append("%W");
                    }
                }
                sb.append(charAt);
            } else {
                sb.append("%U");
            }
        }
        return sb.toString();
    }
}
