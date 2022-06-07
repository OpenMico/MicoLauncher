package io.netty.handler.codec.dns;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public class DefaultDnsPtrRecord extends AbstractDnsRecord implements DnsPtrRecord {
    private final String a;

    public DefaultDnsPtrRecord(String str, int i, long j, String str2) {
        super(str, DnsRecordType.PTR, i, j);
        this.a = (String) ObjectUtil.checkNotNull(str2, "hostname");
    }

    @Override // io.netty.handler.codec.dns.DnsPtrRecord
    public String hostname() {
        return this.a;
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsRecord
    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(StringUtil.simpleClassName(this));
        sb.append('(');
        DnsRecordType type = type();
        sb.append(name().isEmpty() ? "<root>" : name());
        sb.append(' ');
        sb.append(timeToLive());
        sb.append(' ');
        StringBuilder a = a.a(sb, dnsClass());
        a.append(' ');
        a.append(type.name());
        sb.append(' ');
        sb.append(this.a);
        return sb.toString();
    }
}
