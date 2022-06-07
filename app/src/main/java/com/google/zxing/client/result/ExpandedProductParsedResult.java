package com.google.zxing.client.result;

import java.util.Map;

/* loaded from: classes2.dex */
public final class ExpandedProductParsedResult extends ParsedResult {
    public static final String KILOGRAM = "KG";
    public static final String POUND = "LB";
    private final String a;
    private final String b;
    private final String c;
    private final String d;
    private final String e;
    private final String f;
    private final String g;
    private final String h;
    private final String i;
    private final String j;
    private final String k;
    private final String l;
    private final String m;
    private final String n;
    private final Map<String, String> o;

    public ExpandedProductParsedResult(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, Map<String, String> map) {
        super(ParsedResultType.PRODUCT);
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
        this.e = str5;
        this.f = str6;
        this.g = str7;
        this.h = str8;
        this.i = str9;
        this.j = str10;
        this.k = str11;
        this.l = str12;
        this.m = str13;
        this.n = str14;
        this.o = map;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ExpandedProductParsedResult)) {
            return false;
        }
        ExpandedProductParsedResult expandedProductParsedResult = (ExpandedProductParsedResult) obj;
        return a(this.b, expandedProductParsedResult.b) && a(this.c, expandedProductParsedResult.c) && a(this.d, expandedProductParsedResult.d) && a(this.e, expandedProductParsedResult.e) && a(this.g, expandedProductParsedResult.g) && a(this.h, expandedProductParsedResult.h) && a(this.i, expandedProductParsedResult.i) && a(this.j, expandedProductParsedResult.j) && a(this.k, expandedProductParsedResult.k) && a(this.l, expandedProductParsedResult.l) && a(this.m, expandedProductParsedResult.m) && a(this.n, expandedProductParsedResult.n) && a(this.o, expandedProductParsedResult.o);
    }

    private static boolean a(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    public int hashCode() {
        return ((((((((((((a(this.b) ^ 0) ^ a(this.c)) ^ a(this.d)) ^ a(this.e)) ^ a(this.g)) ^ a(this.h)) ^ a(this.i)) ^ a(this.j)) ^ a(this.k)) ^ a(this.l)) ^ a(this.m)) ^ a(this.n)) ^ a(this.o);
    }

    private static int a(Object obj) {
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public String getRawText() {
        return this.a;
    }

    public String getProductID() {
        return this.b;
    }

    public String getSscc() {
        return this.c;
    }

    public String getLotNumber() {
        return this.d;
    }

    public String getProductionDate() {
        return this.e;
    }

    public String getPackagingDate() {
        return this.f;
    }

    public String getBestBeforeDate() {
        return this.g;
    }

    public String getExpirationDate() {
        return this.h;
    }

    public String getWeight() {
        return this.i;
    }

    public String getWeightType() {
        return this.j;
    }

    public String getWeightIncrement() {
        return this.k;
    }

    public String getPrice() {
        return this.l;
    }

    public String getPriceIncrement() {
        return this.m;
    }

    public String getPriceCurrency() {
        return this.n;
    }

    public Map<String, String> getUncommonAIs() {
        return this.o;
    }

    @Override // com.google.zxing.client.result.ParsedResult
    public String getDisplayResult() {
        return String.valueOf(this.a);
    }
}
