package com.google.zxing.integration.android;

/* loaded from: classes2.dex */
public final class IntentResult {
    private final String a;
    private final String b;
    private final byte[] c;
    private final Integer d;
    private final String e;
    private final String f;

    /* JADX INFO: Access modifiers changed from: package-private */
    public IntentResult() {
        this(null, null, null, null, null, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public IntentResult(String str, String str2, byte[] bArr, Integer num, String str3, String str4) {
        this.a = str;
        this.b = str2;
        this.c = bArr;
        this.d = num;
        this.e = str3;
        this.f = str4;
    }

    public String getContents() {
        return this.a;
    }

    public String getFormatName() {
        return this.b;
    }

    public byte[] getRawBytes() {
        return this.c;
    }

    public Integer getOrientation() {
        return this.d;
    }

    public String getErrorCorrectionLevel() {
        return this.e;
    }

    public String getBarcodeImagePath() {
        return this.f;
    }

    public String toString() {
        byte[] bArr = this.c;
        int length = bArr == null ? 0 : bArr.length;
        return "Format: " + this.b + "\nContents: " + this.a + "\nRaw bytes: (" + length + " bytes)\nOrientation: " + this.d + "\nEC level: " + this.e + "\nBarcode image: " + this.f + '\n';
    }
}
