package com.google.zxing.client.result;

/* loaded from: classes2.dex */
public final class ISBNParsedResult extends ParsedResult {
    private final String a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ISBNParsedResult(String str) {
        super(ParsedResultType.ISBN);
        this.a = str;
    }

    public String getISBN() {
        return this.a;
    }

    @Override // com.google.zxing.client.result.ParsedResult
    public String getDisplayResult() {
        return this.a;
    }
}
