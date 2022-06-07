package org.apache.commons.text.translate;

import java.util.Arrays;
import java.util.EnumSet;

/* loaded from: classes5.dex */
public class NumericEntityUnescaper extends CharSequenceTranslator {
    private final EnumSet<OPTION> b;

    /* loaded from: classes5.dex */
    public enum OPTION {
        semiColonRequired,
        semiColonOptional,
        errorIfNoSemiColon
    }

    public NumericEntityUnescaper(OPTION... optionArr) {
        if (optionArr.length > 0) {
            this.b = EnumSet.copyOf(Arrays.asList(optionArr));
        } else {
            this.b = EnumSet.copyOf(Arrays.asList(OPTION.semiColonRequired));
        }
    }

    public boolean isSet(OPTION option) {
        EnumSet<OPTION> enumSet = this.b;
        return enumSet != null && enumSet.contains(option);
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x0071, code lost:
        if (r8.charAt(r3) != ';') goto L_0x0075;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0073, code lost:
        r0 = 1;
     */
    @Override // org.apache.commons.text.translate.CharSequenceTranslator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int translate(java.lang.CharSequence r8, int r9, java.io.Writer r10) throws java.io.IOException {
        /*
            r7 = this;
            int r0 = r8.length()
            char r1 = r8.charAt(r9)
            r2 = 0
            r3 = 38
            if (r1 != r3) goto L_0x00cf
            int r1 = r0 + (-2)
            if (r9 >= r1) goto L_0x00cf
            int r1 = r9 + 1
            char r1 = r8.charAt(r1)
            r3 = 35
            if (r1 != r3) goto L_0x00cf
            int r9 = r9 + 2
            char r1 = r8.charAt(r9)
            r3 = 120(0x78, float:1.68E-43)
            r4 = 1
            if (r1 == r3) goto L_0x002d
            r3 = 88
            if (r1 != r3) goto L_0x002b
            goto L_0x002d
        L_0x002b:
            r1 = r2
            goto L_0x0033
        L_0x002d:
            int r9 = r9 + 1
            if (r9 != r0) goto L_0x0032
            return r2
        L_0x0032:
            r1 = r4
        L_0x0033:
            r3 = r9
        L_0x0034:
            if (r3 >= r0) goto L_0x0069
            char r5 = r8.charAt(r3)
            r6 = 48
            if (r5 < r6) goto L_0x0046
            char r5 = r8.charAt(r3)
            r6 = 57
            if (r5 <= r6) goto L_0x0066
        L_0x0046:
            char r5 = r8.charAt(r3)
            r6 = 97
            if (r5 < r6) goto L_0x0056
            char r5 = r8.charAt(r3)
            r6 = 102(0x66, float:1.43E-43)
            if (r5 <= r6) goto L_0x0066
        L_0x0056:
            char r5 = r8.charAt(r3)
            r6 = 65
            if (r5 < r6) goto L_0x0069
            char r5 = r8.charAt(r3)
            r6 = 70
            if (r5 > r6) goto L_0x0069
        L_0x0066:
            int r3 = r3 + 1
            goto L_0x0034
        L_0x0069:
            if (r3 == r0) goto L_0x0075
            char r0 = r8.charAt(r3)
            r5 = 59
            if (r0 != r5) goto L_0x0075
            r0 = r4
            goto L_0x0076
        L_0x0075:
            r0 = r2
        L_0x0076:
            if (r0 != 0) goto L_0x0092
            org.apache.commons.text.translate.NumericEntityUnescaper$OPTION r5 = org.apache.commons.text.translate.NumericEntityUnescaper.OPTION.semiColonRequired
            boolean r5 = r7.isSet(r5)
            if (r5 == 0) goto L_0x0081
            return r2
        L_0x0081:
            org.apache.commons.text.translate.NumericEntityUnescaper$OPTION r5 = org.apache.commons.text.translate.NumericEntityUnescaper.OPTION.errorIfNoSemiColon
            boolean r5 = r7.isSet(r5)
            if (r5 != 0) goto L_0x008a
            goto L_0x0092
        L_0x008a:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
            java.lang.String r9 = "Semi-colon required at end of numeric entity"
            r8.<init>(r9)
            throw r8
        L_0x0092:
            if (r1 == 0) goto L_0x00a3
            java.lang.CharSequence r8 = r8.subSequence(r9, r3)     // Catch: NumberFormatException -> 0x00ce
            java.lang.String r8 = r8.toString()     // Catch: NumberFormatException -> 0x00ce
            r5 = 16
            int r8 = java.lang.Integer.parseInt(r8, r5)     // Catch: NumberFormatException -> 0x00ce
            goto L_0x00b1
        L_0x00a3:
            java.lang.CharSequence r8 = r8.subSequence(r9, r3)     // Catch: NumberFormatException -> 0x00ce
            java.lang.String r8 = r8.toString()     // Catch: NumberFormatException -> 0x00ce
            r5 = 10
            int r8 = java.lang.Integer.parseInt(r8, r5)     // Catch: NumberFormatException -> 0x00ce
        L_0x00b1:
            r5 = 65535(0xffff, float:9.1834E-41)
            if (r8 <= r5) goto L_0x00c5
            char[] r8 = java.lang.Character.toChars(r8)
            char r2 = r8[r2]
            r10.write(r2)
            char r8 = r8[r4]
            r10.write(r8)
            goto L_0x00c8
        L_0x00c5:
            r10.write(r8)
        L_0x00c8:
            int r3 = r3 + 2
            int r3 = r3 - r9
            int r3 = r3 + r1
            int r3 = r3 + r0
            return r3
        L_0x00ce:
            return r2
        L_0x00cf:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.text.translate.NumericEntityUnescaper.translate(java.lang.CharSequence, int, java.io.Writer):int");
    }
}
