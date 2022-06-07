package io.netty.handler.codec.socksx.v5;

/* loaded from: classes4.dex */
public class Socks5PasswordAuthStatus implements Comparable<Socks5PasswordAuthStatus> {
    private final byte a;
    private final String b;
    private String c;
    public static final Socks5PasswordAuthStatus SUCCESS = new Socks5PasswordAuthStatus(0, "SUCCESS");
    public static final Socks5PasswordAuthStatus FAILURE = new Socks5PasswordAuthStatus(255, "FAILURE");

    public static Socks5PasswordAuthStatus valueOf(byte b) {
        switch (b) {
            case -1:
                return FAILURE;
            case 0:
                return SUCCESS;
            default:
                return new Socks5PasswordAuthStatus(b);
        }
    }

    public Socks5PasswordAuthStatus(int i) {
        this(i, "UNKNOWN");
    }

    public Socks5PasswordAuthStatus(int i, String str) {
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

    public boolean isSuccess() {
        return this.a == 0;
    }

    public int hashCode() {
        return this.a;
    }

    public boolean equals(Object obj) {
        return (obj instanceof Socks5PasswordAuthStatus) && this.a == ((Socks5PasswordAuthStatus) obj).a;
    }

    public int compareTo(Socks5PasswordAuthStatus socks5PasswordAuthStatus) {
        return this.a - socks5PasswordAuthStatus.a;
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
