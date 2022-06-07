package io.netty.handler.codec.http;

import io.netty.util.AsciiString;

/* loaded from: classes4.dex */
public final class HttpScheme {
    public static final HttpScheme HTTP = new HttpScheme(80, "http");
    public static final HttpScheme HTTPS = new HttpScheme(443, "https");
    private final int a;
    private final AsciiString b;

    private HttpScheme(int i, String str) {
        this.a = i;
        this.b = new AsciiString(str);
    }

    public AsciiString name() {
        return this.b;
    }

    public int port() {
        return this.a;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof HttpScheme)) {
            return false;
        }
        HttpScheme httpScheme = (HttpScheme) obj;
        return httpScheme.port() == this.a && httpScheme.name().equals(this.b);
    }

    public int hashCode() {
        return (this.a * 31) + this.b.hashCode();
    }

    public String toString() {
        return this.b.toString();
    }
}
