package com.google.zxing.client.result;

import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public final class URIParsedResult extends ParsedResult {
    private static final Pattern a = Pattern.compile(":/*([^/@]+)@[^/]+");
    private final String b;
    private final String c;

    public URIParsedResult(String str, String str2) {
        super(ParsedResultType.URI);
        this.b = a(str);
        this.c = str2;
    }

    public String getURI() {
        return this.b;
    }

    public String getTitle() {
        return this.c;
    }

    public boolean isPossiblyMaliciousURI() {
        return a.matcher(this.b).find();
    }

    @Override // com.google.zxing.client.result.ParsedResult
    public String getDisplayResult() {
        StringBuilder sb = new StringBuilder(30);
        maybeAppend(this.c, sb);
        maybeAppend(this.b, sb);
        return sb.toString();
    }

    private static String a(String str) {
        String trim = str.trim();
        int indexOf = trim.indexOf(58);
        if (indexOf >= 0 && !a(trim, indexOf)) {
            return trim;
        }
        return "http://" + trim;
    }

    private static boolean a(String str, int i) {
        int i2 = i + 1;
        int indexOf = str.indexOf(47, i2);
        if (indexOf < 0) {
            indexOf = str.length();
        }
        return ResultParser.isSubstringOfDigits(str, i2, indexOf - i2);
    }
}
