package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes2.dex */
public final class Code128Writer extends OneDimensionalCodeWriter {

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public enum a {
        UNCODABLE,
        ONE_DIGIT,
        TWO_DIGITS,
        FNC_1
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter, com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.CODE_128) {
            return super.encode(str, barcodeFormat, i, i2, map);
        }
        throw new IllegalArgumentException("Can only encode CODE_128, but got " + barcodeFormat);
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public boolean[] encode(String str) {
        int length = str.length();
        if (length <= 0 || length > 80) {
            throw new IllegalArgumentException("Contents length should be between 1 and 80 characters, but got " + length);
        }
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            switch (charAt) {
                case 241:
                case 242:
                case 243:
                case 244:
                    break;
                default:
                    if (charAt <= 127) {
                        break;
                    } else {
                        throw new IllegalArgumentException("Bad character in input: " + charAt);
                    }
            }
        }
        ArrayList<int[]> arrayList = new ArrayList();
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 1;
        while (true) {
            int i7 = 103;
            if (i3 < length) {
                int a2 = a(str, i3, i5);
                if (a2 == i5) {
                    i7 = 101;
                    switch (str.charAt(i3)) {
                        case 241:
                            i7 = 102;
                            break;
                        case 242:
                            i7 = 97;
                            break;
                        case 243:
                            i7 = 96;
                            break;
                        case 244:
                            if (i5 != 101) {
                                i7 = 100;
                                break;
                            }
                            break;
                        default:
                            switch (i5) {
                                case 100:
                                    i7 = str.charAt(i3) - ' ';
                                    break;
                                case 101:
                                    i7 = str.charAt(i3) - ' ';
                                    if (i7 < 0) {
                                        i7 += 96;
                                        break;
                                    }
                                    break;
                                default:
                                    i7 = Integer.parseInt(str.substring(i3, i3 + 2));
                                    i3++;
                                    break;
                            }
                    }
                    i3++;
                } else {
                    if (i5 == 0) {
                        switch (a2) {
                            case 100:
                                i7 = 104;
                                break;
                            case 101:
                                break;
                            default:
                                i7 = 105;
                                break;
                        }
                    } else {
                        i7 = a2;
                    }
                    i5 = a2;
                }
                arrayList.add(Code128Reader.a[i7]);
                i4 += i7 * i6;
                if (i3 != 0) {
                    i6++;
                }
            } else {
                arrayList.add(Code128Reader.a[i4 % 103]);
                arrayList.add(Code128Reader.a[106]);
                int i8 = 0;
                for (int[] iArr : arrayList) {
                    int i9 = i8;
                    for (int i10 : iArr) {
                        i9 += i10;
                    }
                    i8 = i9;
                }
                boolean[] zArr = new boolean[i8];
                for (int[] iArr2 : arrayList) {
                    i += appendPattern(zArr, i, iArr2, true);
                }
                return zArr;
            }
        }
    }

    private static a a(CharSequence charSequence, int i) {
        int length = charSequence.length();
        if (i >= length) {
            return a.UNCODABLE;
        }
        char charAt = charSequence.charAt(i);
        if (charAt == 241) {
            return a.FNC_1;
        }
        if (charAt < '0' || charAt > '9') {
            return a.UNCODABLE;
        }
        int i2 = i + 1;
        if (i2 >= length) {
            return a.ONE_DIGIT;
        }
        char charAt2 = charSequence.charAt(i2);
        if (charAt2 < '0' || charAt2 > '9') {
            return a.ONE_DIGIT;
        }
        return a.TWO_DIGITS;
    }

    private static int a(CharSequence charSequence, int i, int i2) {
        a a2;
        a a3;
        char charAt;
        a a4 = a(charSequence, i);
        if (a4 == a.ONE_DIGIT) {
            return 100;
        }
        if (a4 == a.UNCODABLE) {
            return (i >= charSequence.length() || ((charAt = charSequence.charAt(i)) >= ' ' && (i2 != 101 || charAt >= '`'))) ? 100 : 101;
        }
        if (i2 == 99) {
            return 99;
        }
        if (i2 != 100) {
            if (a4 == a.FNC_1) {
                a4 = a(charSequence, i + 1);
            }
            return a4 == a.TWO_DIGITS ? 99 : 100;
        } else if (a4 == a.FNC_1 || (a2 = a(charSequence, i + 2)) == a.UNCODABLE || a2 == a.ONE_DIGIT) {
            return 100;
        } else {
            if (a2 == a.FNC_1) {
                return a(charSequence, i + 3) == a.TWO_DIGITS ? 99 : 100;
            }
            int i3 = i + 4;
            while (true) {
                a3 = a(charSequence, i3);
                if (a3 != a.TWO_DIGITS) {
                    break;
                }
                i3 += 2;
            }
            return a3 == a.ONE_DIGIT ? 100 : 99;
        }
    }
}
