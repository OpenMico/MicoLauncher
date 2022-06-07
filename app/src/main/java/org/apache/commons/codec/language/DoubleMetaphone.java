package org.apache.commons.codec.language;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.micolauncher.module.homepage.record.HomePageRecordHelper;
import com.xiaomi.onetrack.api.c;
import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes5.dex */
public class DoubleMetaphone implements StringEncoder {
    private static final String[] a = {"GN", "KN", "PN", "WR", "PS"};
    private static final String[] b = {"L", "R", "N", "M", "B", c.b, HomePageRecordHelper.AREA_F, ExifInterface.GPS_MEASUREMENT_INTERRUPTED, ExifInterface.LONGITUDE_WEST, StringUtils.SPACE};
    private static final String[] c = {"ES", "EP", "EB", "EL", "EY", "IB", "IL", "IN", "IE", "EI", "ER"};
    private static final String[] d = {"L", ExifInterface.GPS_DIRECTION_TRUE, "K", ExifInterface.LATITUDE_SOUTH, "N", "M", "B", "Z"};
    private int e = 4;

    public String doubleMetaphone(String str) {
        return doubleMetaphone(str, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v1, types: [int] */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11, types: [int] */
    /* JADX WARN: Type inference failed for: r1v12, types: [int] */
    /* JADX WARN: Type inference failed for: r1v13, types: [int] */
    /* JADX WARN: Type inference failed for: r1v14, types: [int] */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v16, types: [int] */
    /* JADX WARN: Type inference failed for: r1v17, types: [int] */
    /* JADX WARN: Type inference failed for: r1v18, types: [int] */
    /* JADX WARN: Type inference failed for: r1v19, types: [int] */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v20 */
    /* JADX WARN: Type inference failed for: r1v21, types: [int] */
    /* JADX WARN: Type inference failed for: r1v22, types: [int] */
    /* JADX WARN: Type inference failed for: r1v23 */
    /* JADX WARN: Type inference failed for: r1v24, types: [int] */
    /* JADX WARN: Type inference failed for: r1v25, types: [int] */
    /* JADX WARN: Type inference failed for: r1v26, types: [int] */
    /* JADX WARN: Type inference failed for: r1v27, types: [int] */
    /* JADX WARN: Type inference failed for: r1v28 */
    /* JADX WARN: Type inference failed for: r1v29, types: [int] */
    /* JADX WARN: Type inference failed for: r1v3, types: [int] */
    /* JADX WARN: Type inference failed for: r1v30, types: [int] */
    /* JADX WARN: Type inference failed for: r1v31, types: [int] */
    /* JADX WARN: Type inference failed for: r1v32, types: [int] */
    /* JADX WARN: Type inference failed for: r1v33, types: [int] */
    /* JADX WARN: Type inference failed for: r1v4, types: [int] */
    /* JADX WARN: Type inference failed for: r1v5, types: [int] */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7, types: [int] */
    /* JADX WARN: Type inference failed for: r1v8, types: [int] */
    /* JADX WARN: Type inference failed for: r1v9, types: [int] */
    /* JADX WARN: Type inference failed for: r7v0, types: [org.apache.commons.codec.language.DoubleMetaphone] */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String doubleMetaphone(java.lang.String r8, boolean r9) {
        /*
            Method dump skipped, instructions count: 354
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.language.DoubleMetaphone.doubleMetaphone(java.lang.String, boolean):java.lang.String");
    }

    @Override // org.apache.commons.codec.Encoder
    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof String) {
            return doubleMetaphone((String) obj);
        }
        throw new EncoderException("DoubleMetaphone encode parameter is not of type String");
    }

    @Override // org.apache.commons.codec.StringEncoder
    public String encode(String str) {
        return doubleMetaphone(str);
    }

    public boolean isDoubleMetaphoneEqual(String str, String str2) {
        return isDoubleMetaphoneEqual(str, str2, false);
    }

    public boolean isDoubleMetaphoneEqual(String str, String str2, boolean z) {
        return org.apache.commons.codec.binary.StringUtils.equals(doubleMetaphone(str, z), doubleMetaphone(str2, z));
    }

    public int getMaxCodeLen() {
        return this.e;
    }

    public void setMaxCodeLen(int i) {
        this.e = i;
    }

    private int a(DoubleMetaphoneResult doubleMetaphoneResult, int i) {
        if (i == 0) {
            doubleMetaphoneResult.append('A');
        }
        return i + 1;
    }

    private int a(String str, DoubleMetaphoneResult doubleMetaphoneResult, int i) {
        if (a(str, i)) {
            doubleMetaphoneResult.append('K');
            return i + 2;
        } else if (i == 0 && contains(str, i, 6, "CAESAR")) {
            doubleMetaphoneResult.append('S');
            return i + 2;
        } else if (contains(str, i, 2, "CH")) {
            return c(str, doubleMetaphoneResult, i);
        } else {
            if (!contains(str, i, 2, "CZ") || contains(str, i - 2, 4, "WICZ")) {
                int i2 = i + 1;
                if (contains(str, i2, 3, "CIA")) {
                    doubleMetaphoneResult.append('X');
                    return i + 3;
                } else if (contains(str, i, 2, "CC") && (i != 1 || charAt(str, 0) != 'M')) {
                    return b(str, doubleMetaphoneResult, i);
                } else {
                    if (contains(str, i, 2, "CK", "CG", "CQ")) {
                        doubleMetaphoneResult.append('K');
                        return i + 2;
                    } else if (contains(str, i, 2, "CI", "CE", "CY")) {
                        if (contains(str, i, 3, "CIO", "CIE", "CIA")) {
                            doubleMetaphoneResult.append('S', 'X');
                        } else {
                            doubleMetaphoneResult.append('S');
                        }
                        return i + 2;
                    } else {
                        doubleMetaphoneResult.append('K');
                        return contains(str, i2, 2, " C", " Q", " G") ? i + 3 : (!contains(str, i2, 1, HomePageRecordHelper.AREA_C, "K", "Q") || contains(str, i2, 2, "CE", "CI")) ? i2 : i + 2;
                    }
                }
            } else {
                doubleMetaphoneResult.append('S', 'X');
                return i + 2;
            }
        }
    }

    private int b(String str, DoubleMetaphoneResult doubleMetaphoneResult, int i) {
        int i2 = i + 2;
        if (!contains(str, i2, 1, "I", "E", c.b) || contains(str, i2, 2, "HU")) {
            doubleMetaphoneResult.append('K');
            return i2;
        }
        if (!(i == 1 && charAt(str, i - 1) == 'A') && !contains(str, i - 1, 5, "UCCEE", "UCCES")) {
            doubleMetaphoneResult.append('X');
        } else {
            doubleMetaphoneResult.append("KS");
        }
        return i + 3;
    }

    private int c(String str, DoubleMetaphoneResult doubleMetaphoneResult, int i) {
        if (i > 0 && contains(str, i, 4, "CHAE")) {
            doubleMetaphoneResult.append('K', 'X');
            return i + 2;
        } else if (b(str, i)) {
            doubleMetaphoneResult.append('K');
            return i + 2;
        } else if (c(str, i)) {
            doubleMetaphoneResult.append('K');
            return i + 2;
        } else {
            if (i <= 0) {
                doubleMetaphoneResult.append('X');
            } else if (contains(str, 0, 2, "MC")) {
                doubleMetaphoneResult.append('K');
            } else {
                doubleMetaphoneResult.append('X', 'K');
            }
            return i + 2;
        }
    }

    private int d(String str, DoubleMetaphoneResult doubleMetaphoneResult, int i) {
        if (contains(str, i, 2, "DG")) {
            int i2 = i + 2;
            if (contains(str, i2, 1, "I", "E", "Y")) {
                doubleMetaphoneResult.append('J');
                return i + 3;
            }
            doubleMetaphoneResult.append("TK");
            return i2;
        } else if (contains(str, i, 2, "DT", "DD")) {
            doubleMetaphoneResult.append('T');
            return i + 2;
        } else {
            doubleMetaphoneResult.append('T');
            return i + 1;
        }
    }

    private int a(String str, DoubleMetaphoneResult doubleMetaphoneResult, int i, boolean z) {
        int i2 = i + 1;
        if (charAt(str, i2) == 'H') {
            return e(str, doubleMetaphoneResult, i);
        }
        if (charAt(str, i2) == 'N') {
            if (i == 1 && a(charAt(str, 0)) && !z) {
                doubleMetaphoneResult.append("KN", "N");
            } else if (contains(str, i + 2, 2, "EY") || charAt(str, i2) == 'Y' || z) {
                doubleMetaphoneResult.append("KN");
            } else {
                doubleMetaphoneResult.append("N", "KN");
            }
            return i + 2;
        } else if (contains(str, i2, 2, "LI") && !z) {
            doubleMetaphoneResult.append("KL", "L");
            return i + 2;
        } else if (i != 0 || (charAt(str, i2) != 'Y' && !contains(str, i2, 2, c))) {
            if ((contains(str, i2, 2, "ER") || charAt(str, i2) == 'Y') && !contains(str, 0, 6, "DANGER", "RANGER", "MANGER")) {
                int i3 = i - 1;
                if (!contains(str, i3, 1, "E", "I") && !contains(str, i3, 3, "RGY", "OGY")) {
                    doubleMetaphoneResult.append('K', 'J');
                    return i + 2;
                }
            }
            if (contains(str, i2, 1, "E", "I", "Y") || contains(str, i - 1, 4, "AGGI", "OGGI")) {
                if (contains(str, 0, 4, "VAN ", "VON ") || contains(str, 0, 3, "SCH") || contains(str, i2, 2, "ET")) {
                    doubleMetaphoneResult.append('K');
                } else if (contains(str, i2, 3, "IER")) {
                    doubleMetaphoneResult.append('J');
                } else {
                    doubleMetaphoneResult.append('J', 'K');
                }
                return i + 2;
            } else if (charAt(str, i2) == 'G') {
                int i4 = i + 2;
                doubleMetaphoneResult.append('K');
                return i4;
            } else {
                doubleMetaphoneResult.append('K');
                return i2;
            }
        } else {
            doubleMetaphoneResult.append('K', 'J');
            return i + 2;
        }
    }

    private int e(String str, DoubleMetaphoneResult doubleMetaphoneResult, int i) {
        if (i > 0 && !a(charAt(str, i - 1))) {
            doubleMetaphoneResult.append('K');
            return i + 2;
        } else if (i == 0) {
            int i2 = i + 2;
            if (charAt(str, i2) == 'I') {
                doubleMetaphoneResult.append('J');
                return i2;
            }
            doubleMetaphoneResult.append('K');
            return i2;
        } else if ((i > 1 && contains(str, i - 2, 1, "B", c.b, HomePageRecordHelper.AREA_D)) || ((i > 2 && contains(str, i - 3, 1, "B", c.b, HomePageRecordHelper.AREA_D)) || (i > 3 && contains(str, i - 4, 1, "B", c.b)))) {
            return i + 2;
        } else {
            if (i > 2 && charAt(str, i - 1) == 'U' && contains(str, i - 3, 1, HomePageRecordHelper.AREA_C, "G", "L", "R", ExifInterface.GPS_DIRECTION_TRUE)) {
                doubleMetaphoneResult.append('F');
            } else if (i > 0 && charAt(str, i - 1) != 'I') {
                doubleMetaphoneResult.append('K');
            }
            return i + 2;
        }
    }

    private int f(String str, DoubleMetaphoneResult doubleMetaphoneResult, int i) {
        if ((i != 0 && !a(charAt(str, i - 1))) || !a(charAt(str, i + 1))) {
            return i + 1;
        }
        doubleMetaphoneResult.append('H');
        return i + 2;
    }

    private int b(String str, DoubleMetaphoneResult doubleMetaphoneResult, int i, boolean z) {
        if (contains(str, i, 4, "JOSE") || contains(str, 0, 4, "SAN ")) {
            if ((i == 0 && charAt(str, i + 4) == ' ') || str.length() == 4 || contains(str, 0, 4, "SAN ")) {
                doubleMetaphoneResult.append('H');
            } else {
                doubleMetaphoneResult.append('J', 'H');
            }
            return i + 1;
        }
        if (i != 0 || contains(str, i, 4, "JOSE")) {
            int i2 = i - 1;
            if (a(charAt(str, i2)) && !z) {
                int i3 = i + 1;
                if (charAt(str, i3) == 'A' || charAt(str, i3) == 'O') {
                    doubleMetaphoneResult.append('J', 'H');
                }
            }
            if (i == str.length() - 1) {
                doubleMetaphoneResult.append('J', ' ');
            } else if (!contains(str, i + 1, 1, d) && !contains(str, i2, 1, ExifInterface.LATITUDE_SOUTH, "K", "L")) {
                doubleMetaphoneResult.append('J');
            }
        } else {
            doubleMetaphoneResult.append('J', 'A');
        }
        int i4 = i + 1;
        return charAt(str, i4) == 'J' ? i + 2 : i4;
    }

    private int g(String str, DoubleMetaphoneResult doubleMetaphoneResult, int i) {
        int i2 = i + 1;
        if (charAt(str, i2) == 'L') {
            if (d(str, i)) {
                doubleMetaphoneResult.appendPrimary('L');
            } else {
                doubleMetaphoneResult.append('L');
            }
            return i + 2;
        }
        doubleMetaphoneResult.append('L');
        return i2;
    }

    private int h(String str, DoubleMetaphoneResult doubleMetaphoneResult, int i) {
        int i2 = i + 1;
        if (charAt(str, i2) == 'H') {
            doubleMetaphoneResult.append('F');
            return i + 2;
        }
        doubleMetaphoneResult.append('P');
        if (contains(str, i2, 1, "P", "B")) {
            i2 = i + 2;
        }
        return i2;
    }

    private int c(String str, DoubleMetaphoneResult doubleMetaphoneResult, int i, boolean z) {
        if (i != str.length() - 1 || z || !contains(str, i - 2, 2, "IE") || contains(str, i - 4, 2, "ME", "MA")) {
            doubleMetaphoneResult.append('R');
        } else {
            doubleMetaphoneResult.appendAlternate('R');
        }
        int i2 = i + 1;
        return charAt(str, i2) == 'R' ? i + 2 : i2;
    }

    private int d(String str, DoubleMetaphoneResult doubleMetaphoneResult, int i, boolean z) {
        if (contains(str, i - 1, 3, "ISL", "YSL")) {
            return i + 1;
        }
        if (i == 0 && contains(str, i, 5, "SUGAR")) {
            doubleMetaphoneResult.append('X', 'S');
            return i + 1;
        } else if (contains(str, i, 2, "SH")) {
            if (contains(str, i + 1, 4, "HEIM", "HOEK", "HOLM", "HOLZ")) {
                doubleMetaphoneResult.append('S');
            } else {
                doubleMetaphoneResult.append('X');
            }
            return i + 2;
        } else if (contains(str, i, 3, "SIO", "SIA") || contains(str, i, 4, "SIAN")) {
            if (z) {
                doubleMetaphoneResult.append('S');
            } else {
                doubleMetaphoneResult.append('S', 'X');
            }
            return i + 3;
        } else {
            if (i != 0 || !contains(str, i + 1, 1, "M", "N", "L", ExifInterface.LONGITUDE_WEST)) {
                int i2 = i + 1;
                if (!contains(str, i2, 1, "Z")) {
                    if (contains(str, i, 2, "SC")) {
                        return i(str, doubleMetaphoneResult, i);
                    }
                    if (i != str.length() - 1 || !contains(str, i - 2, 2, "AI", "OI")) {
                        doubleMetaphoneResult.append('S');
                    } else {
                        doubleMetaphoneResult.appendAlternate('S');
                    }
                    return contains(str, i2, 1, ExifInterface.LATITUDE_SOUTH, "Z") ? i + 2 : i2;
                }
            }
            doubleMetaphoneResult.append('S', 'X');
            int i3 = i + 1;
            return contains(str, i3, 1, "Z") ? i + 2 : i3;
        }
    }

    private int i(String str, DoubleMetaphoneResult doubleMetaphoneResult, int i) {
        int i2 = i + 2;
        if (charAt(str, i2) == 'H') {
            int i3 = i + 3;
            if (contains(str, i3, 2, "OO", "ER", "EN", "UY", "ED", "EM")) {
                if (contains(str, i3, 2, "ER", "EN")) {
                    doubleMetaphoneResult.append("X", "SK");
                } else {
                    doubleMetaphoneResult.append("SK");
                }
            } else if (i != 0 || a(charAt(str, 3)) || charAt(str, 3) == 'W') {
                doubleMetaphoneResult.append('X');
            } else {
                doubleMetaphoneResult.append('X', 'S');
            }
        } else if (contains(str, i2, 1, "I", "E", "Y")) {
            doubleMetaphoneResult.append('S');
        } else {
            doubleMetaphoneResult.append("SK");
        }
        return i + 3;
    }

    private int j(String str, DoubleMetaphoneResult doubleMetaphoneResult, int i) {
        if (contains(str, i, 4, "TION")) {
            doubleMetaphoneResult.append('X');
            return i + 3;
        } else if (contains(str, i, 3, "TIA", "TCH")) {
            doubleMetaphoneResult.append('X');
            return i + 3;
        } else if (contains(str, i, 2, "TH") || contains(str, i, 3, "TTH")) {
            int i2 = i + 2;
            if (contains(str, i2, 2, "OM", "AM") || contains(str, 0, 4, "VAN ", "VON ") || contains(str, 0, 3, "SCH")) {
                doubleMetaphoneResult.append('T');
                return i2;
            }
            doubleMetaphoneResult.append('0', 'T');
            return i2;
        } else {
            doubleMetaphoneResult.append('T');
            int i3 = i + 1;
            return contains(str, i3, 1, ExifInterface.GPS_DIRECTION_TRUE, HomePageRecordHelper.AREA_D) ? i + 2 : i3;
        }
    }

    private int k(String str, DoubleMetaphoneResult doubleMetaphoneResult, int i) {
        if (contains(str, i, 2, "WR")) {
            doubleMetaphoneResult.append('R');
            return i + 2;
        }
        if (i == 0) {
            int i2 = i + 1;
            if (a(charAt(str, i2)) || contains(str, i, 2, "WH")) {
                if (a(charAt(str, i2))) {
                    doubleMetaphoneResult.append('A', 'F');
                } else {
                    doubleMetaphoneResult.append('A');
                }
                return i2;
            }
        }
        if ((i == str.length() - 1 && a(charAt(str, i - 1))) || contains(str, i - 1, 5, "EWSKI", "EWSKY", "OWSKI", "OWSKY") || contains(str, 0, 3, "SCH")) {
            doubleMetaphoneResult.appendAlternate('F');
            return i + 1;
        } else if (!contains(str, i, 4, "WICZ", "WITZ")) {
            return i + 1;
        } else {
            doubleMetaphoneResult.append("TS", "FX");
            return i + 4;
        }
    }

    private int l(String str, DoubleMetaphoneResult doubleMetaphoneResult, int i) {
        if (i == 0) {
            doubleMetaphoneResult.append('S');
            return i + 1;
        }
        if (i != str.length() - 1 || (!contains(str, i - 3, 3, "IAU", "EAU") && !contains(str, i - 2, 2, "AU", "OU"))) {
            doubleMetaphoneResult.append("KS");
        }
        int i2 = i + 1;
        return contains(str, i2, 1, HomePageRecordHelper.AREA_C, "X") ? i + 2 : i2;
    }

    private int e(String str, DoubleMetaphoneResult doubleMetaphoneResult, int i, boolean z) {
        int i2 = i + 1;
        if (charAt(str, i2) == 'H') {
            doubleMetaphoneResult.append('J');
            return i + 2;
        }
        if (contains(str, i2, 2, "ZO", "ZI", "ZA") || (z && i > 0 && charAt(str, i - 1) != 'T')) {
            doubleMetaphoneResult.append(ExifInterface.LATITUDE_SOUTH, "TS");
        } else {
            doubleMetaphoneResult.append('S');
        }
        if (charAt(str, i2) == 'Z') {
            i2 = i + 2;
        }
        return i2;
    }

    private boolean a(String str, int i) {
        if (contains(str, i, 4, "CHIA")) {
            return true;
        }
        if (i <= 1) {
            return false;
        }
        int i2 = i - 2;
        if (a(charAt(str, i2)) || !contains(str, i - 1, 3, "ACH")) {
            return false;
        }
        char charAt = charAt(str, i + 2);
        return !(charAt == 'I' || charAt == 'E') || contains(str, i2, 6, "BACHER", "MACHER");
    }

    private boolean b(String str, int i) {
        if (i != 0) {
            return false;
        }
        int i2 = i + 1;
        return (contains(str, i2, 5, "HARAC", "HARIS") || contains(str, i2, 3, "HOR", "HYM", "HIA", "HEM")) && !contains(str, 0, 5, "CHORE");
    }

    private boolean c(String str, int i) {
        if (!contains(str, 0, 4, "VAN ", "VON ") && !contains(str, 0, 3, "SCH") && !contains(str, i - 2, 6, "ORCHES", "ARCHIT", "ORCHID")) {
            int i2 = i + 2;
            if (!contains(str, i2, 1, ExifInterface.GPS_DIRECTION_TRUE, ExifInterface.LATITUDE_SOUTH)) {
                if (!contains(str, i - 1, 1, "A", "O", "U", "E") && i != 0) {
                    return false;
                }
                if (!contains(str, i2, 1, b) && i + 1 != str.length() - 1) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean d(String str, int i) {
        if (i != str.length() - 3 || !contains(str, i - 1, 4, "ILLO", "ILLA", "ALLE")) {
            return (contains(str, str.length() - 2, 2, "AS", "OS") || contains(str, str.length() - 1, 1, "A", "O")) && contains(str, i - 1, 4, "ALLE");
        }
        return true;
    }

    private boolean e(String str, int i) {
        int i2 = i + 1;
        if (charAt(str, i2) == 'M') {
            return true;
        }
        return contains(str, i + (-1), 3, "UMB") && (i2 == str.length() - 1 || contains(str, i + 2, 2, "ER"));
    }

    private boolean a(String str) {
        return str.indexOf(87) > -1 || str.indexOf(75) > -1 || str.indexOf("CZ") > -1 || str.indexOf("WITZ") > -1;
    }

    private boolean a(char c2) {
        return "AEIOUY".indexOf(c2) != -1;
    }

    private boolean b(String str) {
        for (String str2 : a) {
            if (str.startsWith(str2)) {
                return true;
            }
        }
        return false;
    }

    private String c(String str) {
        if (str == null) {
            return null;
        }
        String trim = str.trim();
        if (trim.length() == 0) {
            return null;
        }
        return trim.toUpperCase(Locale.ENGLISH);
    }

    protected char charAt(String str, int i) {
        if (i < 0 || i >= str.length()) {
            return (char) 0;
        }
        return str.charAt(i);
    }

    protected static boolean contains(String str, int i, int i2, String... strArr) {
        int i3;
        if (i < 0 || (i3 = i2 + i) > str.length()) {
            return false;
        }
        String substring = str.substring(i, i3);
        for (String str2 : strArr) {
            if (substring.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    /* loaded from: classes5.dex */
    public class DoubleMetaphoneResult {
        private final StringBuilder b;
        private final StringBuilder c;
        private final int d;

        public DoubleMetaphoneResult(int i) {
            this.b = new StringBuilder(DoubleMetaphone.this.getMaxCodeLen());
            this.c = new StringBuilder(DoubleMetaphone.this.getMaxCodeLen());
            this.d = i;
        }

        public void append(char c) {
            appendPrimary(c);
            appendAlternate(c);
        }

        public void append(char c, char c2) {
            appendPrimary(c);
            appendAlternate(c2);
        }

        public void appendPrimary(char c) {
            if (this.b.length() < this.d) {
                this.b.append(c);
            }
        }

        public void appendAlternate(char c) {
            if (this.c.length() < this.d) {
                this.c.append(c);
            }
        }

        public void append(String str) {
            appendPrimary(str);
            appendAlternate(str);
        }

        public void append(String str, String str2) {
            appendPrimary(str);
            appendAlternate(str2);
        }

        public void appendPrimary(String str) {
            int length = this.d - this.b.length();
            if (str.length() <= length) {
                this.b.append(str);
            } else {
                this.b.append(str.substring(0, length));
            }
        }

        public void appendAlternate(String str) {
            int length = this.d - this.c.length();
            if (str.length() <= length) {
                this.c.append(str);
            } else {
                this.c.append(str.substring(0, length));
            }
        }

        public String getPrimary() {
            return this.b.toString();
        }

        public String getAlternate() {
            return this.c.toString();
        }

        public boolean isComplete() {
            return this.b.length() >= this.d && this.c.length() >= this.d;
        }
    }
}
