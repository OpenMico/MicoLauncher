package io.netty.handler.codec.dns;

import io.netty.buffer.ByteBuf;

/* loaded from: classes4.dex */
public interface DnsRecordDecoder {
    public static final DnsRecordDecoder DEFAULT = new DefaultDnsRecordDecoder();

    DnsQuestion decodeQuestion(ByteBuf byteBuf) throws Exception;

    <T extends DnsRecord> T decodeRecord(ByteBuf byteBuf) throws Exception;
}
