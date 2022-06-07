package io.netty.handler.codec.socksx.v5;

/* loaded from: classes4.dex */
public class Socks5AuthMethod implements Comparable<Socks5AuthMethod> {
    private final byte a;
    private final String b;
    private String c;
    public static final Socks5AuthMethod NO_AUTH = new Socks5AuthMethod(0, "NO_AUTH");
    public static final Socks5AuthMethod GSSAPI = new Socks5AuthMethod(1, "GSSAPI");
    public static final Socks5AuthMethod PASSWORD = new Socks5AuthMethod(2, "PASSWORD");
    public static final Socks5AuthMethod UNACCEPTED = new Socks5AuthMethod(255, "UNACCEPTED");

    public static Socks5AuthMethod valueOf(byte b) {
        switch (b) {
            case -1:
                return UNACCEPTED;
            case 0:
                return NO_AUTH;
            case 1:
                return GSSAPI;
            case 2:
                return PASSWORD;
            default:
                return new Socks5AuthMethod(b);
        }
    }

    public Socks5AuthMethod(int i) {
        this(i, "UNKNOWN");
    }

    public Socks5AuthMethod(int i, String str) {
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
        return (obj instanceof Socks5AuthMethod) && this.a == ((Socks5AuthMethod) obj).a;
    }

    public int compareTo(Socks5AuthMethod socks5AuthMethod) {
        return this.a - socks5AuthMethod.a;
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
