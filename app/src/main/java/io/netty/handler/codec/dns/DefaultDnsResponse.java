package io.netty.handler.codec.dns;

import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public class DefaultDnsResponse extends AbstractDnsMessage implements DnsResponse {
    private boolean a;
    private boolean b;
    private boolean c;
    private DnsResponseCode d;

    public DefaultDnsResponse(int i) {
        this(i, DnsOpCode.QUERY, DnsResponseCode.NOERROR);
    }

    public DefaultDnsResponse(int i, DnsOpCode dnsOpCode) {
        this(i, dnsOpCode, DnsResponseCode.NOERROR);
    }

    public DefaultDnsResponse(int i, DnsOpCode dnsOpCode, DnsResponseCode dnsResponseCode) {
        super(i, dnsOpCode);
        setCode(dnsResponseCode);
    }

    @Override // io.netty.handler.codec.dns.DnsResponse
    public boolean isAuthoritativeAnswer() {
        return this.a;
    }

    public DnsResponse setAuthoritativeAnswer(boolean z) {
        this.a = z;
        return this;
    }

    @Override // io.netty.handler.codec.dns.DnsResponse
    public boolean isTruncated() {
        return this.b;
    }

    public DnsResponse setTruncated(boolean z) {
        this.b = z;
        return this;
    }

    @Override // io.netty.handler.codec.dns.DnsResponse
    public boolean isRecursionAvailable() {
        return this.c;
    }

    public DnsResponse setRecursionAvailable(boolean z) {
        this.c = z;
        return this;
    }

    @Override // io.netty.handler.codec.dns.DnsResponse
    public DnsResponseCode code() {
        return this.d;
    }

    public DnsResponse setCode(DnsResponseCode dnsResponseCode) {
        this.d = (DnsResponseCode) ObjectUtil.checkNotNull(dnsResponseCode, "code");
        return this;
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DnsResponse setId(int i) {
        return (DnsResponse) super.setId(i);
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DnsResponse setOpCode(DnsOpCode dnsOpCode) {
        return (DnsResponse) super.setOpCode(dnsOpCode);
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DnsResponse setRecursionDesired(boolean z) {
        return (DnsResponse) super.setRecursionDesired(z);
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DnsResponse setZ(int i) {
        return (DnsResponse) super.setZ(i);
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DnsResponse setRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        return (DnsResponse) super.setRecord(dnsSection, dnsRecord);
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DnsResponse addRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        return (DnsResponse) super.addRecord(dnsSection, dnsRecord);
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DnsResponse addRecord(DnsSection dnsSection, int i, DnsRecord dnsRecord) {
        return (DnsResponse) super.addRecord(dnsSection, i, dnsRecord);
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DnsResponse clear(DnsSection dnsSection) {
        return (DnsResponse) super.clear(dnsSection);
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DnsResponse clear() {
        return (DnsResponse) super.clear();
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public DnsResponse touch() {
        return (DnsResponse) super.touch();
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.util.ReferenceCounted
    public DnsResponse touch(Object obj) {
        return (DnsResponse) super.touch(obj);
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public DnsResponse retain() {
        return (DnsResponse) super.retain();
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public DnsResponse retain(int i) {
        return (DnsResponse) super.retain(i);
    }

    public String toString() {
        return a.a(new StringBuilder(128), (DnsResponse) this).toString();
    }
}
