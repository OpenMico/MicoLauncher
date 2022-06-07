package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes5.dex */
public class MatchRatingApproachEncoder implements StringEncoder {
    private static final String[] a = {"BB", "CC", "DD", "FF", "GG", "HH", "JJ", "KK", "LL", "MM", "NN", "PP", "QQ", "RR", "SS", "TT", "VV", "WW", "XX", "YY", "ZZ"};

    int a(int i) {
        if (i <= 4) {
            return 5;
        }
        if (i >= 5 && i <= 7) {
            return 4;
        }
        if (i < 8 || i > 11) {
            return i == 12 ? 2 : 1;
        }
        return 3;
    }

    String a(String str) {
        String upperCase = str.toUpperCase(Locale.ENGLISH);
        for (String str2 : new String[]{"\\-", "[&]", "\\'", "\\.", "[\\,]"}) {
            upperCase = upperCase.replaceAll(str2, "");
        }
        return d(upperCase).replaceAll("\\s+", "");
    }

    @Override // org.apache.commons.codec.Encoder
    public final Object encode(Object obj) throws EncoderException {
        if (obj instanceof String) {
            return encode((String) obj);
        }
        throw new EncoderException("Parameter supplied to Match Rating Approach encoder is not of type java.lang.String");
    }

    @Override // org.apache.commons.codec.StringEncoder
    public final String encode(String str) {
        return (str == null || "".equalsIgnoreCase(str) || StringUtils.SPACE.equalsIgnoreCase(str) || str.length() == 1) ? "" : b(e(f(a(str))));
    }

    String b(String str) {
        int length = str.length();
        if (length <= 6) {
            return str;
        }
        String substring = str.substring(0, 3);
        String substring2 = str.substring(length - 3, length);
        return substring + substring2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x006e, code lost:
        r1 = a(java.lang.Math.abs(r5.length() + r6.length()));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isEncodeEquals(java.lang.String r5, java.lang.String r6) {
        /*
            r4 = this;
            r0 = 0
            if (r5 == 0) goto L_0x0089
            java.lang.String r1 = ""
            boolean r1 = r1.equalsIgnoreCase(r5)
            if (r1 != 0) goto L_0x0089
            java.lang.String r1 = " "
            boolean r1 = r1.equalsIgnoreCase(r5)
            if (r1 == 0) goto L_0x0015
            goto L_0x0089
        L_0x0015:
            if (r6 == 0) goto L_0x0088
            java.lang.String r1 = ""
            boolean r1 = r1.equalsIgnoreCase(r6)
            if (r1 != 0) goto L_0x0088
            java.lang.String r1 = " "
            boolean r1 = r1.equalsIgnoreCase(r6)
            if (r1 == 0) goto L_0x0028
            goto L_0x0088
        L_0x0028:
            int r1 = r5.length()
            r2 = 1
            if (r1 == r2) goto L_0x0087
            int r1 = r6.length()
            if (r1 != r2) goto L_0x0036
            goto L_0x0087
        L_0x0036:
            boolean r1 = r5.equalsIgnoreCase(r6)
            if (r1 == 0) goto L_0x003d
            return r2
        L_0x003d:
            java.lang.String r5 = r4.a(r5)
            java.lang.String r6 = r4.a(r6)
            java.lang.String r5 = r4.f(r5)
            java.lang.String r6 = r4.f(r6)
            java.lang.String r5 = r4.e(r5)
            java.lang.String r6 = r4.e(r6)
            java.lang.String r5 = r4.b(r5)
            java.lang.String r6 = r4.b(r6)
            int r1 = r5.length()
            int r3 = r6.length()
            int r1 = r1 - r3
            int r1 = java.lang.Math.abs(r1)
            r3 = 3
            if (r1 < r3) goto L_0x006e
            return r0
        L_0x006e:
            int r1 = r5.length()
            int r3 = r6.length()
            int r1 = r1 + r3
            int r1 = java.lang.Math.abs(r1)
            int r1 = r4.a(r1)
            int r5 = r4.a(r5, r6)
            if (r5 < r1) goto L_0x0086
            r0 = r2
        L_0x0086:
            return r0
        L_0x0087:
            return r0
        L_0x0088:
            return r0
        L_0x0089:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.language.MatchRatingApproachEncoder.isEncodeEquals(java.lang.String, java.lang.String):boolean");
    }

    boolean c(String str) {
        return str.equalsIgnoreCase("E") || str.equalsIgnoreCase("A") || str.equalsIgnoreCase("O") || str.equalsIgnoreCase("I") || str.equalsIgnoreCase("U");
    }

    int a(String str, String str2) {
        char[] charArray = str.toCharArray();
        char[] charArray2 = str2.toCharArray();
        int length = str.length() - 1;
        int length2 = str2.length() - 1;
        int i = 0;
        while (i < charArray.length && i <= length2) {
            int i2 = i + 1;
            String substring = str.substring(i, i2);
            int i3 = length - i;
            String substring2 = str.substring(i3, i3 + 1);
            String substring3 = str2.substring(i, i2);
            int i4 = length2 - i;
            String substring4 = str2.substring(i4, i4 + 1);
            if (substring.equals(substring3)) {
                charArray[i] = ' ';
                charArray2[i] = ' ';
            }
            if (substring2.equals(substring4)) {
                charArray[i3] = ' ';
                charArray2[i4] = ' ';
            }
            i = i2;
        }
        String replaceAll = new String(charArray).replaceAll("\\s+", "");
        String replaceAll2 = new String(charArray2).replaceAll("\\s+", "");
        if (replaceAll.length() > replaceAll2.length()) {
            return Math.abs(6 - replaceAll.length());
        }
        return Math.abs(6 - replaceAll2.length());
    }

    String d(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            int indexOf = "ÀàÈèÌìÒòÙùÁáÉéÍíÓóÚúÝýÂâÊêÎîÔôÛûŶŷÃãÕõÑñÄäËëÏïÖöÜüŸÿÅåÇçŐőŰű".indexOf(charAt);
            if (indexOf > -1) {
                sb.append("AaEeIiOoUuAaEeIiOoUuYyAaEeIiOoUuYyAaOoNnAaEeIiOoUuYyAaCcOoUu".charAt(indexOf));
            } else {
                sb.append(charAt);
            }
        }
        return sb.toString();
    }

    String e(String str) {
        String upperCase = str.toUpperCase();
        String[] strArr = a;
        String str2 = upperCase;
        for (String str3 : strArr) {
            if (str2.contains(str3)) {
                str2 = str2.replace(str3, str3.substring(0, 1));
            }
        }
        return str2;
    }

    String f(String str) {
        String substring = str.substring(0, 1);
        String replaceAll = str.replaceAll("A", "").replaceAll("E", "").replaceAll("I", "").replaceAll("O", "").replaceAll("U", "").replaceAll("\\s{2,}\\b", StringUtils.SPACE);
        if (!c(substring)) {
            return replaceAll;
        }
        return substring + replaceAll;
    }
}
