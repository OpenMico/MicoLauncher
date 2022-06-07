package io.netty.handler.codec.dns;

import io.netty.channel.AddressedEnvelope;
import java.net.InetSocketAddress;

/* loaded from: classes4.dex */
public class DatagramDnsResponse extends DefaultDnsResponse implements AddressedEnvelope<DatagramDnsResponse, InetSocketAddress> {
    private final InetSocketAddress a;
    private final InetSocketAddress b;

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.netty.channel.AddressedEnvelope
    public DatagramDnsResponse content() {
        return this;
    }

    public DatagramDnsResponse(InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, int i) {
        this(inetSocketAddress, inetSocketAddress2, i, DnsOpCode.QUERY, DnsResponseCode.NOERROR);
    }

    public DatagramDnsResponse(InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, int i, DnsOpCode dnsOpCode) {
        this(inetSocketAddress, inetSocketAddress2, i, dnsOpCode, DnsResponseCode.NOERROR);
    }

    public DatagramDnsResponse(InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, int i, DnsOpCode dnsOpCode, DnsResponseCode dnsResponseCode) {
        super(i, dnsOpCode, dnsResponseCode);
        if (inetSocketAddress2 == null && inetSocketAddress == null) {
            throw new NullPointerException("recipient and sender");
        }
        this.a = inetSocketAddress;
        this.b = inetSocketAddress2;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.netty.channel.AddressedEnvelope
    public InetSocketAddress sender() {
        return this.a;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.netty.channel.AddressedEnvelope
    public InetSocketAddress recipient() {
        return this.b;
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsResponse, io.netty.handler.codec.dns.DnsResponse
    public DatagramDnsResponse setAuthoritativeAnswer(boolean z) {
        return (DatagramDnsResponse) super.setAuthoritativeAnswer(z);
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsResponse, io.netty.handler.codec.dns.DnsResponse
    public DatagramDnsResponse setTruncated(boolean z) {
        return (DatagramDnsResponse) super.setTruncated(z);
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsResponse, io.netty.handler.codec.dns.DnsResponse
    public DatagramDnsResponse setRecursionAvailable(boolean z) {
        return (DatagramDnsResponse) super.setRecursionAvailable(z);
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsResponse, io.netty.handler.codec.dns.DnsResponse
    public DatagramDnsResponse setCode(DnsResponseCode dnsResponseCode) {
        return (DatagramDnsResponse) super.setCode(dnsResponseCode);
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsResponse, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DatagramDnsResponse setId(int i) {
        return (DatagramDnsResponse) super.setId(i);
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsResponse, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DatagramDnsResponse setOpCode(DnsOpCode dnsOpCode) {
        return (DatagramDnsResponse) super.setOpCode(dnsOpCode);
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsResponse, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DatagramDnsResponse setRecursionDesired(boolean z) {
        return (DatagramDnsResponse) super.setRecursionDesired(z);
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsResponse, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DatagramDnsResponse setZ(int i) {
        return (DatagramDnsResponse) super.setZ(i);
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsResponse, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DatagramDnsResponse setRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        return (DatagramDnsResponse) super.setRecord(dnsSection, dnsRecord);
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsResponse, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DatagramDnsResponse addRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        return (DatagramDnsResponse) super.addRecord(dnsSection, dnsRecord);
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsResponse, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DatagramDnsResponse addRecord(DnsSection dnsSection, int i, DnsRecord dnsRecord) {
        return (DatagramDnsResponse) super.addRecord(dnsSection, i, dnsRecord);
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsResponse, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DatagramDnsResponse clear(DnsSection dnsSection) {
        return (DatagramDnsResponse) super.clear(dnsSection);
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsResponse, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DatagramDnsResponse clear() {
        return (DatagramDnsResponse) super.clear();
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsResponse, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public DatagramDnsResponse touch() {
        return (DatagramDnsResponse) super.touch();
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsResponse, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.util.ReferenceCounted
    public DatagramDnsResponse touch(Object obj) {
        return (DatagramDnsResponse) super.touch(obj);
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsResponse, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public DatagramDnsResponse retain() {
        return (DatagramDnsResponse) super.retain();
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsResponse, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public DatagramDnsResponse retain(int i) {
        return (DatagramDnsResponse) super.retain(i);
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || !(obj instanceof AddressedEnvelope)) {
            return false;
        }
        AddressedEnvelope addressedEnvelope = (AddressedEnvelope) obj;
        if (sender() == null) {
            if (addressedEnvelope.sender() != null) {
                return false;
            }
        } else if (!sender().equals(addressedEnvelope.sender())) {
            return false;
        }
        if (recipient() == null) {
            if (addressedEnvelope.recipient() != null) {
                return false;
            }
        } else if (!recipient().equals(addressedEnvelope.recipient())) {
            return false;
        }
        return true;
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsMessage
    public int hashCode() {
        int hashCode = super.hashCode();
        if (sender() != null) {
            hashCode = (hashCode * 31) + sender().hashCode();
        }
        return recipient() != null ? (hashCode * 31) + recipient().hashCode() : hashCode;
    }
}
