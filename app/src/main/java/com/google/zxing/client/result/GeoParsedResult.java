package com.google.zxing.client.result;

import io.netty.util.internal.StringUtil;

/* loaded from: classes2.dex */
public final class GeoParsedResult extends ParsedResult {
    private final double a;
    private final double b;
    private final double c;
    private final String d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public GeoParsedResult(double d, double d2, double d3, String str) {
        super(ParsedResultType.GEO);
        this.a = d;
        this.b = d2;
        this.c = d3;
        this.d = str;
    }

    public String getGeoURI() {
        StringBuilder sb = new StringBuilder();
        sb.append("geo:");
        sb.append(this.a);
        sb.append(StringUtil.COMMA);
        sb.append(this.b);
        if (this.c > 0.0d) {
            sb.append(StringUtil.COMMA);
            sb.append(this.c);
        }
        if (this.d != null) {
            sb.append('?');
            sb.append(this.d);
        }
        return sb.toString();
    }

    public double getLatitude() {
        return this.a;
    }

    public double getLongitude() {
        return this.b;
    }

    public double getAltitude() {
        return this.c;
    }

    public String getQuery() {
        return this.d;
    }

    @Override // com.google.zxing.client.result.ParsedResult
    public String getDisplayResult() {
        StringBuilder sb = new StringBuilder(20);
        sb.append(this.a);
        sb.append(", ");
        sb.append(this.b);
        if (this.c > 0.0d) {
            sb.append(", ");
            sb.append(this.c);
            sb.append('m');
        }
        if (this.d != null) {
            sb.append(" (");
            sb.append(this.d);
            sb.append(')');
        }
        return sb.toString();
    }
}
