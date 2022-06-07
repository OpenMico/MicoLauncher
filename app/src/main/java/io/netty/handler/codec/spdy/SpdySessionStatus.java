package io.netty.handler.codec.spdy;

/* loaded from: classes4.dex */
public class SpdySessionStatus implements Comparable<SpdySessionStatus> {
    private final int a;
    private final String b;
    public static final SpdySessionStatus OK = new SpdySessionStatus(0, "OK");
    public static final SpdySessionStatus PROTOCOL_ERROR = new SpdySessionStatus(1, "PROTOCOL_ERROR");
    public static final SpdySessionStatus INTERNAL_ERROR = new SpdySessionStatus(2, "INTERNAL_ERROR");

    public static SpdySessionStatus valueOf(int i) {
        switch (i) {
            case 0:
                return OK;
            case 1:
                return PROTOCOL_ERROR;
            case 2:
                return INTERNAL_ERROR;
            default:
                return new SpdySessionStatus(i, "UNKNOWN (" + i + ')');
        }
    }

    public SpdySessionStatus(int i, String str) {
        if (str != null) {
            this.a = i;
            this.b = str;
            return;
        }
        throw new NullPointerException("statusPhrase");
    }

    public int code() {
        return this.a;
    }

    public String statusPhrase() {
        return this.b;
    }

    public int hashCode() {
        return code();
    }

    public boolean equals(Object obj) {
        return (obj instanceof SpdySessionStatus) && code() == ((SpdySessionStatus) obj).code();
    }

    public String toString() {
        return statusPhrase();
    }

    public int compareTo(SpdySessionStatus spdySessionStatus) {
        return code() - spdySessionStatus.code();
    }
}
