package io.netty.handler.codec.dns;

import io.netty.buffer.ByteBuf;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.internal.ObjectUtil;
import java.net.InetSocketAddress;
import java.util.List;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public class DatagramDnsResponseEncoder extends MessageToMessageEncoder<AddressedEnvelope<DnsResponse, InetSocketAddress>> {
    private final DnsRecordEncoder a;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.MessageToMessageEncoder
    public /* bridge */ /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, AddressedEnvelope<DnsResponse, InetSocketAddress> addressedEnvelope, List list) throws Exception {
        encode2(channelHandlerContext, addressedEnvelope, (List<Object>) list);
    }

    public DatagramDnsResponseEncoder() {
        this(DnsRecordEncoder.DEFAULT);
    }

    public DatagramDnsResponseEncoder(DnsRecordEncoder dnsRecordEncoder) {
        this.a = (DnsRecordEncoder) ObjectUtil.checkNotNull(dnsRecordEncoder, "recordEncoder");
    }

    /* renamed from: encode  reason: avoid collision after fix types in other method */
    protected void encode2(ChannelHandlerContext channelHandlerContext, AddressedEnvelope<DnsResponse, InetSocketAddress> addressedEnvelope, List<Object> list) throws Exception {
        InetSocketAddress recipient = addressedEnvelope.recipient();
        DnsResponse content = addressedEnvelope.content();
        ByteBuf allocateBuffer = allocateBuffer(channelHandlerContext, addressedEnvelope);
        try {
            a(content, allocateBuffer);
            b(content, allocateBuffer);
            a(content, DnsSection.ANSWER, allocateBuffer);
            a(content, DnsSection.AUTHORITY, allocateBuffer);
            a(content, DnsSection.ADDITIONAL, allocateBuffer);
            list.add(new DatagramPacket(allocateBuffer, recipient, null));
        } catch (Throwable th) {
            allocateBuffer.release();
            throw th;
        }
    }

    protected ByteBuf allocateBuffer(ChannelHandlerContext channelHandlerContext, AddressedEnvelope<DnsResponse, InetSocketAddress> addressedEnvelope) throws Exception {
        return channelHandlerContext.alloc().ioBuffer(1024);
    }

    private static void a(DnsResponse dnsResponse, ByteBuf byteBuf) {
        byteBuf.writeShort(dnsResponse.id());
        int byteValue = ((dnsResponse.opCode().byteValue() & 255) << 11) | 32768;
        if (dnsResponse.isAuthoritativeAnswer()) {
            byteValue |= 1024;
        }
        if (dnsResponse.isTruncated()) {
            byteValue |= 512;
        }
        if (dnsResponse.isRecursionDesired()) {
            byteValue |= 256;
        }
        if (dnsResponse.isRecursionAvailable()) {
            byteValue |= 128;
        }
        byteBuf.writeShort(byteValue | (dnsResponse.z() << 4) | dnsResponse.code().intValue());
        byteBuf.writeShort(dnsResponse.count(DnsSection.QUESTION));
        byteBuf.writeShort(dnsResponse.count(DnsSection.ANSWER));
        byteBuf.writeShort(dnsResponse.count(DnsSection.AUTHORITY));
        byteBuf.writeShort(dnsResponse.count(DnsSection.ADDITIONAL));
    }

    private void b(DnsResponse dnsResponse, ByteBuf byteBuf) throws Exception {
        int count = dnsResponse.count(DnsSection.QUESTION);
        for (int i = 0; i < count; i++) {
            this.a.encodeQuestion((DnsQuestion) dnsResponse.recordAt(DnsSection.QUESTION, i), byteBuf);
        }
    }

    private void a(DnsResponse dnsResponse, DnsSection dnsSection, ByteBuf byteBuf) throws Exception {
        int count = dnsResponse.count(dnsSection);
        for (int i = 0; i < count; i++) {
            this.a.encodeRecord(dnsResponse.recordAt(dnsSection, i), byteBuf);
        }
    }
}
