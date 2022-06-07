package io.netty.handler.codec.dns;

/* loaded from: classes4.dex */
public class DefaultDnsQuery extends AbstractDnsMessage implements DnsQuery {
    public DefaultDnsQuery(int i) {
        super(i);
    }

    public DefaultDnsQuery(int i, DnsOpCode dnsOpCode) {
        super(i, dnsOpCode);
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DnsQuery setId(int i) {
        return (DnsQuery) super.setId(i);
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DnsQuery setOpCode(DnsOpCode dnsOpCode) {
        return (DnsQuery) super.setOpCode(dnsOpCode);
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DnsQuery setRecursionDesired(boolean z) {
        return (DnsQuery) super.setRecursionDesired(z);
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DnsQuery setZ(int i) {
        return (DnsQuery) super.setZ(i);
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DnsQuery setRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        return (DnsQuery) super.setRecord(dnsSection, dnsRecord);
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DnsQuery addRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        return (DnsQuery) super.addRecord(dnsSection, dnsRecord);
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DnsQuery addRecord(DnsSection dnsSection, int i, DnsRecord dnsRecord) {
        return (DnsQuery) super.addRecord(dnsSection, i, dnsRecord);
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DnsQuery clear(DnsSection dnsSection) {
        return (DnsQuery) super.clear(dnsSection);
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DnsQuery clear() {
        return (DnsQuery) super.clear();
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public DnsQuery touch() {
        return (DnsQuery) super.touch();
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.util.ReferenceCounted
    public DnsQuery touch(Object obj) {
        return (DnsQuery) super.touch(obj);
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public DnsQuery retain() {
        return (DnsQuery) super.retain();
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public DnsQuery retain(int i) {
        return (DnsQuery) super.retain(i);
    }

    public String toString() {
        return a.a(new StringBuilder(128), (DnsQuery) this).toString();
    }
}
