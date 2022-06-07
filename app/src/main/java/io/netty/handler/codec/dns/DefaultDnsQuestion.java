package io.netty.handler.codec.dns;

import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public class DefaultDnsQuestion extends AbstractDnsRecord implements DnsQuestion {
    public DefaultDnsQuestion(String str, DnsRecordType dnsRecordType) {
        super(str, dnsRecordType, 0L);
    }

    public DefaultDnsQuestion(String str, DnsRecordType dnsRecordType, int i) {
        super(str, dnsRecordType, i, 0L);
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsRecord
    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(StringUtil.simpleClassName(this));
        sb.append('(');
        sb.append(name());
        sb.append(' ');
        StringBuilder a = a.a(sb, dnsClass());
        a.append(' ');
        a.append(type().name());
        a.append(')');
        return sb.toString();
    }
}
