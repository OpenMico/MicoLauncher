package io.netty.handler.codec.dns;

import io.netty.channel.AddressedEnvelope;
import java.net.InetSocketAddress;

/* loaded from: classes4.dex */
public class DatagramDnsQuery extends DefaultDnsQuery implements AddressedEnvelope<DatagramDnsQuery, InetSocketAddress> {
    private final InetSocketAddress a;
    private final InetSocketAddress b;

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.netty.channel.AddressedEnvelope
    public DatagramDnsQuery content() {
        return this;
    }

    public DatagramDnsQuery(InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, int i) {
        this(inetSocketAddress, inetSocketAddress2, i, DnsOpCode.QUERY);
    }

    public DatagramDnsQuery(InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, int i, DnsOpCode dnsOpCode) {
        super(i, dnsOpCode);
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

    @Override // io.netty.handler.codec.dns.DefaultDnsQuery, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DatagramDnsQuery setId(int i) {
        return (DatagramDnsQuery) super.setId(i);
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsQuery, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DatagramDnsQuery setOpCode(DnsOpCode dnsOpCode) {
        return (DatagramDnsQuery) super.setOpCode(dnsOpCode);
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsQuery, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DatagramDnsQuery setRecursionDesired(boolean z) {
        return (DatagramDnsQuery) super.setRecursionDesired(z);
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsQuery, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DatagramDnsQuery setZ(int i) {
        return (DatagramDnsQuery) super.setZ(i);
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsQuery, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DatagramDnsQuery setRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        return (DatagramDnsQuery) super.setRecord(dnsSection, dnsRecord);
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsQuery, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DatagramDnsQuery addRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        return (DatagramDnsQuery) super.addRecord(dnsSection, dnsRecord);
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsQuery, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DatagramDnsQuery addRecord(DnsSection dnsSection, int i, DnsRecord dnsRecord) {
        return (DatagramDnsQuery) super.addRecord(dnsSection, i, dnsRecord);
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsQuery, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DatagramDnsQuery clear(DnsSection dnsSection) {
        return (DatagramDnsQuery) super.clear(dnsSection);
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsQuery, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.handler.codec.dns.DnsMessage
    public DatagramDnsQuery clear() {
        return (DatagramDnsQuery) super.clear();
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsQuery, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public DatagramDnsQuery touch() {
        return (DatagramDnsQuery) super.touch();
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsQuery, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.util.ReferenceCounted
    public DatagramDnsQuery touch(Object obj) {
        return (DatagramDnsQuery) super.touch(obj);
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsQuery, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public DatagramDnsQuery retain() {
        return (DatagramDnsQuery) super.retain();
    }

    @Override // io.netty.handler.codec.dns.DefaultDnsQuery, io.netty.handler.codec.dns.AbstractDnsMessage, io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public DatagramDnsQuery retain(int i) {
        return (DatagramDnsQuery) super.retain(i);
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
