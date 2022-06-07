package com.google.zxing.client.result;

/* loaded from: classes2.dex */
public final class WifiParsedResult extends ParsedResult {
    private final String a;
    private final String b;
    private final String c;
    private final boolean d;
    private final String e;
    private final String f;
    private final String g;
    private final String h;

    public WifiParsedResult(String str, String str2, String str3) {
        this(str, str2, str3, false);
    }

    public WifiParsedResult(String str, String str2, String str3, boolean z) {
        this(str, str2, str3, z, null, null, null, null);
    }

    public WifiParsedResult(String str, String str2, String str3, boolean z, String str4, String str5, String str6, String str7) {
        super(ParsedResultType.WIFI);
        this.a = str2;
        this.b = str;
        this.c = str3;
        this.d = z;
        this.e = str4;
        this.f = str5;
        this.g = str6;
        this.h = str7;
    }

    public String getSsid() {
        return this.a;
    }

    public String getNetworkEncryption() {
        return this.b;
    }

    public String getPassword() {
        return this.c;
    }

    public boolean isHidden() {
        return this.d;
    }

    public String getIdentity() {
        return this.e;
    }

    public String getAnonymousIdentity() {
        return this.f;
    }

    public String getEapMethod() {
        return this.g;
    }

    public String getPhase2Method() {
        return this.h;
    }

    @Override // com.google.zxing.client.result.ParsedResult
    public String getDisplayResult() {
        StringBuilder sb = new StringBuilder(80);
        maybeAppend(this.a, sb);
        maybeAppend(this.b, sb);
        maybeAppend(this.c, sb);
        maybeAppend(Boolean.toString(this.d), sb);
        return sb.toString();
    }
}
