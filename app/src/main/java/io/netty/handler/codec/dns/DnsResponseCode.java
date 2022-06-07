package io.netty.handler.codec.dns;

import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public class DnsResponseCode implements Comparable<DnsResponseCode> {
    private final int a;
    private final String b;
    private String c;
    public static final DnsResponseCode NOERROR = new DnsResponseCode(0, "NoError");
    public static final DnsResponseCode FORMERR = new DnsResponseCode(1, "FormErr");
    public static final DnsResponseCode SERVFAIL = new DnsResponseCode(2, "ServFail");
    public static final DnsResponseCode NXDOMAIN = new DnsResponseCode(3, "NXDomain");
    public static final DnsResponseCode NOTIMP = new DnsResponseCode(4, "NotImp");
    public static final DnsResponseCode REFUSED = new DnsResponseCode(5, "Refused");
    public static final DnsResponseCode YXDOMAIN = new DnsResponseCode(6, "YXDomain");
    public static final DnsResponseCode YXRRSET = new DnsResponseCode(7, "YXRRSet");
    public static final DnsResponseCode NXRRSET = new DnsResponseCode(8, "NXRRSet");
    public static final DnsResponseCode NOTAUTH = new DnsResponseCode(9, "NotAuth");
    public static final DnsResponseCode NOTZONE = new DnsResponseCode(10, "NotZone");
    public static final DnsResponseCode BADVERS_OR_BADSIG = new DnsResponseCode(16, "BADVERS_OR_BADSIG");
    public static final DnsResponseCode BADKEY = new DnsResponseCode(17, "BADKEY");
    public static final DnsResponseCode BADTIME = new DnsResponseCode(18, "BADTIME");
    public static final DnsResponseCode BADMODE = new DnsResponseCode(19, "BADMODE");
    public static final DnsResponseCode BADNAME = new DnsResponseCode(20, "BADNAME");
    public static final DnsResponseCode BADALG = new DnsResponseCode(21, "BADALG");

    public static DnsResponseCode valueOf(int i) {
        switch (i) {
            case 0:
                return NOERROR;
            case 1:
                return FORMERR;
            case 2:
                return SERVFAIL;
            case 3:
                return NXDOMAIN;
            case 4:
                return NOTIMP;
            case 5:
                return REFUSED;
            case 6:
                return YXDOMAIN;
            case 7:
                return YXRRSET;
            case 8:
                return NXRRSET;
            case 9:
                return NOTAUTH;
            case 10:
                return NOTZONE;
            default:
                switch (i) {
                    case 16:
                        return BADVERS_OR_BADSIG;
                    case 17:
                        return BADKEY;
                    case 18:
                        return BADTIME;
                    case 19:
                        return BADMODE;
                    case 20:
                        return BADNAME;
                    case 21:
                        return BADALG;
                    default:
                        return new DnsResponseCode(i);
                }
        }
    }

    private DnsResponseCode(int i) {
        this(i, "UNKNOWN");
    }

    public DnsResponseCode(int i, String str) {
        if (i < 0 || i > 65535) {
            throw new IllegalArgumentException("code: " + i + " (expected: 0 ~ 65535)");
        }
        this.a = i;
        this.b = (String) ObjectUtil.checkNotNull(str, "name");
    }

    public int intValue() {
        return this.a;
    }

    public int compareTo(DnsResponseCode dnsResponseCode) {
        return intValue() - dnsResponseCode.intValue();
    }

    public int hashCode() {
        return intValue();
    }

    public boolean equals(Object obj) {
        return (obj instanceof DnsResponseCode) && intValue() == ((DnsResponseCode) obj).intValue();
    }

    public String toString() {
        String str = this.c;
        if (str != null) {
            return str;
        }
        String str2 = this.b + '(' + intValue() + ')';
        this.c = str2;
        return str2;
    }
}
