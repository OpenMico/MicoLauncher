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
public class DatagramDnsQueryEncoder extends MessageToMessageEncoder<AddressedEnvelope<DnsQuery, InetSocketAddress>> {
    private final DnsRecordEncoder a;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.MessageToMessageEncoder
    public /* bridge */ /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, AddressedEnvelope<DnsQuery, InetSocketAddress> addressedEnvelope, List list) throws Exception {
        encode2(channelHandlerContext, addressedEnvelope, (List<Object>) list);
    }

    public DatagramDnsQueryEncoder() {
        this(DnsRecordEncoder.DEFAULT);
    }

    public DatagramDnsQueryEncoder(DnsRecordEncoder dnsRecordEncoder) {
        this.a = (DnsRecordEncoder) ObjectUtil.checkNotNull(dnsRecordEncoder, "recordEncoder");
    }

    /* renamed from: encode  reason: avoid collision after fix types in other method */
    protected void encode2(ChannelHandlerContext channelHandlerContext, AddressedEnvelope<DnsQuery, InetSocketAddress> addressedEnvelope, List<Object> list) throws Exception {
        InetSocketAddress recipient = addressedEnvelope.recipient();
        DnsQuery content = addressedEnvelope.content();
        ByteBuf allocateBuffer = allocateBuffer(channelHandlerContext, addressedEnvelope);
        try {
            a(content, allocateBuffer);
            b(content, allocateBuffer);
            a(content, DnsSection.ADDITIONAL, allocateBuffer);
            list.add(new DatagramPacket(allocateBuffer, recipient, null));
        } catch (Throwable th) {
            allocateBuffer.release();
            throw th;
        }
    }

    protected ByteBuf allocateBuffer(ChannelHandlerContext channelHandlerContext, AddressedEnvelope<DnsQuery, InetSocketAddress> addressedEnvelope) throws Exception {
        return channelHandlerContext.alloc().ioBuffer(1024);
    }

    private static void a(DnsQuery dnsQuery, ByteBuf byteBuf) {
        byteBuf.writeShort(dnsQuery.id());
        int byteValue = ((dnsQuery.opCode().byteValue() & 255) << 14) | 0;
        if (dnsQuery.isRecursionDesired()) {
            byteValue |= 256;
        }
        byteBuf.writeShort(byteValue);
        byteBuf.writeShort(dnsQuery.count(DnsSection.QUESTION));
        byteBuf.writeShort(0);
        byteBuf.writeShort(0);
        byteBuf.writeShort(dnsQuery.count(DnsSection.ADDITIONAL));
    }

    private void b(DnsQuery dnsQuery, ByteBuf byteBuf) throws Exception {
        int count = dnsQuery.count(DnsSection.QUESTION);
        for (int i = 0; i < count; i++) {
            this.a.encodeQuestion((DnsQuestion) dnsQuery.recordAt(DnsSection.QUESTION, i), byteBuf);
        }
    }

    private void a(DnsQuery dnsQuery, DnsSection dnsSection, ByteBuf byteBuf) throws Exception {
        int count = dnsQuery.count(dnsSection);
        for (int i = 0; i < count; i++) {
            this.a.encodeRecord(dnsQuery.recordAt(dnsSection, i), byteBuf);
        }
    }
}
