package com.google.zxing.client.result;

/* loaded from: classes2.dex */
public final class TextParsedResult extends ParsedResult {
    private final String a;
    private final String b;

    public TextParsedResult(String str, String str2) {
        super(ParsedResultType.TEXT);
        this.a = str;
        this.b = str2;
    }

    public String getText() {
        return this.a;
    }

    public String getLanguage() {
        return this.b;
    }

    @Override // com.google.zxing.client.result.ParsedResult
    public String getDisplayResult() {
        return this.a;
    }
}
