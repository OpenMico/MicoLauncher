package io.netty.handler.codec.socksx.v4;

import org.eclipse.jetty.http.HttpMethods;

/* loaded from: classes4.dex */
public class Socks4CommandType implements Comparable<Socks4CommandType> {
    private final byte a;
    private final String b;
    private String c;
    public static final Socks4CommandType CONNECT = new Socks4CommandType(1, HttpMethods.CONNECT);
    public static final Socks4CommandType BIND = new Socks4CommandType(2, "BIND");

    public static Socks4CommandType valueOf(byte b) {
        switch (b) {
            case 1:
                return CONNECT;
            case 2:
                return BIND;
            default:
                return new Socks4CommandType(b);
        }
    }

    public Socks4CommandType(int i) {
        this(i, "UNKNOWN");
    }

    public Socks4CommandType(int i, String str) {
        if (str != null) {
            this.a = (byte) i;
            this.b = str;
            return;
        }
        throw new NullPointerException("name");
    }

    public byte byteValue() {
        return this.a;
    }

    public int hashCode() {
        return this.a;
    }

    public boolean equals(Object obj) {
        return (obj instanceof Socks4CommandType) && this.a == ((Socks4CommandType) obj).a;
    }

    public int compareTo(Socks4CommandType socks4CommandType) {
        return this.a - socks4CommandType.a;
    }

    public String toString() {
        String str = this.c;
        if (str != null) {
            return str;
        }
        String str2 = this.b + '(' + (this.a & 255) + ')';
        this.c = str2;
        return str2;
    }
}
