package com.google.zxing.client.result;

/* loaded from: classes2.dex */
public final class TelParsedResult extends ParsedResult {
    private final String a;
    private final String b;
    private final String c;

    public TelParsedResult(String str, String str2, String str3) {
        super(ParsedResultType.TEL);
        this.a = str;
        this.b = str2;
        this.c = str3;
    }

    public String getNumber() {
        return this.a;
    }

    public String getTelURI() {
        return this.b;
    }

    public String getTitle() {
        return this.c;
    }

    @Override // com.google.zxing.client.result.ParsedResult
    public String getDisplayResult() {
        StringBuilder sb = new StringBuilder(20);
        maybeAppend(this.a, sb);
        maybeAppend(this.c, sb);
        return sb.toString();
    }
}
