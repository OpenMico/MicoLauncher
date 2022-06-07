package io.netty.handler.codec.dns;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.net.IDN;
import kotlin.UShort;

/* loaded from: classes4.dex */
public abstract class AbstractDnsRecord implements DnsRecord {
    private final String a;
    private final DnsRecordType b;
    private final short c;
    private final long d;
    private int e;

    public AbstractDnsRecord(String str, DnsRecordType dnsRecordType, long j) {
        this(str, dnsRecordType, 1, j);
    }

    public AbstractDnsRecord(String str, DnsRecordType dnsRecordType, int i, long j) {
        if (j >= 0) {
            this.a = a(IDN.toASCII((String) ObjectUtil.checkNotNull(str, "name")));
            this.b = (DnsRecordType) ObjectUtil.checkNotNull(dnsRecordType, "type");
            this.c = (short) i;
            this.d = j;
            return;
        }
        throw new IllegalArgumentException("timeToLive: " + j + " (expected: >= 0)");
    }

    private static String a(String str) {
        if (str.length() <= 0 || str.charAt(str.length() - 1) == '.') {
            return str;
        }
        return str + '.';
    }

    @Override // io.netty.handler.codec.dns.DnsRecord
    public String name() {
        return this.a;
    }

    @Override // io.netty.handler.codec.dns.DnsRecord
    public DnsRecordType type() {
        return this.b;
    }

    @Override // io.netty.handler.codec.dns.DnsRecord
    public int dnsClass() {
        return this.c & UShort.MAX_VALUE;
    }

    @Override // io.netty.handler.codec.dns.DnsRecord
    public long timeToLive() {
        return this.d;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DnsRecord)) {
            return false;
        }
        DnsRecord dnsRecord = (DnsRecord) obj;
        int i = this.e;
        if (i == 0 || i == dnsRecord.hashCode()) {
            return type().intValue() == dnsRecord.type().intValue() && dnsClass() == dnsRecord.dnsClass() && name().equals(dnsRecord.name());
        }
        return false;
    }

    public int hashCode() {
        int i = this.e;
        if (i != 0) {
            return i;
        }
        int hashCode = (this.a.hashCode() * 31) + (type().intValue() * 31) + dnsClass();
        this.e = hashCode;
        return hashCode;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(StringUtil.simpleClassName(this));
        sb.append('(');
        sb.append(name());
        sb.append(' ');
        sb.append(timeToLive());
        sb.append(' ');
        StringBuilder a = a.a(sb, dnsClass());
        a.append(' ');
        a.append(type().name());
        a.append(')');
        return sb.toString();
    }
}
