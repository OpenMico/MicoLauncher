package com.google.zxing.client.result;

/* loaded from: classes2.dex */
public final class VINParsedResult extends ParsedResult {
    private final String a;
    private final String b;
    private final String c;
    private final String d;
    private final String e;
    private final String f;
    private final int g;
    private final char h;
    private final String i;

    public VINParsedResult(String str, String str2, String str3, String str4, String str5, String str6, int i, char c, String str7) {
        super(ParsedResultType.VIN);
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
        this.e = str5;
        this.f = str6;
        this.g = i;
        this.h = c;
        this.i = str7;
    }

    public String getVIN() {
        return this.a;
    }

    public String getWorldManufacturerID() {
        return this.b;
    }

    public String getVehicleDescriptorSection() {
        return this.c;
    }

    public String getVehicleIdentifierSection() {
        return this.d;
    }

    public String getCountryCode() {
        return this.e;
    }

    public String getVehicleAttributes() {
        return this.f;
    }

    public int getModelYear() {
        return this.g;
    }

    public char getPlantCode() {
        return this.h;
    }

    public String getSequentialNumber() {
        return this.i;
    }

    @Override // com.google.zxing.client.result.ParsedResult
    public String getDisplayResult() {
        StringBuilder sb = new StringBuilder(50);
        sb.append(this.b);
        sb.append(' ');
        sb.append(this.c);
        sb.append(' ');
        sb.append(this.d);
        sb.append('\n');
        String str = this.e;
        if (str != null) {
            sb.append(str);
            sb.append(' ');
        }
        sb.append(this.g);
        sb.append(' ');
        sb.append(this.h);
        sb.append(' ');
        sb.append(this.i);
        sb.append('\n');
        return sb.toString();
    }
}
