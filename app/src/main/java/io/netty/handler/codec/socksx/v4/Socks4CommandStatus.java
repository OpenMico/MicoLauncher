package io.netty.handler.codec.socksx.v4;

/* loaded from: classes4.dex */
public class Socks4CommandStatus implements Comparable<Socks4CommandStatus> {
    private final byte a;
    private final String b;
    private String c;
    public static final Socks4CommandStatus SUCCESS = new Socks4CommandStatus(90, "SUCCESS");
    public static final Socks4CommandStatus REJECTED_OR_FAILED = new Socks4CommandStatus(91, "REJECTED_OR_FAILED");
    public static final Socks4CommandStatus IDENTD_UNREACHABLE = new Socks4CommandStatus(92, "IDENTD_UNREACHABLE");
    public static final Socks4CommandStatus IDENTD_AUTH_FAILURE = new Socks4CommandStatus(93, "IDENTD_AUTH_FAILURE");

    public static Socks4CommandStatus valueOf(byte b) {
        switch (b) {
            case 90:
                return SUCCESS;
            case 91:
                return REJECTED_OR_FAILED;
            case 92:
                return IDENTD_UNREACHABLE;
            case 93:
                return IDENTD_AUTH_FAILURE;
            default:
                return new Socks4CommandStatus(b);
        }
    }

    public Socks4CommandStatus(int i) {
        this(i, "UNKNOWN");
    }

    public Socks4CommandStatus(int i, String str) {
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
        return this.a == 90;
    }

    public int hashCode() {
        return this.a;
    }

    public boolean equals(Object obj) {
        return (obj instanceof Socks4CommandStatus) && this.a == ((Socks4CommandStatus) obj).a;
    }

    public int compareTo(Socks4CommandStatus socks4CommandStatus) {
        return this.a - socks4CommandStatus.a;
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
