package io.netty.handler.codec.dns;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public class DefaultDnsRecordEncoder implements DnsRecordEncoder {
    @Override // io.netty.handler.codec.dns.DnsRecordEncoder
    public final void encodeQuestion(DnsQuestion dnsQuestion, ByteBuf byteBuf) throws Exception {
        encodeName(dnsQuestion.name(), byteBuf);
        byteBuf.writeShort(dnsQuestion.type().intValue());
        byteBuf.writeShort(dnsQuestion.dnsClass());
    }

    @Override // io.netty.handler.codec.dns.DnsRecordEncoder
    public void encodeRecord(DnsRecord dnsRecord, ByteBuf byteBuf) throws Exception {
        if (dnsRecord instanceof DnsQuestion) {
            encodeQuestion((DnsQuestion) dnsRecord, byteBuf);
        } else if (dnsRecord instanceof DnsPtrRecord) {
            a((DnsPtrRecord) dnsRecord, byteBuf);
        } else if (dnsRecord instanceof DnsRawRecord) {
            a((DnsRawRecord) dnsRecord, byteBuf);
        } else {
            throw new UnsupportedMessageTypeException(StringUtil.simpleClassName(dnsRecord));
        }
    }

    private void a(DnsPtrRecord dnsPtrRecord, ByteBuf byteBuf) throws Exception {
        encodeName(dnsPtrRecord.name(), byteBuf);
        byteBuf.writeShort(dnsPtrRecord.type().intValue());
        byteBuf.writeShort(dnsPtrRecord.dnsClass());
        byteBuf.writeInt((int) dnsPtrRecord.timeToLive());
        encodeName(dnsPtrRecord.hostname(), byteBuf);
    }

    private void a(DnsRawRecord dnsRawRecord, ByteBuf byteBuf) throws Exception {
        encodeName(dnsRawRecord.name(), byteBuf);
        byteBuf.writeShort(dnsRawRecord.type().intValue());
        byteBuf.writeShort(dnsRawRecord.dnsClass());
        byteBuf.writeInt((int) dnsRawRecord.timeToLive());
        ByteBuf content = dnsRawRecord.content();
        int readableBytes = content.readableBytes();
        byteBuf.writeShort(readableBytes);
        byteBuf.writeBytes(content, content.readerIndex(), readableBytes);
    }

    protected void encodeName(String str, ByteBuf byteBuf) throws Exception {
        if (".".equals(str)) {
            byteBuf.writeByte(0);
            return;
        }
        String[] split = StringUtil.split(str, '.');
        for (String str2 : split) {
            int length = str2.length();
            if (length == 0) {
                break;
            }
            byteBuf.writeByte(length);
            ByteBufUtil.writeAscii(byteBuf, str2);
        }
        byteBuf.writeByte(0);
    }
}
