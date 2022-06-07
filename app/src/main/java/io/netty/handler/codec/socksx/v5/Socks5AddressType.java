package io.netty.handler.codec.socksx.v5;

/* loaded from: classes4.dex */
public class Socks5AddressType implements Comparable<Socks5AddressType> {
    private final byte a;
    private final String b;
    private String c;
    public static final Socks5AddressType IPv4 = new Socks5AddressType(1, "IPv4");
    public static final Socks5AddressType DOMAIN = new Socks5AddressType(3, "DOMAIN");
    public static final Socks5AddressType IPv6 = new Socks5AddressType(4, "IPv6");

    public static Socks5AddressType valueOf(byte b) {
        if (b == 1) {
            return IPv4;
        }
        switch (b) {
            case 3:
                return DOMAIN;
            case 4:
                return IPv6;
            default:
                return new Socks5AddressType(b);
        }
    }

    public Socks5AddressType(int i) {
        this(i, "UNKNOWN");
    }

    public Socks5AddressType(int i, String str) {
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
        return (obj instanceof Socks5AddressType) && this.a == ((Socks5AddressType) obj).a;
    }

    public int compareTo(Socks5AddressType socks5AddressType) {
        return this.a - socks5AddressType.a;
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
