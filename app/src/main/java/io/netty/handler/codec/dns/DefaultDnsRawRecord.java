package io.netty.handler.codec.dns;

import io.netty.buffer.ByteBuf;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public class DefaultDnsRawRecord extends AbstractDnsRecord implements DnsRawRecord {
    private final ByteBuf a;

    public DefaultDnsRawRecord(String str, DnsRecordType dnsRecordType, long j, ByteBuf byteBuf) {
        this(str, dnsRecordType, 1, j, byteBuf);
    }

    public DefaultDnsRawRecord(String str, DnsRecordType dnsRecordType, int i, long j, ByteBuf byteBuf) {
        super(str, dnsRecordType, i, j);
        this.a = (ByteBuf) ObjectUtil.checkNotNull(byteBuf, "content");
    }

    @Override // io.netty.buffer.ByteBufHolder
    public ByteBuf content() {
        return this.a;
    }

    @Override // io.netty.handler.codec.dns.DnsRawRecord, io.netty.buffer.ByteBufHolder
    public DnsRawRecord copy() {
        return replace(content().copy());
    }

    @Override // io.netty.handler.codec.dns.DnsRawRecord, io.netty.buffer.ByteBufHolder
    public DnsRawRecord duplicate() {
        return replace(content().duplicate());
    }

    @Override // io.netty.handler.codec.dns.DnsRawRecord, io.netty.buffer.ByteBufHolder
    public DnsRawRecord retainedDuplicate() {
        return replace(content().retainedDuplicate());
    }

    @Override // io.netty.handler.codec.dns.DnsRawRecord, io.netty.buffer.ByteBufHolder
    public DnsRawRecord replace(ByteBuf byteBuf) {
        return new DefaultDnsRawRecord(name(), type(), dnsClass(), timeToLive(), byteBuf);
    }

    @Override // io.netty.util.ReferenceCounted
    public int refCnt() {
        return content().refCnt();
    }

    @Override // io.netty.handler.codec.dns.DnsRawRecord, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public DnsRawRecord retain() {
        content().retain();
        return this;
    }

    @Override // io.netty.handler.codec.dns.DnsRawRecord, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public DnsRawRecord retain(int i) {
        content().retain(i);
        return this;
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release() {
        return content().release();
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release(int i) {
        return content().release(i);
    }

    @Override // io.netty.handler.codec.dns.DnsRawRecord, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public DnsRawRecord touch() {
        content().touch();
        return this;
    }

    @Override // io.netty.handler.codec.dns.DnsRawRecord, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public DnsRawRecord touch(Object obj) {
        content().touch(obj);
        return this;
    }

    @Override // io.netty.handler.codec.dns.AbstractDnsRecord
    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(StringUtil.simpleClassName(this));
        sb.append('(');
        DnsRecordType type = type();
        if (type != DnsRecordType.OPT) {
            sb.append(name().isEmpty() ? "<root>" : name());
            sb.append(' ');
            sb.append(timeToLive());
            sb.append(' ');
            StringBuilder a = a.a(sb, dnsClass());
            a.append(' ');
            a.append(type.name());
        } else {
            sb.append("OPT flags:");
            sb.append(timeToLive());
            sb.append(" udp:");
            sb.append(dnsClass());
        }
        sb.append(' ');
        sb.append(content().readableBytes());
        sb.append("B)");
        return sb.toString();
    }
}
