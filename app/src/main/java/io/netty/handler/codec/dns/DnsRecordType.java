package io.netty.handler.codec.dns;

import com.umeng.commonsdk.internal.a;
import com.xiaomi.micolauncher.api.model.Video;
import io.netty.util.collection.IntObjectHashMap;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class DnsRecordType implements Comparable<DnsRecordType> {
    private static final String c;
    private final int d;
    private final String e;
    private String f;
    public static final DnsRecordType A = new DnsRecordType(1, "A");
    public static final DnsRecordType NS = new DnsRecordType(2, "NS");
    public static final DnsRecordType CNAME = new DnsRecordType(5, "CNAME");
    public static final DnsRecordType SOA = new DnsRecordType(6, "SOA");
    public static final DnsRecordType PTR = new DnsRecordType(12, "PTR");
    public static final DnsRecordType MX = new DnsRecordType(15, "MX");
    public static final DnsRecordType TXT = new DnsRecordType(16, "TXT");
    public static final DnsRecordType RP = new DnsRecordType(17, "RP");
    public static final DnsRecordType AFSDB = new DnsRecordType(18, "AFSDB");
    public static final DnsRecordType SIG = new DnsRecordType(24, "SIG");
    public static final DnsRecordType KEY = new DnsRecordType(25, "KEY");
    public static final DnsRecordType AAAA = new DnsRecordType(28, "AAAA");
    public static final DnsRecordType LOC = new DnsRecordType(29, "LOC");
    public static final DnsRecordType SRV = new DnsRecordType(33, "SRV");
    public static final DnsRecordType NAPTR = new DnsRecordType(35, "NAPTR");
    public static final DnsRecordType KX = new DnsRecordType(36, "KX");
    public static final DnsRecordType CERT = new DnsRecordType(37, "CERT");
    public static final DnsRecordType DNAME = new DnsRecordType(39, "DNAME");
    public static final DnsRecordType OPT = new DnsRecordType(41, "OPT");
    public static final DnsRecordType APL = new DnsRecordType(42, "APL");
    public static final DnsRecordType DS = new DnsRecordType(43, "DS");
    public static final DnsRecordType SSHFP = new DnsRecordType(44, "SSHFP");
    public static final DnsRecordType IPSECKEY = new DnsRecordType(45, "IPSECKEY");
    public static final DnsRecordType RRSIG = new DnsRecordType(46, "RRSIG");
    public static final DnsRecordType NSEC = new DnsRecordType(47, "NSEC");
    public static final DnsRecordType DNSKEY = new DnsRecordType(48, "DNSKEY");
    public static final DnsRecordType DHCID = new DnsRecordType(49, "DHCID");
    public static final DnsRecordType NSEC3 = new DnsRecordType(50, "NSEC3");
    public static final DnsRecordType NSEC3PARAM = new DnsRecordType(51, "NSEC3PARAM");
    public static final DnsRecordType TLSA = new DnsRecordType(52, "TLSA");
    public static final DnsRecordType HIP = new DnsRecordType(55, "HIP");
    public static final DnsRecordType SPF = new DnsRecordType(99, "SPF");
    public static final DnsRecordType TKEY = new DnsRecordType(249, "TKEY");
    public static final DnsRecordType TSIG = new DnsRecordType(250, "TSIG");
    public static final DnsRecordType IXFR = new DnsRecordType(251, "IXFR");
    public static final DnsRecordType AXFR = new DnsRecordType(252, "AXFR");
    public static final DnsRecordType ANY = new DnsRecordType(255, Video.Category.ANY_CATEGORY_TYPE);
    public static final DnsRecordType CAA = new DnsRecordType(257, "CAA");
    public static final DnsRecordType TA = new DnsRecordType(32768, "TA");
    public static final DnsRecordType DLV = new DnsRecordType(a.f, "DLV");
    private static final Map<String, DnsRecordType> a = new HashMap();
    private static final IntObjectHashMap<DnsRecordType> b = new IntObjectHashMap<>();

    static {
        DnsRecordType[] dnsRecordTypeArr = {A, NS, CNAME, SOA, PTR, MX, TXT, RP, AFSDB, SIG, KEY, AAAA, LOC, SRV, NAPTR, KX, CERT, DNAME, OPT, APL, DS, SSHFP, IPSECKEY, RRSIG, NSEC, DNSKEY, DHCID, NSEC3, NSEC3PARAM, TLSA, HIP, SPF, TKEY, TSIG, IXFR, AXFR, ANY, CAA, TA, DLV};
        StringBuilder sb = new StringBuilder(512);
        sb.append(" (expected: ");
        for (DnsRecordType dnsRecordType : dnsRecordTypeArr) {
            a.put(dnsRecordType.name(), dnsRecordType);
            b.put(dnsRecordType.intValue(), (int) dnsRecordType);
            sb.append(dnsRecordType.name());
            sb.append('(');
            sb.append(dnsRecordType.intValue());
            sb.append("), ");
        }
        sb.setLength(sb.length() - 2);
        sb.append(')');
        c = sb.toString();
    }

    public static DnsRecordType valueOf(int i) {
        DnsRecordType dnsRecordType = b.get(i);
        return dnsRecordType == null ? new DnsRecordType(i) : dnsRecordType;
    }

    public static DnsRecordType valueOf(String str) {
        DnsRecordType dnsRecordType = a.get(str);
        if (dnsRecordType != null) {
            return dnsRecordType;
        }
        throw new IllegalArgumentException("name: " + str + c);
    }

    private DnsRecordType(int i) {
        this(i, "UNKNOWN");
    }

    public DnsRecordType(int i, String str) {
        if ((65535 & i) == i) {
            this.d = i;
            this.e = str;
            return;
        }
        throw new IllegalArgumentException("intValue: " + i + " (expected: 0 ~ 65535)");
    }

    public String name() {
        return this.e;
    }

    public int intValue() {
        return this.d;
    }

    public int hashCode() {
        return this.d;
    }

    public boolean equals(Object obj) {
        return (obj instanceof DnsRecordType) && ((DnsRecordType) obj).d == this.d;
    }

    public int compareTo(DnsRecordType dnsRecordType) {
        return intValue() - dnsRecordType.intValue();
    }

    public String toString() {
        String str = this.f;
        if (str != null) {
            return str;
        }
        String str2 = this.e + '(' + intValue() + ')';
        this.f = str2;
        return str2;
    }
}
