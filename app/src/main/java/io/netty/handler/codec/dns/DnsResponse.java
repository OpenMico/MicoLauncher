package io.netty.handler.codec.dns;

/* loaded from: classes4.dex */
public interface DnsResponse extends DnsMessage {
    @Override // io.netty.handler.codec.dns.DnsMessage
    DnsResponse addRecord(DnsSection dnsSection, int i, DnsRecord dnsRecord);

    @Override // io.netty.handler.codec.dns.DnsMessage
    DnsResponse addRecord(DnsSection dnsSection, DnsRecord dnsRecord);

    @Override // io.netty.handler.codec.dns.DnsMessage
    DnsResponse clear();

    @Override // io.netty.handler.codec.dns.DnsMessage
    DnsResponse clear(DnsSection dnsSection);

    DnsResponseCode code();

    boolean isAuthoritativeAnswer();

    boolean isRecursionAvailable();

    boolean isTruncated();

    @Override // io.netty.handler.codec.dns.DnsMessage, io.netty.util.ReferenceCounted
    DnsResponse retain();

    @Override // io.netty.handler.codec.dns.DnsMessage, io.netty.util.ReferenceCounted
    DnsResponse retain(int i);

    DnsResponse setAuthoritativeAnswer(boolean z);

    DnsResponse setCode(DnsResponseCode dnsResponseCode);

    @Override // io.netty.handler.codec.dns.DnsMessage
    DnsResponse setId(int i);

    @Override // io.netty.handler.codec.dns.DnsMessage
    DnsResponse setOpCode(DnsOpCode dnsOpCode);

    @Override // io.netty.handler.codec.dns.DnsMessage
    DnsResponse setRecord(DnsSection dnsSection, DnsRecord dnsRecord);

    DnsResponse setRecursionAvailable(boolean z);

    @Override // io.netty.handler.codec.dns.DnsMessage
    DnsResponse setRecursionDesired(boolean z);

    DnsResponse setTruncated(boolean z);

    @Override // io.netty.handler.codec.dns.DnsMessage
    DnsResponse setZ(int i);

    @Override // io.netty.handler.codec.dns.DnsMessage, io.netty.util.ReferenceCounted
    DnsResponse touch();

    @Override // io.netty.handler.codec.dns.DnsMessage, io.netty.util.ReferenceCounted
    DnsResponse touch(Object obj);
}
