package io.netty.handler.codec.dns;

import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public class DnsOpCode implements Comparable<DnsOpCode> {
    private final byte a;
    private final String b;
    private String c;
    public static final DnsOpCode QUERY = new DnsOpCode(0, "QUERY");
    public static final DnsOpCode IQUERY = new DnsOpCode(1, "IQUERY");
    public static final DnsOpCode STATUS = new DnsOpCode(2, "STATUS");
    public static final DnsOpCode NOTIFY = new DnsOpCode(4, "NOTIFY");
    public static final DnsOpCode UPDATE = new DnsOpCode(5, "UPDATE");

    public static DnsOpCode valueOf(int i) {
        switch (i) {
            case 0:
                return QUERY;
            case 1:
                return IQUERY;
            case 2:
                return STATUS;
            case 3:
            default:
                return new DnsOpCode(i);
            case 4:
                return NOTIFY;
            case 5:
                return UPDATE;
        }
    }

    private DnsOpCode(int i) {
        this(i, "UNKNOWN");
    }

    public DnsOpCode(int i, String str) {
        this.a = (byte) i;
        this.b = (String) ObjectUtil.checkNotNull(str, "name");
    }

    public byte byteValue() {
        return this.a;
    }

    public int hashCode() {
        return this.a;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof DnsOpCode) && this.a == ((DnsOpCode) obj).a;
    }

    public int compareTo(DnsOpCode dnsOpCode) {
        return this.a - dnsOpCode.a;
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
