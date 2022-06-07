package com.google.zxing.client.result;

/* loaded from: classes2.dex */
public final class ExpandedProductResultParser extends ResultParser {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0217 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0235 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0242 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0251 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:151:0x0260 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:152:0x026a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0274 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:154:0x027e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:155:0x0288 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0292 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:157:0x029c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:160:0x020a A[SYNTHETIC] */
    @Override // com.google.zxing.client.result.ResultParser
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.zxing.client.result.ExpandedProductParsedResult parse(com.google.zxing.Result r25) {
        /*
            Method dump skipped, instructions count: 854
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.client.result.ExpandedProductResultParser.parse(com.google.zxing.Result):com.google.zxing.client.result.ExpandedProductParsedResult");
    }

    private static String a(int i, String str) {
        if (str.charAt(i) != '(') {
            return null;
        }
        String substring = str.substring(i + 1);
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < substring.length(); i2++) {
            char charAt = substring.charAt(i2);
            if (charAt == ')') {
                return sb.toString();
            }
            if (charAt < '0' || charAt > '9') {
                return null;
            }
            sb.append(charAt);
        }
        return sb.toString();
    }

    private static String b(int i, String str) {
        StringBuilder sb = new StringBuilder();
        String substring = str.substring(i);
        for (int i2 = 0; i2 < substring.length(); i2++) {
            char charAt = substring.charAt(i2);
            if (charAt == '(') {
                if (a(i2, substring) != null) {
                    break;
                }
                sb.append('(');
            } else {
                sb.append(charAt);
            }
        }
        return sb.toString();
    }
}
