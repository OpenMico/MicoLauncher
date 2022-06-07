package com.google.zxing.client.result;

/* loaded from: classes2.dex */
public final class ProductParsedResult extends ParsedResult {
    private final String a;
    private final String b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ProductParsedResult(String str, String str2) {
        super(ParsedResultType.PRODUCT);
        this.a = str;
        this.b = str2;
    }

    public String getProductID() {
        return this.a;
    }

    public String getNormalizedProductID() {
        return this.b;
    }

    @Override // com.google.zxing.client.result.ParsedResult
    public String getDisplayResult() {
        return this.a;
    }
}
