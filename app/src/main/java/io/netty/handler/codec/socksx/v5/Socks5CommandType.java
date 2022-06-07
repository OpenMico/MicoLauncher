package io.netty.handler.codec.socksx.v5;

import org.eclipse.jetty.http.HttpMethods;

/* loaded from: classes4.dex */
public class Socks5CommandType implements Comparable<Socks5CommandType> {
    private final byte a;
    private final String b;
    private String c;
    public static final Socks5CommandType CONNECT = new Socks5CommandType(1, HttpMethods.CONNECT);
    public static final Socks5CommandType BIND = new Socks5CommandType(2, "BIND");
    public static final Socks5CommandType UDP_ASSOCIATE = new Socks5CommandType(3, "UDP_ASSOCIATE");

    public static Socks5CommandType valueOf(byte b) {
        switch (b) {
            case 1:
                return CONNECT;
            case 2:
                return BIND;
            case 3:
                return UDP_ASSOCIATE;
            default:
                return new Socks5CommandType(b);
        }
    }

    public Socks5CommandType(int i) {
        this(i, "UNKNOWN");
    }

    public Socks5CommandType(int i, String str) {
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
        return (obj instanceof Socks5CommandType) && this.a == ((Socks5CommandType) obj).a;
    }

    public int compareTo(Socks5CommandType socks5CommandType) {
        return this.a - socks5CommandType.a;
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
